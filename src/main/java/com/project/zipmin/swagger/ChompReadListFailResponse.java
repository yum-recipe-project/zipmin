package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChompReadListFailResponse")
public class ChompReadListFailResponse {
	
    @Schema(example = "CHOMP_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "쩝쩝박사 게시물 목록 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
