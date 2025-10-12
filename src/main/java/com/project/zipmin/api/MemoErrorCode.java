package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemoErrorCode implements Code {
	// 인증/인가
    MEMO_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
    MEMO_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
    
    // 입력값 오류
    MEMO_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
    MEMO_CONTENT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
    // 데이터 처리
    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메모를 찾을 수 없음"),
    MEMO_CREATE_FAIL(HttpStatus.BAD_REQUEST, "메모 작성 실패"),
    MEMO_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메모 목록 조회 실패"),
    MEMO_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "메모 수정 실패"),
    MEMO_DELETE_FAIL(HttpStatus.BAD_REQUEST, "메모 삭제 실패"),
    MEMO_COUNT_FAIL(HttpStatus.BAD_REQUEST, "메모 집계 실패"),
    MEMO_FROM_RECIPE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "레시피 재료를 메모로 담기 실패"),
    
    // 기타
    MEMO_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
