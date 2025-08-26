package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeCategoryDeleteFailResponse")
public class RecipeCategoryDeleteFailResponse {
	
    @Schema(example = "RECIPE_CATEGORY_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "카테고리 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
