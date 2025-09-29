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
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.entity.Review;
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
                case "postdate-desc":
                    sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id")); //최신
                    break;
                case "postdate-asc":
                    sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id")); //오래된
                    break;
                case "score-desc":
                    sortSpec = Sort.by(Sort.Order.desc("score"), Sort.Order.desc("id")); //평점 높은
                    break;
                case "score-asc":
                    sortSpec = Sort.by(Sort.Order.asc("score"), Sort.Order.asc("id"));
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
        
        
        return new PageImpl<>(reviewDtoList, pageable, reviewPage.getTotalElements());
    }

	
	
	
	
	
	
	
	

	


}
