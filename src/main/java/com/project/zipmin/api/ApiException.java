package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	private final Code code;
	
	public ApiException(Code code) {
		super(code.getMessage());
		this.code = code;
	}
	
	public Code getCode() {
		return code;
	}
}