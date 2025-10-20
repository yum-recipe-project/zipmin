package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundErrorCode implements Code {

    FUND_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),              
    FUND_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),

    // 후원
    FUND_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
    FUND_POINT_EXCEED(HttpStatus.PAYMENT_REQUIRED, "보유 포인트를 초과한 후원"),
    FUND_NOT_FOUND(HttpStatus.NOT_FOUND, "후원 정보를 찾을 수 없음."),   
    FUND_DUPLICATE_REQUEST(HttpStatus.CONFLICT, "중복 후원 요청."),   

    // 포인트
    FUND_POINT_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포인트 업데이트 실패"), 
    FUND_RECORD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "후원 실패"),  

    FUND_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류"); 

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
