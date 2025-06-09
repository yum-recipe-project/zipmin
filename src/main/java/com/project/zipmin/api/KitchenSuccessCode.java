package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KitchenSuccessCode implements Code {
	
	KITCHEN_LIST_FETCH_SUCCESS(HttpStatus.OK, "키친가이드 게시판 목록이 조회되었습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
