package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeCreateSuccessResponse")
public class RecipeCreateSuccessResponse {
	
    @Schema(example = "RECIPE_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}