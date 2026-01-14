package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundSuccessCode implements Code {
	
	// 데이터 처리
	FUND_CREATE_SUCCESS(HttpStatus.CREATED, "후원 작성 성공"),
	FUND_READ_LIST_SUCCESS(HttpStatus.OK, "후원 목록 조회 성공"),
	FUND_READ_SUM_SUCCESS(HttpStatus.OK, "후원 합계 조회 실패"),
	
	
	
	
	
    // 후원 처리
    FUND_REQUEST_SUCCESS(HttpStatus.OK, "후원 요청이 성공적으로 전송되었습니다."),
    FUND_COMPLETE_SUCCESS(HttpStatus.OK, "후원이 성공적으로 완료되었습니다."),

    // 포인트 처리
    FUND_POINT_USE_SUCCESS(HttpStatus.OK, "포인트 사용이 완료되었습니다."),
    FUND_POINT_REFUND_SUCCESS(HttpStatus.OK, "포인트 환불이 완료되었습니다."),
    FUND_POINT_BALANCE_READ_SUCCESS(HttpStatus.OK, "포인트 잔액 조회 성공"),

    // 후원 내역 조회
    FUND_STATUS_READ_SUCCESS(HttpStatus.OK, "후원 상태 조회 성공");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
