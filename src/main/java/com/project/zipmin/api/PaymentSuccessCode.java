package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentSuccessCode implements Code {

	// 결제 처리
    PAYMENT_REQUEST_SUCCESS(HttpStatus.OK, "결제 요청이 성공적으로 전송되었습니다."),
    PAYMENT_VERIFY_SUCCESS(HttpStatus.OK, "결제 검증이 완료되었습니다."),
    PAYMENT_COMPLETE_SUCCESS(HttpStatus.OK, "결제가 성공적으로 완료되었습니다."),
    PAYMENT_CANCEL_SUCCESS(HttpStatus.OK, "결제가 정상적으로 취소되었습니다."),

    // 포인트 처리
    POINT_CHARGE_SUCCESS(HttpStatus.CREATED, "포인트 충전이 완료되었습니다."),
    POINT_USE_SUCCESS(HttpStatus.OK, "포인트 사용이 완료되었습니다."),
    POINT_REFUND_SUCCESS(HttpStatus.OK, "포인트 환불이 완료되었습니다."),
    POINT_BALANCE_READ_SUCCESS(HttpStatus.OK, "포인트 잔액 조회 성공"),

    PAYMENT_STATUS_READ_SUCCESS(HttpStatus.OK, "결제 상태 조회 성공"),
    PAYMENT_HISTORY_READ_SUCCESS(HttpStatus.OK, "결제 내역 조회 성공");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
