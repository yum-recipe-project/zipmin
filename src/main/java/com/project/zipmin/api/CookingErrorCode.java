package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookingErrorCode implements Code {
	
	// 인증/인가
	COOKING_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	COOKING_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	COOKING_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	COOKING_TARGET_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	COOKING_SCHEDULE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	COOKING_TUTOR_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	COOKING_APPLY_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	COOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 쿠킹클래스를 찾을 수 없음"),
    COOKING_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 작성 실패"),
    COOKING_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쿠킹클래스 목록 조회 실패"),
    COOKING_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쿠킹클래스 조회 실패"),
    COOKING_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 수정 실패"),
    COOKING_DELETE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 삭제 실패"),
    
    COOKING_TARGET_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 교육대상을 찾을 수 없음"),
    COOKING_TARGET_CREATE_FAIL(HttpStatus.BAD_REQUEST, "교육대상 작성 실패"),
    COOKING_TARGET_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "교육대상 목록 조회 실패"),
    COOKING_TARGET_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "교육대상 조회 실패"),
    COOKING_TARGET_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "교육대상 수정 실패"),
    COOKING_TARGET_DELETE_FAIL(HttpStatus.BAD_REQUEST, "교육대상 삭제 실패"),
    
    COOKING_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 교육일정을 찾을 수 없음"),
    COOKING_SCHEDULE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "교육일정 작성 실패"),
    COOKING_SCHEDULE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "교육일정 목록 조회 실패"),
    COOKING_SCHEDULE_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "교육일정 조회 실패"),
    COOKING_SCHEDULE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "교육일정 수정 실패"),
    COOKING_SCHEDULE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "교육일정 삭제 실패"),
    
    COOKING_TUTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 강사를 찾을 수 없음"),
    COOKING_TUTOR_CREATE_FAIL(HttpStatus.BAD_REQUEST, "강사 작성 실패"),
    COOKING_TUTOR_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "강사 목록 조회 실패"),
    COOKING_TUTOR_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "강사 조회 실패"),
    COOKING_TUTOR_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "강사 수정 실패"),
    COOKING_TUTOR_DELETE_FAIL(HttpStatus.BAD_REQUEST, "강사 삭제 실패"),
    
    COOKING_APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 신청를 찾을 수 없음"),
    COOKING_APPLY_CREATE_FAIL(HttpStatus.BAD_REQUEST, "신청 작성 실패"),
    COOKING_APPLY_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "신청 목록 조회 실패"),
    COOKING_APPLY_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "신청 조회 실패"),
    COOKING_APPLY_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "신청 수정 실패"),
    COOKING_APPLY_DELETE_FAIL(HttpStatus.BAD_REQUEST, "신청 삭제 실패"),
    COOKING_APPLY_DUPLICATE(HttpStatus.CONFLICT, "신청 중복 작성 시도"),
	
	// 기타
	COOKING_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
