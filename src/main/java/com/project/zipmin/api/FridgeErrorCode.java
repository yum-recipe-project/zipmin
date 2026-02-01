package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FridgeErrorCode implements Code {
	
	FRIDGE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	FRIDGE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	FRIDGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	FRIDGE_STORAGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	FRIDGE_MEMO_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	FRIDGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 냉장고를 찾을 수 없음"),
	FRIDGE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 작성 실패"),
	FRIDGE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "냉장고 목록 조회 실패"),
	FRIDGE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 수정 실패"),
	FRIDGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 삭제 실패"),
	FRIDGE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "냉장고 파일 업로드 실패"),
	
	FRIDGE_STORAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 나의 냉장고를 찾을 수 없음"),
	FRIDGE_STORAGE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "나의 냉장고 작성 실패"),
	FRIDGE_STORAGE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "나의 냉장고 목록 조회 실패"),
	FRIDGE_STORAGE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "나의 냉장고 수정 실패"),
	FRIDGE_STORAGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "나의 냉장고 삭제 실패"),
	FRIDGE_STORAGE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "나의 냉장고 파일 업로드 실패"),
	
	FRIDGE_MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장보기 메모를 찾을 수 없음"),
	FRDIGE_MEMO_CREATE_FAIL(HttpStatus.BAD_REQUEST, "장보기 메모 작성 실패"),
	FRIDGE_MEMO_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 메모 목록 조회 실패"),
	FRIDGE_MEMO_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "장보기 메모 수정 실패"),
	FRIDGE_MEMO_DELETE_FAIL(HttpStatus.BAD_REQUEST, "장보기 메모 삭제 실패"),
	// MEMO_COUNT_FAIL(HttpStatus.BAD_REQUEST, "메모 집계 실패"),
	
	FRIDGE_LIKE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 좋아요 실패"),
	FRIDGE_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 좋아요 삭제 실패"),

	FRIDGE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
