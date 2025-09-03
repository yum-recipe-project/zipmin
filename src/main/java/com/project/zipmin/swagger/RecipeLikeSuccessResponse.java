package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeLikeSuccessResponse")
public class RecipeLikeSuccessResponse {
	
    @Schema(example = "RECIPE_LIKE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 좋아요 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}