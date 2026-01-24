package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportSuccessCode implements Code {

	REPORT_READ_LIST_SUCCESS(HttpStatus.OK, "신고 목록 조회 성공"),
	REPORT_CREATE_SUCCESS(HttpStatus.CREATED, "신고 작성 성공"),
	REPORT_DELETE_SUCCESS(HttpStatus.OK, "신고 삭제 성공"),
	REPORT_COUNT_SUCCESS(HttpStatus.OK, "신고 집계 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
