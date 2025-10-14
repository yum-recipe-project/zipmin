package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ReviewErrorCode;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.ReviewCreateRequestDto;
import com.project.zipmin.dto.ReviewCreateResponseDto;
import com.project.zipmin.dto.ReviewReadMyResponseDto;
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.dto.ReviewUpdateRequestDto;
import com.project.zipmin.dto.ReviewUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Review;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ReviewMapper;
import com.project.zipmin.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	private final UserService userService;
	private final LikeService likeService;
	private final ReportService reportService;

	private final ReviewMapper reviewMapper;
	

	// 리뷰 목록 조회
    public Page<ReviewReadResponseDto> readReviewPage(Integer recipeId, String sort, Pageable pageable) {

        // 입력값 검증
        if (recipeId == null || pageable == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 정렬 문자열 객체 변환
        Sort sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
        if (sort != null && !sort.isBlank()) {
            switch (sort) {
	            case "id-desc":
	                sortSpec = Sort.by(Sort.Order.desc("id")); 
	                break;
	            case "id-asc":
	                sortSpec = Sort.by(Sort.Order.asc("id")); 
	                break;
	            case "postdate-desc":
	                sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id")); // 최신순
	                break;
	            case "postdate-asc":
	                sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id")); // 오래된순
	                break;
	            case "score-desc":
	                sortSpec = Sort.by(Sort.Order.desc("score"), Sort.Order.desc("id")); // 별점 높은순
	                break;
	            case "score-asc":
	                sortSpec = Sort.by(Sort.Order.asc("score"), Sort.Order.asc("id")); // 별점 낮은순
	                break;
	            case "likecount-desc":
	                sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id")); // 좋아요 높은순
	                break;
	            case "likecount-asc":
	                sortSpec = Sort.by(Sort.Order.asc("likecount"), Sort.Order.asc("id")); // 좋아요 낮은순
	                break;
	            default:
	                break;
	        }
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);

        // 리뷰 조회
        Page<Review> reviewPage;
        try {
            reviewPage = reviewRepository.findAllByRecipeId(recipeId, sortedPageable);
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_READ_LIST_FAIL);
        }

        // DTO 변환
        List<ReviewReadResponseDto> reviewDtoList = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer currentUserId = null;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            currentUserId = userService.readUserByUsername(username).getId();
        }

        for (Review review : reviewPage) {
        	
            ReviewReadResponseDto dto = reviewMapper.toReadResponseDto(review);
            
            dto.setUsername(review.getUser().getUsername());
            dto.setNickname(review.getUser().getNickname());

            // 좋아요, 신고, 로그인 사용자 좋아요 여부
            dto.setLikecount(likeService.countLike("review", review.getId()));
            dto.setReportcount(reportService.countReport("review", review.getId()));

            if (currentUserId != null) {
                dto.setLiked(likeService.existsUserLike("review", review.getId(), currentUserId));
            }

            reviewDtoList.add(dto);
        }

        System.err.println("리뷰 목록" + reviewDtoList);
        
        
        return new PageImpl<>(reviewDtoList, sortedPageable, reviewPage.getTotalElements());
    }

	
	
	
    // 리뷰 작성
    public ReviewCreateResponseDto createReview(ReviewCreateRequestDto reviewRequestDto) {

        // 입력값 검증
        if (reviewRequestDto == null 
                || reviewRequestDto.getContent() == null
                || reviewRequestDto.getScore() == null
                || reviewRequestDto.getRecipeId() == null
                || reviewRequestDto.getUserId() == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }
        
        

        // DTO → 엔티티 변환
        Review review = reviewMapper.toEntity(reviewRequestDto);
        System.err.println(review);

        try {
            // 리뷰 저장
            review = reviewRepository.saveAndFlush(review);

            // 응답 DTO 생성
            ReviewCreateResponseDto reviewResponseDto = reviewMapper.toCreateResponseDto(review);
            reviewResponseDto.setNickname(userService.readUserById(reviewRequestDto.getUserId()).getNickname());
            reviewResponseDto.setLikecount(likeService.countLike("review", review.getId()));
            reviewResponseDto.setLikestatus(false); // 작성 직후는 기본적으로 좋아요 안 누른 상태

            return reviewResponseDto;
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_CREATE_FAIL);
        }
    }

	
    
    
    // 리뷰 수정
    public ReviewUpdateResponseDto updateReview(ReviewUpdateRequestDto reviewDto) {
        
        // 입력값 검증
        if (reviewDto == null || reviewDto.getId() == null || reviewDto.getContent() == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }
        
        // 리뷰 존재 여부 판단
        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND));
        
        // 권한 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserReadResponseDto user = userService.readUserByUsername(username);
        if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
            // 관리자
            if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
                if (review.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
                    throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
                }
                else if (review.getUser().getRole().equals(Role.ROLE_ADMIN)) {
                    if (user.getId() != review.getUser().getId()) {
                        throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
                    }
                }
            }
            // 일반 회원
            else {
                if (user.getId() != review.getUser().getId()) {
                    throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
                }
            }
        }
        
        // 변경 값 설정
        review.setContent(reviewDto.getContent());
        review.setScore(reviewDto.getScore());

        try {
            review = reviewRepository.save(review);
            return reviewMapper.toUpdateResponseDto(review);
        }
        catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_UPDATE_FAIL);
        }
    }

	
	

    // 리뷰 삭제
    public void deleteReview(Integer id) {

        // 입력값 검증
        if (id == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 리뷰 존재 여부 판단
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 권한 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserReadResponseDto user = userService.readUserByUsername(username);

        if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (review.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
				}
				else if (review.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != review.getUser().getId()) {
						throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != review.getUser().getId()) {
					throw new ApiException(ReviewErrorCode.REVIEW_FORBIDDEN);
				}
			}
		}

        // 리뷰 삭제
        try {
            reviewRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_DELETE_FAIL);
        }
    }

	

    
    // 리뷰 좋아요
    public LikeCreateResponseDto likeReview(LikeCreateRequestDto likeDto) {

        // 입력값 검증
        if (likeDto == null || likeDto.getTablename() == null
                || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 리뷰 존재 여부 확인
        if (!reviewRepository.existsById(likeDto.getRecodenum())) {
            throw new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND);
        }

        // 좋아요 저장
        try {
            return likeService.createLike(likeDto);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_LIKE_FAIL);
        }
    }
    
    
    // 리뷰 좋아요 취소
    public void unlikeReview(LikeDeleteRequestDto likeDto) {

        // 입력값 검증
        if (likeDto == null || likeDto.getTablename() == null
                || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 리뷰 존재 여부 확인
        if (!reviewRepository.existsById(likeDto.getRecodenum())) {
            throw new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND);
        }

        // 좋아요 취소
        try {
            likeService.deleteLike(likeDto);
        } 
        catch (ApiException e) {
            throw e;
        } 
        catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_UNLIKE_FAIL);
        }
    }



    // 사용자가 작성한 리뷰 목록을 조회하는 함수
    public Page<ReviewReadMyResponseDto> readReviewPageByUserId(Integer userId, Pageable pageable) {

        if (userId == null || pageable == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 리뷰 목록 조회
        Page<Review> reviewPage;
        try {
            reviewPage = reviewRepository.findByUserId(userId, pageable);
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_READ_LIST_FAIL);
        }

        List<ReviewReadMyResponseDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewPage) {
            ReviewReadMyResponseDto reviewDto = reviewMapper.toReadMyResponseDto(review);
            reviewDto.setNickname(review.getUser().getNickname());

            // 리뷰가 속한 레시피 제목 가져오기
            String title = null;
            if (review.getRecipe() != null) {
                title = review.getRecipe().getTitle();
            }
            reviewDto.setTitle(title);

            reviewDtoList.add(reviewDto);
        }

        return new PageImpl<>(reviewDtoList, pageable, reviewPage.getTotalElements());
    }

    
    
    
    // 전체 리뷰 목록 조회 (관리자용)
    public Page<ReviewReadResponseDto> readAllReviewPage(String sort, Pageable pageable) {

        // 입력값 검증
        if (pageable == null) {
            throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
        }

        // 정렬 문자열 객체 변환
        Sort sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
        if (sort != null && !sort.isBlank()) {
            switch (sort) {
                case "postdate-desc":
                    sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id")); // 최신순
                    break;
                case "postdate-asc":
                    sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id")); // 오래된순
                    break;
                case "likecount-desc":
                    sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id")); // 좋아요 많은순
                    break;
                case "likecount-asc":
                    sortSpec = Sort.by(Sort.Order.asc("likecount"), Sort.Order.asc("id")); // 좋아요 적은순
                    break;
                default:
                    break;
            }
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);

        // 리뷰 전체 조회
        Page<Review> reviewPage;
        try {
            reviewPage = reviewRepository.findAll(sortedPageable);
        } catch (Exception e) {
            throw new ApiException(ReviewErrorCode.REVIEW_READ_LIST_FAIL);
        }

        // DTO 변환
        List<ReviewReadResponseDto> reviewDtoList = new ArrayList<>();

        for (Review review : reviewPage) {

            ReviewReadResponseDto dto = reviewMapper.toReadResponseDto(review);

            // 작성자 정보
            dto.setUsername(review.getUser().getUsername());
            dto.setNickname(review.getUser().getNickname());

            // 좋아요, 신고 개수
            dto.setLikecount(likeService.countLike("review", review.getId()));
            dto.setReportcount(reportService.countReport("review", review.getId()));

            // 관리자 조회이므로 좋아요 여부는 기본 false
            dto.setLiked(false);
            
            // 리뷰가 속한 레시피 제목 가져오기
            String title = null;
            if (review.getRecipe() != null) {
                title = review.getRecipe().getTitle();
            }
            dto.setTitle(title);
            

            reviewDtoList.add(dto);
        }

        System.err.println("관리자 전체 리뷰 목록: " + reviewDtoList);

        return new PageImpl<>(reviewDtoList, pageable, reviewPage.getTotalElements());
    }

    
    
    
    
    
    
}
