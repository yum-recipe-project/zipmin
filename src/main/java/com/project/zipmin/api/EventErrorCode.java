package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventErrorCode implements Code {
	
	// 인증/인가
	EVENT_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	EVENT_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	EVENT_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	EVENT_INVALID_FILE(HttpStatus.BAD_REQUEST, "파일이 유효하지 않음"),
	EVENT_INVALID_PERIOD(HttpStatus.BAD_REQUEST, "기간이 유효하지 않음"),
	
	// 데이터 처리
	EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이벤트를 찾을 수 없음"),
	EVENT_CREATE_FAIL(HttpStatus.BAD_REQUEST, "이벤트 작성 실패"),
	EVENT_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이벤트 조회 실패"),
	EVENT_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "이벤트 수정 실패"),
	EVENT_DELETE_FAIL(HttpStatus.BAD_REQUEST, "이벤트 삭제 실패"),
	EVENT_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이벤트 파일 업로드 실패"),
	
	// 기타
	EVENT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");

	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}