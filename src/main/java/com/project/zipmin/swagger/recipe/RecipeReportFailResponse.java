package com.project.zipmin.swagger.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeReportFailResponse")
public class RecipeReportFailResponse {
	
    @Schema(example = "RECIPE_REPORT_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 신고 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
