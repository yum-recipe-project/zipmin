package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChompSuccessCode implements Code {
	
	CHOMP_LIST_FETCH_SUCCESS(HttpStatus.OK, "쩝쩝박사 게시판 목록이 조회되었습니다.");
	
	
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
