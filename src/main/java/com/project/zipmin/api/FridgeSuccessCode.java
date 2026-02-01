package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FridgeSuccessCode implements Code {
	
	FRIDGE_READ_LIST_SUCCESS(HttpStatus.OK, "냉장고 목록 조회 성공"),
	FRIDGE_READ_SUCCESS(HttpStatus.OK, "냉장고 조회 성공"),
	FRIDGE_CREATE_SUCCESS(HttpStatus.CREATED, "냉장고 작성 성공"),
	FRIDGE_UPDATE_SUCCESS(HttpStatus.OK, "냉장고 수정 성공"),
	FRIDGE_DELETE_SUCCESS(HttpStatus.OK, "냉장고 삭제 성공"),
	
	FRIDGE_STORAGE_READ_LIST_SUCCESS(HttpStatus.OK, "나의 냉장고 목록 조회 성공"),
	FRIDGE_STORAGE_READ_SUCCESS(HttpStatus.OK, "나의 냉장고 조회 성공"),
	FRIDGE_STORAGE_CREATE_SUCCESS(HttpStatus.CREATED, "나의 냉장고 작성 성공"),
	FRIDGE_STORAGE_UPDATE_SUCCESS(HttpStatus.OK, "나의 냉장고 수정 성공"),
	FRIDGE_STORAGE_DELETE_SUCCESS(HttpStatus.OK, "나의 냉장고 삭제 성공"),
	
	FRIDGE_MEMO_CREATE_SUCCESS(HttpStatus.CREATED, "장보기 메모 작성 성공"),
	FRIDGE_MEMO_READ_LIST_SUCCESS(HttpStatus.OK, "장보기 메모 조회 성공"),
	FRIDGE_MEMO_UPDATE_SUCCESS(HttpStatus.OK, "장보기 메모 수정 성공"),
	FRIDGE_MEMO_DELETE_SUCCESS(HttpStatus.OK, "장보기 메모 삭제 성공"),
	
	// FRIDGE_PICK_LIST_SUCCESS(HttpStatus.OK, "냉장고 파먹기 성공"),
	
	FRIDGE_LIKE_SUCCESS(HttpStatus.OK, "댓글 좋아요 성공"),
	FRIDGE_UNLIKE_SUCCESS(HttpStatus.OK, "댓글 좋아요 취소 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
