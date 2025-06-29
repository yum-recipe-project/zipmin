package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MegazineErrorCode implements Code {
	
	// 인증/인가
	MEGAZINE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	MEGAZINE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	MEGAZINE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),

	// 데이터 처리
	MEGAZINE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 매거진을 찾을 수 없음"),
	MEGAZINE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "매거진 작성 실패"),
	MEGAZINE_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "매거진 조회 실패"),
	MEGAZINE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "매거진 수정 실패"),
	MEGAZINE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "매거진 삭제 실패"),
	
	// 기타
	MEGAZINE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
