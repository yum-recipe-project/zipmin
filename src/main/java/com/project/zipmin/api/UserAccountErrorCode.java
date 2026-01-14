package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAccountErrorCode implements Code {
	
	// 인증
	USER_ACCOUNT_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	USER_ACCOUNT_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	USER_ACCOUNT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	USER_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 계좌를 찾을 수 없음"),
	USER_ACCOUNT_CREATE_FAIL(HttpStatus.BAD_REQUEST, "계좌 작성 실패"),
	USER_ACCOUNT_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "계좌 조회 실패"),
	USER_ACCOUNT_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "계좌 수정 실패"),
	USER_ACCOUNT_DELETE_FAUL(HttpStatus.BAD_REQUEST, "계좌 삭제 실패"),

	// 기타
	USER_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}

	
	