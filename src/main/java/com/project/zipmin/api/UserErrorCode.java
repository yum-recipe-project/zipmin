package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements Code {
	
	// 회원가입
	USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
	USER_TEL_DUPLICATED(HttpStatus.CONFLICT, "이미 등록된 휴대폰 번호입니다."),
	USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),
	
	// 로그인
	USER_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
	USER_PASSWORD_RESET_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "임시 비밀번호 발급에 실패했습니다."),
	
	// 인증
	USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
	USER_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 작업에 대한 권한이 없습니다."),
	
	// 그 외
	USER_INVALID_PARAM(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}

	
	