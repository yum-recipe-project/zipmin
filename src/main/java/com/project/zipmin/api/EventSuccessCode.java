package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventSuccessCode implements Code {
	
	// 데이터 처리
	EVENT_CREATE_SUCCESS(HttpStatus.CREATED, "이벤트 작성 성공"),
	EVENT_READ_SUCCESS(HttpStatus.OK, "이벤트 조회 성공"),
	EVENT_UPDATE_SUCCESS(HttpStatus.OK, "이벤트 수정 성공"),
	EVENT_DELETE_SUCCESS(HttpStatus.OK, "이벤트 삭제 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
