package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChompSuccessCode implements Code {
	
	CHOMP_READ_LIST_SUCCESS(HttpStatus.OK, "매거진 목록 조회 성공");	
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
