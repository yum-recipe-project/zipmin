package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WithdrawSuccessCode implements Code {
	
	// 데이터 처리
	WITHDRAW_CREATE_SUCCESS(HttpStatus.CREATED, "출금 작성 성공"),
	WITHDRAW_READ_LIST_SUCCESS(HttpStatus.OK, "출금 목록 조회 성공"),
	
	
	
	
	;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
