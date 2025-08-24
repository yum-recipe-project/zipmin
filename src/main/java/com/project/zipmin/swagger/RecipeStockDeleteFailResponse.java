package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeStockDeleteFailResponse")
public class RecipeStockDeleteFailResponse {
	
    @Schema(example = "RECIPE_STOCK_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 재료 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
