package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements Code {
	
	// 인증/인가
	LIKE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	LIKE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	LIKE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좋아요를 찾을 수 없음"),
	LIKE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "좋아요 작성 실패"),
	LIKE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "좋아요 삭제 실패"),
	LIKE_COUNT_READ_FAIL(HttpStatus.BAD_REQUEST, "좋아요 개수 조회 실패"),
	
	// 비즈니스 로직
	LIKE_DUPLICATE(HttpStatus.CONFLICT, "좋아요 중복 작성 시도"),
	
	// 기타
	LIKE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
