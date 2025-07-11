package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InternalServerErrorResponse")
public class InternalServerErrorResponse {
	
    @Schema(example = "INTERNAL_SERVER_ERROR", description = "에러 코드")
    public String code;

    @Schema(example = "서버 내부 오류", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
