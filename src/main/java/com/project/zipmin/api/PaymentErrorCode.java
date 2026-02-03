package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements Code {

    PAYMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
    PAYMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),

    PAYMENT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
    PAYMENT_INVALID_POINT(HttpStatus.BAD_REQUEST, "포인트가 유효하지 않음"),
    PAYMENT_INVALID_STATUS(HttpStatus.BAD_REQUEST, "상태가 유효하지 않음"),
    
    PAYMENT_LOOKUP_FAIL(HttpStatus.BAD_REQUEST, "포트원 결제 내역 조회 실패");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
