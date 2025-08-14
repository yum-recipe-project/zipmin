package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChompErrorCode implements Code {
	
	// 인증/인가
	CHOMP_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	CHOMP_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	CHOMP_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),

	// 데이터 처리
	CHOMP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 쩝쩝박사 게시물을 찾을 수 없음"),
	CHOMP_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쩝쩝박사 게시물 작성 실패"),
	CHOMP_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쩝쩝박사 게시물 조회 실패"),
	CHOMP_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쩝쩝박사 게시물 목록 조회 실패"),
	CHOMP_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "쩝쩝박사 게시물 수정 실패"),
	CHOMP_DELETE_FAIL(HttpStatus.BAD_REQUEST, "쩝쩝박사 게시물 삭제 실패"),
	
	// 기타
	CHOMP_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
