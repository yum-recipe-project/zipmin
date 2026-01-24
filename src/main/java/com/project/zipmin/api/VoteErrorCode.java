package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode implements Code {
	
	VOTE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	VOTE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자"),
	
	VOTE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	VOTE_INVALID_FILE(HttpStatus.BAD_REQUEST, "파일이 유효하지 않음"),
	VOTE_INVALID_PERIOD(HttpStatus.BAD_REQUEST, "기간이 유효하지 않음"),
	VOTE_RECORD_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	VOTE_CHOICE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 투표를 찾을 수 없음"),
	VOTE_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "투표 조회 실패"),
	VOTE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 작성 실패"),
	VOTE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "투표 수정 실패"),
	VOTE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 삭제 실패"),
	VOTE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "투표 파일 업로드 실패"),
	
	VOTE_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 투표 기록을 찾을 수 없음"),
	VOTE_RECORD_READ_FAIL(HttpStatus.BAD_REQUEST, "투표 기록 조회 실패"),
	VOTE_RECORD_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 기록 작성 실패"),
	VOTE_RECORD_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 기록 삭제 실패"),
	
	VOTE_CHOICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 투표 옵션을 찾을 수 없음"),
	VOTE_CHOICE_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 목록 조회 실패"),
	VOTE_CHOICE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 작성 실패"),
	VOTE_CHOICE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 수정 실패"),
	VOTE_CHOICE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 삭제 실패"),
	
	VOTE_NOT_OPENED(HttpStatus.FORBIDDEN, "투표 기간 외 접근 시도"),
	VOTE_RECORD_DUPLICATE(HttpStatus.CONFLICT, "투표 중복 참여 시도"),
	
	VOTE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리되지 않은 서버 내부 오류");

	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
	
}
