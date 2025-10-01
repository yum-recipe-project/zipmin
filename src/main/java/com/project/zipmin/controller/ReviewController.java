package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ReviewErrorCode;
import com.project.zipmin.api.ReviewSuccessCode;
import com.project.zipmin.dto.ReviewCreateRequestDto;
import com.project.zipmin.dto.ReviewCreateResponseDto;
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.dto.ReviewUpdateRequestDto;
import com.project.zipmin.dto.ReviewUpdateResponseDto;
import com.project.zipmin.service.ReviewService;
import com.project.zipmin.service.UserService;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserService userService;
	
	
	// 리뷰 목록 조회 
	@GetMapping("/reviews")
	public ResponseEntity<?> readReview(
	        @RequestParam Integer recipeId,
	        @RequestParam(required = false) String sort,
	        @RequestParam int page,
	        @RequestParam int size) {
		
	    Pageable pageable = PageRequest.of(page, size);
	    Page<ReviewReadResponseDto> reviewPage = reviewService.readReviewPage(recipeId, sort, pageable);
	    
	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS, reviewPage));
	}

	
	// 리뷰 작성
	@PostMapping("/reviews")
	public ResponseEntity<?> createReview(
	        @RequestBody ReviewCreateRequestDto reviewRequestDto) {
		
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
	    }

	    // 작성자 userId 설정
	    reviewRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
	    
	    // 리뷰 생성 서비스 호출
	    ReviewCreateResponseDto reviewResponseDto = reviewService.createReview(reviewRequestDto);

	    // 응답 반환
	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, reviewResponseDto));
	}

	
	// 댓글 수정
	@PatchMapping("/reviews/{id}")
	public ResponseEntity<?> updateReview(
	        @PathVariable int id,
	        @RequestBody ReviewUpdateRequestDto reviewRequestDto) {
		System.err.println("수정 컨트롤러");
	    
	    // 인증 여부 확인 (비로그인)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
	    }

	    // 서비스 호출 (리뷰 수정)
	    ReviewUpdateResponseDto reviewResponseDto = reviewService.updateReview(reviewRequestDto);

	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_UPDATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_UPDATE_SUCCESS, reviewResponseDto));
	}

	
	
	
}


