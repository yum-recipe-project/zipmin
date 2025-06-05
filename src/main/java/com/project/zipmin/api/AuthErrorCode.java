package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements Code {
	
	// 쿠키
	AUTH_COOKIE_MISSING(HttpStatus.UNAUTHORIZED, "쿠키가 없습니다."),
	
	
	// 토큰
	AUTH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
	AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	AUTH_TOKEN_FORGED(HttpStatus.UNAUTHORIZED, "위조된 토큰입니다."),
	AUTH_TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰 형식입니다."),
	AUTH_TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
	AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
	
	// 리프레시 토큰
	AUTH_REFRESH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 없습니다."),
	AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
	AUTH_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 아닙니다."),
	
	AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
	AUTH_OAUTH_PROVIDER_MISSING(HttpStatus.BAD_REQUEST, "소셜 로그인 제공자가 일치하지 않습니다."),
	AUTH_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	AUTH_LOGOUT_FAILD(HttpStatus.INTERNAL_SERVER_ERROR, "로그아웃 처리에 실패했습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
