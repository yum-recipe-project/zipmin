package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements Code {
	
	// 인증/인가
    REVIEW_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
    REVIEW_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
    
    // 입력값 오류
    REVIEW_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
    
    // 데이터 처리
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없음"),
    REVIEW_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "리뷰 목록 조회 실패"),
    REVIEW_READ_COUNT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "리뷰 개수 조회 실패"),
    REVIEW_CREATE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 작성 실패"),
    REVIEW_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 수정 실패"),
    REVIEW_DELETE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 삭제 실패"),
    
    REVIEW_LIKE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 좋아요 실패"),
    REVIEW_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 좋아요 삭제 실패"),
    REVIEW_REPORT_FAIL(HttpStatus.BAD_REQUEST, "리뷰 신고 실패"),
    REVIEW_UNREPORT_FAIL(HttpStatus.BAD_REQUEST, "리뷰 신고 삭제 실패"),
    
    // 기타
    REVIEW_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
    
    private final HttpStatus status;
    private final String message;
    
    @Override
    public String getCode() {
        return this.name();
    }

}
