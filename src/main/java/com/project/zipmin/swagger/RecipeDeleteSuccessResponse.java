package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeDeleteSuccessResponse")
public class RecipeDeleteSuccessResponse {
	
    @Schema(example = "RECIPE_DELETE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}