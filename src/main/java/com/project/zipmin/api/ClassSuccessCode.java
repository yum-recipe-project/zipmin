 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClassSuccessCode implements Code {
	
	// 데이터 처리
	CLASS_READ_LIST_SUCCESS(HttpStatus.OK, "클래스 목록 조회 성공"),
	CLASS_READ_SUCCESS(HttpStatus.OK, "클래스 조회 성공"),
	CLASS_CREATE_SUCCESS(HttpStatus.CREATED, "클래스 작성 성공"),
    CLASS_UPDATE_SUCCESS(HttpStatus.OK, "클래스 수정 성공"),
    CLASS_DELETE_SUCCESS(HttpStatus.OK, "클래스 삭제 성공"),
    CLASS_UPDATE_APPROVAL_SUCCESS(HttpStatus.OK, "클래스 승인 수정 성공"),
    
    CLASS_APPLY_READ_LIST_SUCCESS(HttpStatus.OK, "클래스 신청 목록 조회 성공"),
    CLASS_APPLY_READ_SUCCESS(HttpStatus.OK, "신청 조회 성공"),
    CLASS_APPLY_CREATE_SUCCESS(HttpStatus.CREATED, "신청 작성 성공"),
    CLASS_APPLY_UPDATE_SUCCESS(HttpStatus.OK, "신청 수정 성공"),
    CLASS_APPLY_DELETE_SUCCESS(HttpStatus.OK, "신청 삭제 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
