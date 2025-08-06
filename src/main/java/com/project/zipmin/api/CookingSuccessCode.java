 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookingSuccessCode implements Code {
	
	// 데이터 처리
	COOKING_READ_LIST_SUCCESS(HttpStatus.OK, "쿠킹클래스 목록 조회 성공"),
	COOKING_READ_SUCCESS(HttpStatus.OK, "쿠킹클래스 조회 성공"),
	COOKING_CREATE_SUCCESS(HttpStatus.CREATED, "쿠킹클래스 작성 성공"),
    COOKING_UPDATE_SUCCESS(HttpStatus.OK, "쿠킹클래스 수정 성공"),
    COOKING_DELETE_SUCCESS(HttpStatus.OK, "쿠킹클래스 삭제 성공"),
    
    COOKING_APPLY_READ_LIST_SUCCESS(HttpStatus.OK, "신청 목록 조회 성공"),
    COOKING_APPLY_READ_SUCCESS(HttpStatus.OK, "신청 조회 성공"),
    COOKING_APPLY_CREATE_SUCCESS(HttpStatus.CREATED, "신청 작성 성공"),
    COOKING_APPLY_UPDATE_SUCCESS(HttpStatus.OK, "신청 수정 성공"),
    COOKING_APPLY_DELETE_SUCCESS(HttpStatus.OK, "신청 삭제 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
