package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode implements Code {
	
	REPORT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	REPORT_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	REPORT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 신고를 찾을 수 없음"),
	REPORT_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "신고 목록 조회 실패"),
	REPORT_CREATE_FAIL(HttpStatus.BAD_REQUEST, "신고 작성 실패"),
	REPORT_DELETE_FAIL(HttpStatus.BAD_REQUEST, "신고 삭제 실패"),
	
	REPORT_COUNT_FAIL(HttpStatus.BAD_REQUEST, "신고 집계 실패"),
	REPORT_EXIST_FAIL(HttpStatus.BAD_REQUEST, "신고 여부 확인 실패"),
	REPORT_DUPLICATE(HttpStatus.CONFLICT, "신고 중복 시도"),
	
	REPORT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리되지 않은 서버 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
	
}
