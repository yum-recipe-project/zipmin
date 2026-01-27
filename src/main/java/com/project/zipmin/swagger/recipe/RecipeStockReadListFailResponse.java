package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeStockReadListFailResponse")
public class RecipeStockReadListFailResponse {
	
    @Schema(example = "RECIPE_STOCK_READ_LIST_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 재료 목록 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
