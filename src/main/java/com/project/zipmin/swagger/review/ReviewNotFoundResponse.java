package com.project.zipmin.swagger.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewNotFoundResponse")
public class ReviewNotFoundResponse {
	
    @Schema(example = "REVIEW_NOT_FOUND", description = "에러 코드")
    public String code;

    @Schema(example = "해당 리뷰를 찾을 수 없음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
