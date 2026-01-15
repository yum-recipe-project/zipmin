package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundErrorCode implements Code {

	FUND_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),			  
	FUND_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	FUND_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	FUND_INVALID_POINT(HttpStatus.BAD_REQUEST, "포인트 입력값이 유효하지 않음"),
	
	FUND_NOT_FOUND(HttpStatus.NOT_FOUND, "후원 정보를 찾을 수 없음"),
	FUND_CREATE_FAIL(HttpStatus.BAD_REQUEST, "후원 작성 실패"),
	FUND_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "후원 조회 실패"),
	FUND_READ_SUM_FAIL(HttpStatus.BAD_REQUEST, "후원 합계 조회 실패"),
	
	FUND_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류"); 

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
