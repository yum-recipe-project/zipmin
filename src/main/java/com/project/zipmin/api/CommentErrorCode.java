package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements Code {
	
	// 인증/인가
	COMMENT_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	COMMENT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없음"),
    COMMENT_CREATE_FAIL(HttpStatus.BAD_REQUEST, "댓글 작성 실패"),
    COMMENT_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 목록 조회 실패"),
    COMMENT_READ_COUNT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 개수 조회 실패"),
    COMMENT_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "댓글 수정 실패"),
    COMMENT_DELETE_FAIL(HttpStatus.BAD_REQUEST, "댓글 삭제 실패"),
    
    COMMENT_LIKE_FAIL(HttpStatus.BAD_REQUEST, "댓글 좋아요 실패"),
    COMMENT_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "댓글 좋아요 삭제 실패"),
    COMMENT_REPORT_FAIL(HttpStatus.BAD_REQUEST, "댓글 신고 실패"),
    COMMENT_UNREPORT_FAIL(HttpStatus.BAD_REQUEST, "댓글 신고 삭제 실패"),
	
	// 기타
	COMMENT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
