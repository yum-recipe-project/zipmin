package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemoSuccessCode implements Code {
	
	// 데이터 처리
    MEMO_CREATE_SUCCESS(HttpStatus.CREATED, "메모 작성 성공"),
    MEMO_READ_LIST_SUCCESS(HttpStatus.OK, "메모 조회 성공"),
    MEMO_UPDATE_SUCCESS(HttpStatus.OK, "메모 수정 성공"),
    MEMO_DELETE_SUCCESS(HttpStatus.OK, "메모 삭제 성공"),
    
    // 레시피 재료를 메모로 담기
    MEMO_FROM_RECIPE_CREATE_SUCCESS(HttpStatus.CREATED, "레시피 재료를 메모로 담기 성공");
	
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}



