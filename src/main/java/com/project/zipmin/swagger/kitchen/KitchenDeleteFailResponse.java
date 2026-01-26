package com.project.zipmin.swagger.kitchen;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "KitchenDeleteFailResponse")
public class KitchenDeleteFailResponse {
	
    @Schema(example = "KITCHEN_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "키친가이드 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
