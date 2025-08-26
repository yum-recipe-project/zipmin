package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeReportSuccessResponse")
public class RecipeReportSuccessResponse {
	
    @Schema(example = "RECIPE_REPORT_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 신고 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}