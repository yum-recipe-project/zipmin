package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ReviewSuccessCode;
import com.project.zipmin.dto.ReviewReadResponseDto;
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

	
	
}


