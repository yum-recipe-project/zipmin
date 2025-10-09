package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements Code {

    // 인증/인가
    PAYMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    PAYMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "결제 요청 권한이 없습니다."),

    // 결제 요청 및 검증 오류
    PAYMENT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 요청입니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "결제 금액이 일치하지 않습니다."),
    PAYMENT_STATUS_INVALID(HttpStatus.BAD_REQUEST, "결제 상태가 유효하지 않습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_DUPLICATE_REQUEST(HttpStatus.CONFLICT, "중복 결제 요청이 감지되었습니다."),

    // 포트원 관련 오류
    PAYMENT_TOKEN_ISSUE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포트원 API 토큰 발급 실패"),
    PAYMENT_LOOKUP_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포트원 결제내역 조회 실패"),
    PAYMENT_VERIFICATION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포트원 결제 검증 실패"),

    // 포인트 처리 관련 오류
    POINT_CHARGE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포인트 충전에 실패했습니다."),
    POINT_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포인트 업데이트 중 오류가 발생했습니다."),

    PAYMENT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 결제 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
