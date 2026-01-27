package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeUnlikeSuccessResponse")
public class RecipeUnlikeSuccessResponse {
	
    @Schema(example = "RECIPE_UNLIKE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 좋아요 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}