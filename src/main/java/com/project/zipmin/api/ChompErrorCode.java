package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChompErrorCode implements Code {
	
	CHOMP_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CHOMP_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	CHOMP_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),

	CHOMP_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쩝쩝박사 목록 조회 실패"),
	
	CHOMP_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리되지 않은 서버 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
