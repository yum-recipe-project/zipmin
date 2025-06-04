package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements Code {
	
	// 회원가입
	USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
	USER_USERNAME_AVAILABLE(HttpStatus.OK, "사용 가능한 아이디입니다."),
	
	// 로그인
	USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
	USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
	USER_FIND_USERNAME_SUCCESS(HttpStatus.OK, "아이디를 찾았습니다."),
	USER_FIND_PASSWORD_SUCCESS(HttpStatus.OK, "임시 비밀번호를 발급하였습니다."),
	
	// 회원 정보
	USER_PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보가 수정되었습니다."),
	USER_DELETE_SUCCESS(HttpStatus.OK, "회원 탈퇴가 완료되었습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
