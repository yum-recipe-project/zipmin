package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MemoCreateFailResponse")
public class MemoCreateFailResponse {
	
    @Schema(example = "MEMO_CREATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "장보기 메모 작성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
