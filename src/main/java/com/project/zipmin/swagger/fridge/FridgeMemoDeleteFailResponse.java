package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeMemoDeleteFailResponse")
public class FridgeMemoDeleteFailResponse {
	
    @Schema(example = "FRIDGE_MEMO_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "장보기 메모 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
