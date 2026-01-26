 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeSuccessCode implements Code {
	
	LIKE_CREATE_SUCCESS(HttpStatus.CREATED, "좋아요 작성 성공"),
    LIKE_DELETE_SUCCESS(HttpStatus.OK, "좋아요 삭제 성공"),
    LIKE_COUNT_SUCCESS(HttpStatus.OK, "좋아요 집계 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
