package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideNotFoundResponse")
public class GuideNotFoundResponse {
	
    @Schema(example = "GUIDE_NOT_FOUND", description = "에러 코드")
    public String code;

    @Schema(example = "해당 가이드를 찾을 수 없음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
