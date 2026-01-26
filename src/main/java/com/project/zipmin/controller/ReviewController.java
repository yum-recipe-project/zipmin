package com.project.zipmin.controller;

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
import com.project.zipmin.dto.ReviewCreateRequestDto;
import com.project.zipmin.dto.ReviewCreateResponseDto;
import com.project.zipmin.dto.ReviewReadMyResponseDto;
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.dto.ReviewUpdateRequestDto;
import com.project.zipmin.dto.ReviewUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.report.ReportCreateRequestDto;
import com.project.zipmin.dto.report.ReportCreateResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.ReviewService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.RecipeNotFoundResponse;
import com.project.zipmin.swagger.ReviewCreateFailResponse;
import com.project.zipmin.swagger.ReviewCreateSuccessResponse;
import com.project.zipmin.swagger.ReviewDeleteFailResponse;
import com.project.zipmin.swagger.ReviewDeleteSuccessResponse;
import com.project.zipmin.swagger.ReviewForbiddenResponse;
import com.project.zipmin.swagger.ReviewInvalidInputResponse;
import com.project.zipmin.swagger.ReviewLikeFailResponse;
import com.project.zipmin.swagger.ReviewLikeSuccessResponse;
import com.project.zipmin.swagger.ReviewNotFoundResponse;
import com.project.zipmin.swagger.ReviewReadListFailResponse;
import com.project.zipmin.swagger.ReviewReadListSuccessResponse;
import com.project.zipmin.swagger.ReviewReportFailResponse;
import com.project.zipmin.swagger.ReviewReportSuccessResponse;
import com.project.zipmin.swagger.ReviewUnauthorizedAccessResponse;
import com.project.zipmin.swagger.ReviewUnlikeFailResponse;
import com.project.zipmin.swagger.ReviewUnlikeSuccessResponse;
import com.project.zipmin.swagger.ReviewUpdateFailResponse;
import com.project.zipmin.swagger.ReviewUpdateSuccessResponse;
import com.project.zipmin.swagger.UserForbiddenResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.UserReviewReadListSuccessResponse;
import com.project.zipmin.swagger.UserUnauthorizedAccessResponse;
import com.project.zipmin.swagger.like.LikeCountFailResponse;
import com.project.zipmin.swagger.like.LikeCreateFailResponse;
import com.project.zipmin.swagger.like.LikeDeleteFailResponse;
import com.project.zipmin.swagger.like.LikeDuplicatedResponse;
import com.project.zipmin.swagger.like.LikeExistFailResponse;
import com.project.zipmin.swagger.like.LikeForbiddenResponse;
import com.project.zipmin.swagger.like.LikeInvalidInputResponse;
import com.project.zipmin.swagger.like.LikeNotFoundResponse;
import com.project.zipmin.swagger.report.ReportCountFailResponse;
import com.project.zipmin.swagger.report.ReportCreateFailResponse;
import com.project.zipmin.swagger.report.ReportDuplicatedResponse;
import com.project.zipmin.swagger.report.ReportForbiddenResponse;
import com.project.zipmin.swagger.report.ReportInvalidInputResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "REVIEW API", description = "리뷰 관련 API")
public class ReviewController {
	
	private final ReviewService reviewService;
	private final UserService userService;

	
	
	
	
	// 리뷰 목록 조회
	@Operation(
	    summary = "리뷰 목록 조회"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "리뷰 목록 조회 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewReadListSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 목록 조회 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewReadListFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "좋아요 집계 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = LikeCountFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "신고 집계 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReportCountFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "좋아요 여부 확인 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = LikeExistFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@GetMapping("/reviews")
	public ResponseEntity<?> readReview(
			@Parameter(description = "레코드 번호", required = false)  @RequestParam(required = false) Integer recodenum,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
	    Pageable pageable = PageRequest.of(page, size);
	    Page<ReviewReadResponseDto> reviewPage = reviewService.readReviewPage(recodenum, sort, pageable);
	    
	    return ResponseEntity.status(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS, reviewPage));
	}

	
	
	
	
	@Operation(
	    summary = "리뷰 작성"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "201",
	            description = "리뷰 작성 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewCreateSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 작성 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewCreateFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인 되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@PostMapping("/reviews")
	public ResponseEntity<?> createReview(
			@Parameter(description = "리뷰 작성 요청 정보")  @RequestBody ReviewCreateRequestDto reviewRequestDto) {
		
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
	@Operation(
	    summary = "리뷰 수정"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "리뷰 수정 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUpdateSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 수정 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUpdateFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 리뷰를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@PatchMapping("/reviews/{id}")
	public ResponseEntity<?> updateReview(
			@Parameter(description = "리뷰의 일련번호") @PathVariable int id,
			@Parameter(description = "리뷰 수정 요청 정보") @RequestBody ReviewUpdateRequestDto reviewRequestDto) {
	    
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

	
	
	
	
	@Operation(
	    summary = "리뷰 삭제"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "리뷰 삭제 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewDeleteSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 삭제 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewDeleteFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 리뷰를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<?> deleteReview(
			@Parameter(description = "리뷰의 일련번호")  @PathVariable int id) {

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

	
	
	
	
	@Operation(
	    summary = "리뷰 좋아요"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "리뷰 좋아요 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewLikeSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 좋아요 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewLikeFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeCreateFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인 되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 리뷰를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "좋아요 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeDuplicatedResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})	
	@PostMapping("/reviews/{id}/likes")
	public ResponseEntity<?> likeReview(
			@Parameter(description = "리뷰의 일련번호")  @PathVariable int id,
			@Parameter(description = "리뷰 작성 요청 정보") LikeCreateRequestDto likeRequestDto) {

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

	
	
	
	
	@Operation(
	    summary = "리뷰 좋아요 취소"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "리뷰 좋아요 취소 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnlikeSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 좋아요 취소 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnlikeFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeDeleteFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인 되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 좋아요를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeNotFoundResponse.class))),
		 @io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "404",
					description = "해당 레시피를 찾을 수 없음",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 리뷰를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@DeleteMapping("/reviews/{id}/likes")
	public ResponseEntity<?> unlikeReview(
			@Parameter(description = "리뷰의 일련번호")  @PathVariable int id,
			@Parameter(description = "리뷰 좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {

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
	
	
	
	
	@Operation(
	    summary = "사용자가 작성한 리뷰 목록 조회"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "사용자 리뷰 목록 조회 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserReviewReadListSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "리뷰 목록 조회 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewReadListFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인 되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ReviewForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 사용자를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 사용자가 작성한 리뷰 조회
	@GetMapping("/users/{id}/reviews")
	public ResponseEntity<?> readUserReviewList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {

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


