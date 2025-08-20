package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode implements Code {
	
	// 인증/인가
	VOTE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	VOTE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	VOTE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	VOTE_INVALID_PERIOD(HttpStatus.BAD_REQUEST, "투표 기간 설정이 유효하지 않음"),
	VOTE_RECORD_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	VOTE_CHOICE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 투표를 찾을 수 없음"),
	VOTE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 작성 실패"),
	VOTE_READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "투표 조회 실패"),
	VOTE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "투표 수정 실패"),
	VOTE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 삭제 실패"),
	
	VOTE_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 투표 기록을 찾을 수 없음"),
	VOTE_RECORD_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 기록 작성 실패"),
	VOTE_RECORD_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 기록 삭제 실패"),
	
	VOTE_CHOICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 투표 옵션을 찾을 수 없음"),
	VOTE_CHOICE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 작성 실패"),
	VOTE_CHOICE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 수정 실패"),
	VOTE_CHOICE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "투표 옵션 삭제 실패"),
	
	// 비즈니스 로직
	// VOTE_PATICIPATE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "기간 외 참여 시도"),
	VOTE_NOT_STARTED(HttpStatus.FORBIDDEN, "투표 시작 전 접근 시도"),
	VOTE_ALREADY_ENDED(HttpStatus.FORBIDDEN, "투표 종료 후 접근 시도"),
	VOTE_RECORD_DUPLICATE(HttpStatus.CONFLICT, "투표 중복 참여 시도"),
	
	// 기타
	VOTE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");

	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
	
}
