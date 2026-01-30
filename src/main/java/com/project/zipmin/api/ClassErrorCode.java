package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClassErrorCode implements Code {
	
	CLASS_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	CLASS_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	CLASS_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CLASS_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 작성 실패"),
	CLASS_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 목록 조회 실패"),
	CLASS_READ_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 조회 실패"),
	CLASS_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 수정 실패"),
	CLASS_UPDATE_APPROVAL_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 승인 수정 실패"),
	CLASS_DELETE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 삭제 실패"),
	CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 쿠킹클래스를 찾을 수 없음"),
	CLASS_CREATE_DUPLICATE(HttpStatus.CONFLICT, "쿠킹클래스 중복 작성 시도"),
	CLASS_ALREADY_ENDED(HttpStatus.FORBIDDEN, "쿠킹클래스 종료 후 접근 시도"),
	CLASS_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쿠킹클래스 이미지 업로드 실패"),
	
	CLASS_TARGET_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CLASS_TARGET_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 교육대상 작성 실패"),
	CLASS_TARGET_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 교육대상 목록 조회 실패"),
	
	CLASS_SCHEDULE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CLASS_SCHEDULE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 교육일정 작성 실패"),
	CLASS_SCHEDULE_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 교육일정 목록 조회 실패"),
	
	CLASS_TUTOR_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CLASS_TUTOR_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 강사 작성 실패"),
	CLASS_TUTOR_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 강사 목록 조회 실패"),
	CLASS_TUTOR_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "쿠킹클래스 강사 이미지 업로드 실패"),
	
	CLASS_APPLY_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	CLASS_APPLY_CREATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 신청 작성 실패"),
	CLASS_APPLY_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 신청 목록 조회 실패"),
	CLASS_APPLY_READ_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 신청 조회 실패"),
	CLASS_APPLY_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 신청 수정 실패"),
	CLASS_APPLY_DELETE_FAIL(HttpStatus.BAD_REQUEST, "쿠킹클래스 신청 삭제 실패"),
	CLASS_APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 쿠킹클래스 신청을 찾을 수 없음"),
	CLASS_APPLY_DUPLICATE(HttpStatus.CONFLICT, "쿠킹클래스 신청 중복 작성 시도"),
	CLASS_APPLY_UNABLE(HttpStatus.FORBIDDEN, "쿠킹클래스 신청 작성 불가"),
	
	CLASS_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
