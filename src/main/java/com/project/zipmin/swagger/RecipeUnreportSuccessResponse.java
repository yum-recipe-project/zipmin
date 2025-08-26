package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeUnreportSuccessResponse")
public class RecipeUnreportSuccessResponse {
	
    @Schema(example = "RECIPE_UNREPORT_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 신고 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}