package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeCreateFailResponse")
public class FridgeCreateFailResponse {
	
    @Schema(example = "FRIDGE_CREATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "냉장고 작성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
