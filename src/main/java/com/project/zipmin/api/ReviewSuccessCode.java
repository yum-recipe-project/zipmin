 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements Code {
	
    REVIEW_READ_LIST_SUCCESS(HttpStatus.OK, "리뷰 목록 조회 성공"),
    REVIEW_READ_SUCCESS(HttpStatus.OK, "리뷰 조회 성공"),
    REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "리뷰 작성 성공"),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰 수정 성공"),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰 삭제 성공"),
    
    REVIEW_LIKE_SUCCESS(HttpStatus.OK, "리뷰 좋아요 성공"),
    REVIEW_UNLIKE_SUCCESS(HttpStatus.OK, "리뷰 좋아요 취소 성공"),
    REVIEW_REPORT_SUCCESS(HttpStatus.OK, "리뷰 신고 성공");
    
    private final HttpStatus status;
    private final String message;
    
    @Override
    public String getCode() {
        return this.name();
    }
}
