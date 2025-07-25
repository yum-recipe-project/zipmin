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
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.CommentSuccessCode;
import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentCreateResponseDto;
import com.project.zipmin.dto.CommentDeleteRequestDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.CommentCreateFailResponse;
import com.project.zipmin.swagger.CommentCreateSuccessResponse;
import com.project.zipmin.swagger.CommentDeleteFailResponse;
import com.project.zipmin.swagger.CommentDeleteSuccessResponse;
import com.project.zipmin.swagger.CommentForbiddenResponse;
import com.project.zipmin.swagger.CommentInvalidInputResponse;
import com.project.zipmin.swagger.CommentLikeFailResponse;
import com.project.zipmin.swagger.CommentLikeSuccessResponse;
import com.project.zipmin.swagger.CommentNotFoundResponse;
import com.project.zipmin.swagger.CommentReadListSuccessResponse;
import com.project.zipmin.swagger.CommentReportFailResponse;
import com.project.zipmin.swagger.CommentReportSuccessResponse;
import com.project.zipmin.swagger.CommentUnauthorizedAccessResponse;
import com.project.zipmin.swagger.CommentUnlikeFailResponse;
import com.project.zipmin.swagger.CommentUnlikeSuccessResponse;
import com.project.zipmin.swagger.CommentUnreportSuccessResponse;
import com.project.zipmin.swagger.CommentUpdateFailResponse;
import com.project.zipmin.swagger.CommentUpdateSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.LikeCreateFailResponse;
import com.project.zipmin.swagger.LikeDeleteFailResponse;
import com.project.zipmin.swagger.LikeDuplicatedResponse;
import com.project.zipmin.swagger.LikeForbiddenResponse;
import com.project.zipmin.swagger.LikeInvalidInputResponse;
import com.project.zipmin.swagger.LikeNotFoundResponse;
import com.project.zipmin.swagger.ReportCreateFailResponse;
import com.project.zipmin.swagger.ReportDeleteFailResponse;
import com.project.zipmin.swagger.ReportDuplicatedResponse;
import com.project.zipmin.swagger.ReportForbiddenResponse;
import com.project.zipmin.swagger.ReportInvalidInputResponse;
import com.project.zipmin.swagger.ReportNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Comment API", description = "댓글 관련 API")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	@Autowired
	UserService userService;
	
	
	
	// 댓글 목록 조회
	@Operation(
	    summary = "댓글 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "댓글 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@GetMapping("/comments")
	public ResponseEntity<?> readComment(
			@Parameter(description = "테이블 이름", required = true, example = "recipe") @RequestParam String tablename,
			@Parameter(description = "레코드 번호", required = true, example = "1") @RequestParam int recodenum,
			@Parameter(description = "정렬 순서", required = true, example = "new") @RequestParam String sort,
			@Parameter(description = "조회할 페이지 번호", required = true, example = "1") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수", required = true, example = "10") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<CommentReadResponseDto> commentPage = null;
		
		if (sort.equals("new")) {
			commentPage = commentService.readCommentPageOrderByIdDesc(tablename, recodenum, pageable);
		}
		else if (sort.equals("old")) {
			commentPage = commentService.readCommentPageOrderByIdAsc(tablename, recodenum, pageable);
		}
		else if (sort.equals("hot")) {
			commentPage = commentService.readCommentPageOrderByLikecount(tablename, recodenum, pageable);
		}
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_READ_LIST_SUCCESS.getStatus())
        		.body(ApiResponse.success(CommentSuccessCode.COMMENT_READ_LIST_SUCCESS, commentPage));
	}
	
	
	
	// 댓글 작성
	@Operation(
	    summary = "댓글 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "댓글 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@PostMapping("/comments")
	public ResponseEntity<?> createComment(
			@Parameter(description = "댓글 작성 요청 정보", required = true) @RequestBody CommentCreateRequestDto commentRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		commentRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		CommentCreateResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_CREATE_SUCCESS, commentResponseDto));
	}
	
	
	
	// 댓글 수정
	@Operation(
	    summary = "댓글 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@PatchMapping("/comments/{id}")
	public ResponseEntity<?> updateComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 수정 요청 정보", required = true) @RequestBody CommentUpdateRequestDto commentRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		commentRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		CommentUpdateResponseDto commentResponseDto = commentService.updateComment(commentRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_UPDATE_SUCCESS, commentResponseDto));
	}
	
	
	
	// 댓글 삭제
	@Operation(
	    summary = "댓글 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<?> deleteComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 삭제 요청 정보", required = true) @RequestBody CommentDeleteRequestDto commentDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		commentDto.setUserId(userService.readUserByUsername(username).getId());
		
		commentService.deleteComment(commentDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_DELETE_SUCCESS, null));
	}
	
	
	
	// 댓글 좋아요 작성
	@Operation(
	    summary = "댓글 좋아요"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 좋아요 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentLikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 좋아요 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentLikeFailResponse.class))),
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
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
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
	@PostMapping("/comments/{id}/likes")
	public ResponseEntity<?> likeComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 좋아요 작성 요청 정보", required = true) @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		likeRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		LikeCreateResponseDto likeResponseDto = commentService.likeComment(likeRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_LIKE_SUCCESS, likeResponseDto));
	}
	
	
	
	// 댓글 좋아요 삭제
	@Operation(
	    summary = "댓글 좋아요 취소"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 좋아요 취소 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnlikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 좋아요 취소 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnlikeFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeDeleteFailResponse.class))),
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
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
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
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@DeleteMapping("/comments/{id}/likes")
	public ResponseEntity<?> unlikeComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 좋아요 삭제 요청 정보", required = true) @RequestBody LikeDeleteRequestDto likeDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		likeDto.setUserId(userService.readUserByUsername(username).getId());
		
		commentService.unlikeComment(likeDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_UNLIKE_SUCCESS, null));
	}
	
	
	
	// 댓글 신고 작성
	@Operation(
	    summary = "댓글 신고"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 신고 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReportSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 신고 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReportFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "신고 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "좋아요 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@PostMapping("/comments/{id}/reports")
	public ResponseEntity<?> reportComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 신고 작성 요청 정보", required = true) @RequestBody ReportCreateRequestDto reportRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		reportRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		ReportCreateResponseDto reportReponseDto = commentService.reportComment(reportRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_REPORT_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_REPORT_SUCCESS, reportReponseDto));
	}
	
	
	
	// 댓글 신고 삭제
	@Operation(
	    summary = "댓글 신고 취소"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 신고 취소 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnreportSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 신고 취소 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReportFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "신고 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 신고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 댓글을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@DeleteMapping("/comments/{id}/reports")
	public ResponseEntity<?> unlikeComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 신고 삭제 요청 정보", required = true) @RequestBody ReportDeleteRequestDto reportDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		reportDto.setUserId(userService.readUserByUsername(username).getId());
		
		commentService.unreportComment(reportDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_UNREPORT_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_UNREPORT_SUCCESS, null));
	}
	
}


