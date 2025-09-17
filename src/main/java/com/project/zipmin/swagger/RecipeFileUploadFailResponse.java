package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecipeFileUploadFailResponse")
public class RecipeFileUploadFailResponse {
	
    @Schema(example = "RECIPE_FILE_UPLOAD_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "레시피 파일 업로드 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}