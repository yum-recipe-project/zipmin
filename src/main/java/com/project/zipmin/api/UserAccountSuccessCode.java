package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAccountSuccessCode implements Code {
	
	// 데이터 처리
	USER_ACCOUNT_CREATE_SUCCESS(HttpStatus.CREATED, "계좌 작성 성공"),
	USER_ACCOUNT_READ_SUCCESS(HttpStatus.OK, "계좌 조회 성공"),
	USER_ACCOUNT_UPDATE_SUCCESS(HttpStatus.OK, "계좌 수정 성공"),
	
	;

	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
