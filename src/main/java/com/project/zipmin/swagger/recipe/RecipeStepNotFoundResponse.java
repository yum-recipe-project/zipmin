package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeStepNotFoundResponse")
public class RecipeStepNotFoundResponse {
	
    @Schema(example = "RECIPE_STEP_NOT_FOUND_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "해당 레시피 조리 순서를 찾을 수 없음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}