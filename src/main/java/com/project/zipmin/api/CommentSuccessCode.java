 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentSuccessCode implements Code {
	
	COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "댓글 작성 성공"),
	COMMENT_READ_LIST_SUCCESS(HttpStatus.OK, "댓글 목록 조회 성공"),
	COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글 조회 성공"),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정 성공"),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글 삭제 성공"),
    
    COMMENT_LIKE_SUCCESS(HttpStatus.OK, "댓글 좋아요 성공"),
    COMMENT_UNLIKE_SUCCESS(HttpStatus.OK, "댓글 좋아요 취소 성공"),
    COMMENT_REPORT_SUCCESS(HttpStatus.OK, "댓글 신고 성공"),
    COMMENT_UNREPORT_SUCCESS(HttpStatus.OK, "댓글 신고 취소 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
