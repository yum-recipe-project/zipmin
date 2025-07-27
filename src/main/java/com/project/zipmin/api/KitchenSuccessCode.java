package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KitchenSuccessCode implements Code {
	
	KITCHEN_READ_LIST_SUCCESS(HttpStatus.OK, "키친가이드 목록 조회 성공"),
	KITCHEN_READ_SUCCESS(HttpStatus.OK, "키친가이드 조회 성공"),
	KITCHEN_CREATE_SUCCESS(HttpStatus.CREATED, "키친가이드 작성 성공"),
	KITCHEN_UPDATE_SUCCESS(HttpStatus.OK, "키친가이드 수정 성공"),
    KITCHEN_DELETE_SUCCESS(HttpStatus.OK, "키친가이드 삭제 성공"),
	
    KITCHEN_LIKE_SUCCESS(HttpStatus.OK, "키친가이드 좋아요 성공"),
    KITCHEN_UNLIKE_SUCCESS(HttpStatus.OK, "키친가이드 좋아요 취소 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
