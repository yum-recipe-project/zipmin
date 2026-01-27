package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeUnreportFailResponse")
public class RecipeUnreportFailResponse {
	
    @Schema(example = "RECIPE_UNREPORT_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 신고 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
