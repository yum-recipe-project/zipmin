package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WithdrawErrorCode implements Code {

	WITHDRAW_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),			  
	WITHDRAW_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	WITHDRAW_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	WITHDRAW_INVALID_POINT(HttpStatus.BAD_REQUEST, "포인트 입력값이 유효하지 않음"),
	
	WITHDRAW_NOT_FOUND(HttpStatus.NOT_FOUND, "출금 정보를 찾을 수 없음"),
	WITHDRAW_CREATE_FAIL(HttpStatus.BAD_REQUEST, "출금 작성 실패"),
	WITHDRAW_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "출금 목록 조회 실패"),
	WITHDRAW_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "출금 수정 실패"),
	WITHDRAW_DELETE_FAIL(HttpStatus.BAD_REQUEST, "출금 삭제 실패"),
	
	WITHDRAW_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류"); 

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
