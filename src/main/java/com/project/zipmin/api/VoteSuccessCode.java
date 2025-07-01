package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteSuccessCode implements Code {
	
	// 데이터 처리
	VOTE_CREATE_SUCCESS(HttpStatus.CREATED, "투표 등록 성공"),
	VOTE_READ_SUCCESS(HttpStatus.OK, "투표 조회 성공"),
	VOTE_UPDATE_SUCCESS(HttpStatus.OK, "투표 수정 성공"),
	VOTE_DELETE_SUCCESS(HttpStatus.OK, "투표 삭제 성공"),
	VOTE_RECORD_SUCCESS(HttpStatus.OK, "투표 기록 작성 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
