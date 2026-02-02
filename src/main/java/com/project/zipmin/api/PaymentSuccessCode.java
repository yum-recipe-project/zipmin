package com.project.zipmin.api;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentSuccessCode implements Code {
	
	PAYMENT_CREATE_SUCCESS(HttpStatus.OK, "결제 작성 성공");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
