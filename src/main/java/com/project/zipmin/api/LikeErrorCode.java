package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements Code {
	
	LIKE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	LIKE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	LIKE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좋아요를 찾을 수 없음"),
	LIKE_READ_FAIL(HttpStatus.BAD_REQUEST, "좋아요 조회 실패"),
	LIKE_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "좋아요 목록 조회 실패"),
	LIKE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "좋아요 작성 실패"),
	LIKE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "좋아요 삭제 실패"),
	
	LIKE_COUNT_FAIL(HttpStatus.BAD_REQUEST, "좋아요 집계 실패"),
	LIKE_EXIST_FAIL(HttpStatus.BAD_REQUEST, "좋아요 여부 확인 실패"),
	LIKE_DUPLICATE(HttpStatus.CONFLICT, "좋아요 중복 시도"),
	
	LIKE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
