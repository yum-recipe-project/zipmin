package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FridgeSuccessCode implements Code {
	
	// 데이터 처리
	FRIDGE_CREATE_SUCCESS(HttpStatus.CREATED, "냉장고 작성 성공"),
	FRIDGE_READ_SUCCESS(HttpStatus.OK, "냉장고 조회 성공"),
	FRIDGE_UPDATE_SUCCESS(HttpStatus.OK, "냉장고 수정 성공"),
	FRIDGE_DELETE_SUCCESS(HttpStatus.OK, "냉장고 삭제 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
