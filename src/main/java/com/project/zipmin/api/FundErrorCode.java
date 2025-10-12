package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundErrorCode implements Code {

    // 인증/인가
    FUND_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FUND_FORBIDDEN(HttpStatus.FORBIDDEN, "후원 요청 권한이 없습니다."),

    // 후원 요청 및 검증 오류
    FUND_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 후원 요청입니다."),
    FUND_POINT_EXCEED(HttpStatus.BAD_REQUEST, "보유 포인트를 초과한 후원입니다."),
    FUND_NOT_FOUND(HttpStatus.NOT_FOUND, "후원 정보를 찾을 수 없습니다."),
    FUND_DUPLICATE_REQUEST(HttpStatus.CONFLICT, "중복 후원 요청이 감지되었습니다."),

    // 포인트 처리 관련 오류
    FUND_POINT_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "포인트 업데이트 중 오류가 발생했습니다."),
    FUND_RECORD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "후원 기록 생성 중 오류가 발생했습니다."),

    FUND_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 후원 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
