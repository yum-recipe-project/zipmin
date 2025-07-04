package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements Code {
	
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    COMMENT_CREATE_FAIL(HttpStatus.BAD_REQUEST, "댓글 작성에 실패했습니다."),
    COMMENT_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "댓글 수정에 실패했습니다."),
    COMMENT_DELETE_FAIL(HttpStatus.BAD_REQUEST, "댓글 삭제에 실패했습니다."),
    
    COMMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "댓글에 대한 권한이 없습니다."),
    COMMENT_LIKE_FAIL(HttpStatus.BAD_REQUEST, "댓글 좋아요 처리에 실패했습니다."),
    COMMENT_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "댓글 좋아요 취소에 실패했습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
