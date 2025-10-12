package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements Code {
	
	// 인증/인가
	USER_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	USER_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	USER_SUPER_ADMIN_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	USER_ADMIN_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	USER_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	USER_PASSWORD_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없음"),
	USER_CREATE_FAIL(HttpStatus.BAD_REQUEST, "사용자 작성 실패"),
	USER_READ_LIST_FAIL(HttpStatus.BAD_REQUEST, "사용자 목록 조회 실패"),
	USER_READ_FAIL(HttpStatus.BAD_REQUEST, "사용자 조회 실패"),
	USER_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "사용자 수정 실패"),
	USER_DELETE_FAIL(HttpStatus.BAD_REQUEST, "사용자 삭제 실패"),
	USER_RESET_PASSWORD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "임시 비밀번호 발급 실패"),
	USER_INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호 불일치"),
	
	USER_READ_RECIPE_LIST_FAIL(HttpStatus.BAD_REQUEST, "사용자 레시피 목록 조회 실패"),
	USER_READ_CLASS_LIST_FAIL(HttpStatus.BAD_REQUEST, "사용자 클래스 목록 조회 실패"),
	
	USER_ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자 출금 계좌 조회 실패"),
	USER_CREATE_ACCOUNT_FAIL(HttpStatus.BAD_REQUEST, "사용자 출금 계좌 등록 실패"),
	USER_UPDATE_ACCOUNT_FAIL(HttpStatus.BAD_REQUEST, "사용자 출금 계좌 수정 실패"),

	USER_WITHDRAW_HISTORY_READ_FAIL(HttpStatus.BAD_REQUEST, "사용자 출금내역 조회 실패"),
	USER_WITHDRAW_REQUEST_FAIL(HttpStatus.BAD_REQUEST, "사용자 출금 신청 실패"),

	USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "아이디 중복 작성 시도"),
	USER_TEL_DUPLICATED(HttpStatus.CONFLICT, "전화번호 중복 작성 시도"),
	USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이메일 중복 작성 시도"),
	
    USER_LIKE_FAIL(HttpStatus.BAD_REQUEST, "사용자 좋아요 실패"),
    USER_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "사용자 좋아요 삭제 실패"),
    
    USER_SEND_EMAIL_FAIL(HttpStatus.BAD_REQUEST, "사용자 이메일 전송 실패"),
    
    USER_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "해당 사용자 토큰을 찾을 수 없음"),
    USER_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "사용자 토큰이 유효하지 않음"),
    USER_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "사용자 토큰 만료"),
    USER_TOKEN_UPDATE_FAIL(HttpStatus.UNAUTHORIZED, "사용자 토큰 수정 실패"),

	// 기타
	USER_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}

	
	