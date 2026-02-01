package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeReadListFailResponse")
public class FridgeReadListFailResponse {
	
    @Schema(example = "FRIDGE_READ_LIST_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "냉장고 목록 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
