package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundSuccessCode implements Code {
	
	FUND_CREATE_SUCCESS(HttpStatus.CREATED, "후원 작성 성공"),
	FUND_READ_LIST_SUCCESS(HttpStatus.OK, "후원 목록 조회 성공"),
	FUND_READ_SUM_SUCCESS(HttpStatus.OK, "후원 합계 조회 성공");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
