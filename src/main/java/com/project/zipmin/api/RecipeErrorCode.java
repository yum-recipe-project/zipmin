package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeErrorCode implements Code {
	
	// 인증/인가
	RECIPE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인되지 않은 사용자"),
	RECIPE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없는 사용자의 접근"),
	
	// 입력값 오류
	RECIPE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	RECIPE_STEP_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	RECIPE_CATEGORY_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	RECIPE_STOCK_INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않음"),
	
	// 데이터 처리
	RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피를 찾을 수 없음"),
    RECIPE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "레시피 작성 실패"),
    RECIPE_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 목록 조회 실패"),
    RECIPE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "레시피 삭제 실패"),
    RECIPE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 파일 업로드 실패"),
    
    RECIPE_STEP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피 조리 순서를 찾을 수 없음"),
    RECIPE_STEP_CREATE_FAIL(HttpStatus.BAD_REQUEST, "레시피 조리 순서 작성 실패"),
    RECIPE_STEP_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 조리 순서 목록 조회 실패"),
    RECIPE_STEP_DELETE_FAIL(HttpStatus.BAD_REQUEST, "레시피 조리 순서 삭제 실패"),
    RECIPE_STEP_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 조리 순서 파일 업로드 실패"),
    
    RECIPE_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피 카테고리를 찾을 수 없음"),
    RECIPE_CATEGORY_CREATE_FAIL(HttpStatus.BAD_REQUEST, "레시피 카테고리 작성 실패"),
    RECIPE_CATEGORY_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 카테고리 목록 조회 실패"),
    RECIPE_CATEGORY_DELETE_FAIL(HttpStatus.BAD_REQUEST, "레시피 카테고리 삭제 실패"),
    
    RECIPE_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피 재료를 찾을 수 없음"),
    RECIPE_STOCK_READ_LIST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "레시피 재료 목록 조회 실패"),
    RECIPE_STOCK_CREATE_FAIL(HttpStatus.BAD_REQUEST, "레시피 재료 작성 실패"),
    RECIPE_STOCK_DELETE_FAIL(HttpStatus.BAD_REQUEST, "레시피 재료 삭제 실패"),
    
    RECIPE_LIKE_FAIL(HttpStatus.BAD_REQUEST, "레시피 좋아요 실패"),
    RECIPE_UNLIKE_FAIL(HttpStatus.BAD_REQUEST, "레시피 좋아요 삭제 실패"),
    RECIPE_REPORT_FAIL(HttpStatus.BAD_REQUEST, "레시피 신고 실패"),
    RECIPE_UNREPORT_FAIL(HttpStatus.BAD_REQUEST, "레시피 신고 삭제 실패"),
	
	// 기타
	RECIPE_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예외 처리되지 않은 내부 오류");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
