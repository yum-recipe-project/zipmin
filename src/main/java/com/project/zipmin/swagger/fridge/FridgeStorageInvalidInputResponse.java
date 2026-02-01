package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeStorageInvalidInputResponse")
public class FridgeStorageInvalidInputResponse {
	
    @Schema(example = "FRIDGE_STORAGE_INVALID_INPUT", description = "에러 코드")
    public String code;

    @Schema(example = "입력값이 유효하지 않음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
