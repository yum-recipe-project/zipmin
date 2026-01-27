package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeStepCreateFailResponse")
public class RecipeStepCreateFailResponse {
	
    @Schema(example = "RECIPE_STEP_CREATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 조리 순서 작성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
