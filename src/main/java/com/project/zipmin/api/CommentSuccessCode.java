package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentSuccessCode implements Code {
	
	COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "댓글이 등록되었습니다."),
    COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글이 조회되었습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글이 수정되었습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글이 삭제되었습니다."),
    
    COMMENT_LIST_SUCCESS(HttpStatus.OK, "댓글 목록이 조회되었습니다."),
    COMMENT_LIKE_SUCCESS(HttpStatus.OK, "댓글에 좋아요를 눌렀습니다."),
    COMMENT_UNLIKE_SUCCESS(HttpStatus.OK, "댓글 좋아요가 취소되었습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
