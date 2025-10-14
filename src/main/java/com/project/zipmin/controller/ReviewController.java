package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
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
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.ReviewService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;

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

	
	// 리뷰 수정
	@PatchMapping("/reviews/{id}")
	public ResponseEntity<?> updateReview(
	        @PathVariable int id,
	        @RequestBody ReviewUpdateRequestDto reviewRequestDto) {
	    
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

	
	// 리뷰 삭제
	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<?> deleteReview(
	        @PathVariable int id) {

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
	    }

	    // 리뷰 삭제 서비스 호출
	    reviewService.deleteReview(id);

	    // 삭제 성공 응답 반환
	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_DELETE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_DELETE_SUCCESS, null));
	}

	
	
	
	// 리뷰 좋아요
	@PostMapping("/reviews/{id}/likes")
	public ResponseEntity<?> likeReview(
	        @PathVariable int id,
	        @RequestBody LikeCreateRequestDto likeRequestDto) {

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
	    }

	    // 좋아요 작성자 userId 설정
	    likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

	    // 리뷰 좋아요 서비스 호출
	    LikeCreateResponseDto likeResponseDto = reviewService.likeReview(likeRequestDto);

	    // 응답 반환
	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_LIKE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_LIKE_SUCCESS, likeResponseDto));
	}

	
	
	
	// 리뷰 좋아요 취소
	@DeleteMapping("/reviews/{id}/likes")
	public ResponseEntity<?> unlikeReview(
	        @PathVariable int id,
	        @RequestBody LikeDeleteRequestDto likeDto) {

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
	    }

	    // 현재 로그인 유저 ID 설정
	    likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

	    // 좋아요 취소 서비스 호출
	    reviewService.unlikeReview(likeDto);

	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_UNLIKE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_UNLIKE_SUCCESS, null));
	}
	
	
	
	
	
	
	
	// 사용자가 작성한 리뷰 조회
	@GetMapping("/users/{id}/reviews")
	public ResponseEntity<?> readUserReviewList(
	        @PathVariable Integer id,
	        @RequestParam int page,
	        @RequestParam int size) {

	    // 입력값 검증
	    if (id == null) {
	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
	    }

	    // 인증 여부 확인 (비로그인)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
	    }

	    // 로그인 정보
	    String username = authentication.getName();

	    // 본인 확인 (관리자가 아니면 본인만 조회 가능)
	    UserReadResponseDto loginUser = userService.readUserByUsername(username);
	    if (!loginUser.getRole().equals(Role.ROLE_ADMIN.name()) && !id.equals(loginUser.getId())) {
	        throw new ApiException(UserErrorCode.USER_FORBIDDEN);
	    }

	    Pageable pageable = PageRequest.of(page, size);
	    Page<ReviewReadMyResponseDto> reviewPage = reviewService.readReviewPageByUserId(id, pageable);

	    return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, reviewPage));
	}

}


