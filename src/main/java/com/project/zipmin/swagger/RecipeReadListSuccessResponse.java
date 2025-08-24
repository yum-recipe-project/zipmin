package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeReadListSuccessResponse")
public class RecipeReadListSuccessResponse {
	
    @Schema(example = "RECIPE_READ_LIST_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 목록 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}