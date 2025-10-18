package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideLikeFailResponse")
public class GuideLikeFailResponse {
	
    @Schema(example = "KITCHEN_LIKE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "키친가이드 좋아요 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}