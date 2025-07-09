 package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeSuccessCode implements Code {
	
	// 데이터 처리
	RECIPE_READ_LIST_SUCCESS(HttpStatus.OK, "레시피 목록 조회 성공"),
	RECIPE_READ_SUCCESS(HttpStatus.OK, "레시피 조회 성공"),
	RECIPE_CREATE_SUCCESS(HttpStatus.CREATED, "레시피 작성 성공."),
    RECIPE_UPDATE_SUCCESS(HttpStatus.OK, "레시피 수정 성공"),
    RECIPE_DELETE_SUCCESS(HttpStatus.OK, "레시피 삭제 성공"),
    
    RECIPE_STEP_READ_LIST_SUCCESS(HttpStatus.OK, "조리 과정 목록 조회 성공"),
    RECIPE_STEP_READ_SUCCESS(HttpStatus.OK, "조리 과정 조회 성공"),
    RECIPE_STEP_CREATE_SUCCESS(HttpStatus.CREATED, "조리 과정 작성 성공."),
    RECIPE_STEP_UPDATE_SUCCESS(HttpStatus.OK, "조리 과정 수정 성공"),
    RECIPE_STEP_DELETE_SUCCESS(HttpStatus.OK, "조리 과정 삭제 성공"),
    
    RECIPE_LIKE_SUCCESS(HttpStatus.OK, "레시피 좋아요 성공"),
    RECIPE_UNLIKE_SUCCESS(HttpStatus.OK, "레시피 좋아요 취소 성공"),
    RECIPE_REPORT_SUCCESS(HttpStatus.OK, "레시피 신고 성공"),
    RECIPE_UNREPORT_SUCCESS(HttpStatus.OK, "레시피 신고 취소 성공");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
