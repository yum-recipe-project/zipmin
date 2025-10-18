package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewDeleteSuccessResponse")
public class ReviewDeleteSuccessResponse {
	
    @Schema(example = "REVIEW_DELETE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 삭제 성공", description = "성공 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}