package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeForbiddenResponse")
public class FridgeForbiddenResponse {
	
    @Schema(example = "FRIDGE_FORBIDDEN", description = "에러 코드")
    public String code;

    @Schema(example = "권한 없는 사용자", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
