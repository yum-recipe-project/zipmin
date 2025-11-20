package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MemoDeleteSuccessResponse")
public class MemoDeleteSuccessResponse {
	
    @Schema(example = "MEMO_DELETE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "장보기 메모 삭제 성공", description = "성공 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
