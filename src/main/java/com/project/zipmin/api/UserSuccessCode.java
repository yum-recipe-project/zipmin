package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements Code {
	
	// 데이터 처리
	USER_READ_LIST_SUCCESS(HttpStatus.OK, "사용자 목록 조회 성공"),
	USER_READ_USERNAME_SUCCESS(HttpStatus.OK, "사용자 아이디 조회 성공"),
	USER_READ_SUCCESS(HttpStatus.OK, "사용자 조회 성공"),
	USER_CREATE_SUCCESS(HttpStatus.CREATED, "사용자 작성 성공"),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "사용자 수정 성공"),
	USER_DELETE_SUCCESS(HttpStatus.OK, "사용자 삭제 성공"),
	
	USER_READ_RECIPE_LIST_SUCCESS(HttpStatus.OK, "사용자 레시피 목록 조회 성공"),
	USER_READ_CLASS_LIST_SUCCESS(HttpStatus.OK, "사용자 클래스 목록 조회 성공"),
	
	USER_READ_ACCOUNT_SUCCESS(HttpStatus.OK, "사용자 출금 계좌 조회 성공"),
	USER_CREATE_ACCOUNT_SUCCESS(HttpStatus.OK, "사용자 출금 계좌 등록 성공"),
	
	USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
	USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),
	USER_RESET_PASSWORD_SUCCESS(HttpStatus.OK, "임시 비밀번호 발급 성공"),
	
	USER_LIKE_SUCCESS(HttpStatus.OK, "사용자 좋아요 성공"),
	USER_UNLIKE_SUCCESS(HttpStatus.OK, "사용자 좋아요 취소 성공"),
	
	USER_USERNAME_NOT_DUPLICATED(HttpStatus.OK, "사용 가능한 아이디"),
	USER_CORRECT_PASSWORD(HttpStatus.OK, "비밀번호 일치");

	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
