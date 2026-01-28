package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ReviewErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.review.ReviewCreateRequestDto;
import com.project.zipmin.dto.review.ReviewCreateResponseDto;
import com.project.zipmin.dto.review.ReviewReadResponseDto;
import com.project.zipmin.dto.review.ReviewUpdateRequestDto;
import com.project.zipmin.dto.review.ReviewUpdateResponseDto;
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
	private final ReviewMapper reviewMapper;
	private final UserService userService;
	private final LikeService likeService;
	private final ReportService reportService;
	
	
	
	
	
	// 리뷰 목록 조회 (관리자)
	public Page<ReviewReadResponseDto> readReviewPage(String keyword, String tablename, String sort, Pageable pageable) {

		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
		}

		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 리뷰 목록 조회
		Page<Review> reviewPage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasTablename = tablename != null && !tablename.isBlank();
			
			if (hasTablename) {
				reviewPage = hasKeyword
						? reviewRepository.findAllByTablenameAndContentContainingIgnoreCase(tablename, keyword, pageable)
						: reviewRepository.findAllByTablename(tablename, pageable);
			}
			else {
				reviewPage = hasKeyword
						? reviewRepository.findAllByContentContainingIgnoreCase(keyword, pageable)
						: reviewRepository.findAll(pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(ReviewErrorCode.REVIEW_READ_LIST_FAIL);
		}
		
		// 리뷰 목록 응답 구성
		List<ReviewReadResponseDto> reviewDtoList = new ArrayList<>();
		for (Review review : reviewPage) {
			ReviewReadResponseDto reviewDto = reviewMapper.toReadResponseDto(review);
			reviewDto.setUsername(review.getUser().getUsername());
			reviewDto.setNickname(review.getUser().getNickname());
			reviewDto.setLikecount(likeService.countLike("review", review.getId()));
			reviewDto.setReportcount(reportService.countReport("review", review.getId()));
			// reviewDto.setTitle(review.getRecipe().getTitle());
			reviewDtoList.add(reviewDto);
		}
		
		return new PageImpl<>(reviewDtoList, pageable, reviewPage.getTotalElements());
	}
	
	
	

	
	// 리뷰 목록 조회 (레코드번호 기반)
	public Page<ReviewReadResponseDto> readReviewPageByTablenameAndRecodenum(String tablename, int recodenum, String sort, Pageable pageable) {

		// 입력값 검증
		if (tablename == null || recodenum == 0 ||  pageable == null) {
			throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
		}

		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 리뷰 목록 조회
		Page<Review> reviewPage;
		try {
			reviewPage = reviewRepository.findAllByTablenameAndRecodenum(tablename, recodenum, pageable);
		}
		catch (Exception e) {
			throw new ApiException(ReviewErrorCode.REVIEW_READ_LIST_FAIL);
		}

		// 리뷰 목록 응답 구성
		List<ReviewReadResponseDto> reviewDtoList = new ArrayList<>();
		for (Review review : reviewPage) {
			ReviewReadResponseDto reviewDto = reviewMapper.toReadResponseDto(review);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				reviewDto.setLiked(likeService.existLike("review", review.getId(), userDto.getId()));
			}
			reviewDto.setLikecount(review.getLikecount());
			reviewDto.setReportcount(review.getReportcount());
			reviewDto.setUsername(review.getUser().getUsername());
			reviewDto.setNickname(review.getUser().getNickname());
			reviewDto.setAvatar(review.getUser().getAvatar());
			reviewDtoList.add(reviewDto);
		}

		return new PageImpl<>(reviewDtoList, pageable, reviewPage.getTotalElements());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 리뷰 작성
	public ReviewCreateResponseDto createReview(ReviewCreateRequestDto reviewRequestDto) {

		// 입력값 검증
		if (reviewRequestDto == null 
				|| reviewRequestDto.getScore() == 0
				|| reviewRequestDto.getContent() == null
				|| reviewRequestDto.getTablename() == null
				|| reviewRequestDto.getRecodenum() == 0
				|| reviewRequestDto.getUserId() == 0) {
			throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
		}

		// 리뷰 작성
		try {
			Review review = reviewMapper.toEntity(reviewRequestDto);
			review = reviewRepository.saveAndFlush(review);
			return reviewMapper.toCreateResponseDto(review);
		}
		catch (Exception e) {
			throw new ApiException(ReviewErrorCode.REVIEW_CREATE_FAIL);
		}
		
		// 리뷰 응답 구성
		// ReviewCreateResponseDto reviewResponseDto = reviewMapper.toCreateResponseDto(review);
		//reviewResponseDto.setNickname(userService.readUserById(reviewRequestDto.getUserId()).getNickname());
		//reviewResponseDto.setLikecount(likeService.countLike("review", review.getId()));
		//reviewResponseDto.setLikestatus(false);
		
		// return reviewResponseDto;
	}

	
	

	
	// 리뷰 수정
	public ReviewUpdateResponseDto updateReview(ReviewUpdateRequestDto reviewDto) {
		
		// 입력값 검증
		if (reviewDto == null
				|| reviewDto.getId() == 0
				|| reviewDto.getScore() == 0
				|| reviewDto.getContent() == null) {
			throw new ApiException(ReviewErrorCode.REVIEW_INVALID_INPUT);
		}
		
		// 리뷰 존재 여부 판단
		Review review = reviewRepository.findById(reviewDto.getId())
				.orElseThrow(() -> new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND));
		
		// 리뷰 조회
		try {
			
		}
		catch (Exception e) {
			throw new ApiException(ReviewErrorCode.REVIEW_NOT_FOUND);
		}
		
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
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
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
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
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
	public Page<ReviewReadResponseDto> readReviewPageByUserId(Integer userId, Pageable pageable) {

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

		List<ReviewReadResponseDto> reviewDtoList = new ArrayList<>();
		for (Review review : reviewPage) {
			ReviewReadResponseDto reviewDto = reviewMapper.toReadResponseDto(review);
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
	
}
