package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KitchenErrorCode implements Code {
	
	KITCHEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	KITCHEN_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	KITCHEN_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	KITCHEN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 키친가이드를 찾을 수 없음"),
	KITCHEN_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 목록 조회 실패"),
	KITCHEN_READ_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 조회 실패"),
    KITCHEN_CREATE_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 작성 실패"),
    KITCHEN_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 수정 실패"),
    KITCHEN_DELETE_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 삭제 실패"),
    
    KITCHEN_LIKE_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 좋아요 실패"),
    KITCHEN_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "키친가이드 좋아요 삭제 실패"),
	
	KITCHEN_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
