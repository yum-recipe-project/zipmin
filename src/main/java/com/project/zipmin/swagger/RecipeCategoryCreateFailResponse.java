package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeCategoryCreateFailResponse")
public class RecipeCategoryCreateFailResponse {
	
    @Schema(example = "RECIPE_CATEGORY_CREATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 카테고리 작성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
