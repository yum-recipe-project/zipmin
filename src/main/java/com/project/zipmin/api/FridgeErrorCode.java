package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FridgeErrorCode implements Code {
	
	// 인증/인가
	FRIDGE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	FRIDGE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	USER_FRIDGE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	USER_FRIDGE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	FRIDGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	USER_FRIDGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	FRIDGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 냉장고를 찾을 수 없음"),
    FRIDGE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 작성 실패"),
    FRIDGE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "냉장고 목록 조회 실패"),
    FRIDGE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 수정 실패"),
    FRIDGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "냉장고 삭제 실패"),
    FRIDGE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "냉장고 파일 업로드 실패"),
    
    USER_FRIDGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자 냉장고를 찾을 수 없음"),
    USER_FRIDGE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "사용자 냉장고 작성 실패"),
    USER_FRIDGE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 냉장고 목록 조회 실패"),
    USER_FRIDGE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "사용자 냉장고 수정 실패"),
    USER_FRIDGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "사용자 냉장고 삭제 실패"),

    // 기타
	FRIDGE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
