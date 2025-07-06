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
import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.CommentCreateFailResponse;
import com.project.zipmin.swagger.CommentCreateSuccessResponse;
import com.project.zipmin.swagger.CommentDeleteFailResponse;
import com.project.zipmin.swagger.CommentDeleteSuccessResponse;
import com.project.zipmin.swagger.CommentForbiddenResponse;
import com.project.zipmin.swagger.CommentInvalidInputResponse;
import com.project.zipmin.swagger.CommentNotFoundResponse;
import com.project.zipmin.swagger.CommentUnauthorizedAccessResponse;
import com.project.zipmin.swagger.CommentUpdateFailResponse;
import com.project.zipmin.swagger.CommentUpdateSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;

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
	@GetMapping("/comments")
	public ResponseEntity<?> readComment(
			@RequestParam String tablename,
			@RequestParam int recodenum,
			@RequestParam String sort,
			@RequestParam int page,
			@RequestParam int size) {
		
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
	    summary = "댓글 작성",
	    description = "댓글을 작성합니다."
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
		
		// 권한 없는 사용자의 접근
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getId() != commentRequestDto.getUserId()) {
		    throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
		}
		
		CommentCreateResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_CREATE_SUCCESS, commentResponseDto));
	}
	
	
	
	// 댓글 수정
	@Operation(
	    summary = "댓글 수정",
	    description = "댓글을 수정합니다."
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
		
		// 권한 없는 사용자의 접근
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getId() != commentRequestDto.getUserId()) {
		    throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
		}
		
		CommentUpdateResponseDto commentResponseDto = commentService.updateComment(commentRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_UPDATE_SUCCESS, commentResponseDto));
	}
	
	
	
	// 댓글 삭제
	@DeleteMapping("/comments/{id}")
	@Operation(
	    summary = "댓글 삭제",
	    description = "댓글을 삭제합니다."
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
	public ResponseEntity<?> deleteComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 삭제 요청 정보", required = true) @RequestBody CommentDeleteRequestDto commentDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 없는 사용자의 접근
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getId() != commentDto.getUserId()) {
		    throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
		}
		
		commentService.deleteComment(commentDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_DELETE_SUCCESS, null));
	}
	
	
	
	// 댓글 좋아요 개수
	@GetMapping("/comments/{id}/likes/count")
	public ResponseEntity<?> countCommentLike(@PathVariable int id) {
		return null;
	}
	
	
	
	// 댓글 좋아요 작성
	@GetMapping("/comments/{id}/likes")
	public ResponseEntity<?> likeComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 좋아요 작성 요청 정보", required = true) @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 없는 사용자의 접근
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getId() != likeRequestDto.getUserId()) {
		    throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
		}
		
		LikeCreateResponseDto likeResponseDto = commentService.likeComment(likeRequestDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_LIKE_SUCCESS, likeResponseDto));
	}
	
	
	
	// 댓글 좋아요 삭제
	@PostMapping("/comments/{id}/likes")
	public ResponseEntity<?> unlikeComment(
			@Parameter(description = "댓글의 일련번호", required = true, example = "1") @PathVariable int id,
			@Parameter(description = "댓글 좋아요 삭제 요청 정보", required = true) @RequestBody LikeDeleteRequestDto likeDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 없는 사용자의 접근
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getId() != likeDto.getUserId()) {
		    throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
		}
		
		commentService.unlikeComment(likeDto);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_UNLIKE_SUCCESS, null));
	}
	
	
	
	// 댓글 좋아요 여부 확인
	@GetMapping("/comments/{id}/likes/status")
	public ResponseEntity<?> checkCommentStatus(@PathVariable int id) {
		return null;
	}
	
	
	
	// 댓글 신고 개수 (관리자)
	
	
	
	// 댓글 신고 표시
	// 특정 투표의 특정 댓글 신고
//	@PostMapping("/votes/{voteId}/comments/{commId}/reports")
//	public int reportVoteComment(
//			@PathVariable("voteId") int voteId,
//			@PathVariable("commId") int commId) {
//		return 0;
//	}
//
//	// 특정 투표의 특정 댓글 신고 개수 (관리자)
//	@GetMapping("/votes/{voteId}/comments/{commId}/reports/count")
//	public int countReportVoteComment(
//			@PathVariable("voteId") int voteId,
//			@PathVariable("commId") int commId) {
//		return 0;
//	}
//
//	// 특정 투표의 특정 댓글 신고 여부
//	@GetMapping("/votes/{voteId}/comments/{commId}/reports/status")
//	public boolean checkReportVoteComment(
//			@PathVariable("voteId") int voteId,
//			@PathVariable("commId") int commId) {
//		return false;
//	}
	
	
	
}


