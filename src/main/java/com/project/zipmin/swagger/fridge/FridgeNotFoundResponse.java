package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeNotFoundResponse")
public class FridgeNotFoundResponse {
	
    @Schema(example = "FRIDGE_NOT_FOUND", description = "에러 코드")
    public String code;

    @Schema(example = "해당 냉장고를 찾을 수 없음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
