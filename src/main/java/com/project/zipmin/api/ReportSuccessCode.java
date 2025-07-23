package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportSuccessCode implements Code {

	// 데이터 처리
	REPORT_CREATE_SUCCESS(HttpStatus.CREATED, "신고 작성 성공"),
	REPORT_DELETE_SUCCESS(HttpStatus.OK, "신고 삭제 성공"),
	REPORT_COUNT_READ_SUCCESS(HttpStatus.OK, "신고 개수 조회 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
