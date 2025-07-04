package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements Code {
	
	AUTH_TOKEN_ISSUE_SUCCESS(HttpStatus.OK, "토큰이 발급되었습니다."),
	AUTH_TOKEN_REISSUE_SUCCESS(HttpStatus.OK, "토큰이 재발급되었습니다."),
	AUTH_VALID_TOKEN(HttpStatus.OK, "유효한 토큰입니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
