package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MegazineSuccessCode implements Code {
	
	// 데이터 처리
	MEGAZINE_CREATE_SUCCESS(HttpStatus.CREATED, "매거진 작성 성공"),
	MEGAZINE_READ_SUCCESS(HttpStatus.OK, "매거진 조회 성공"),
	MEGAZINE_UPDATE_SUCCESS(HttpStatus.OK, "매거진 수정 성공"),
	MEGAZINE_DELETE_SUCCESS(HttpStatus.OK, "매거진 삭제 성공");	
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
