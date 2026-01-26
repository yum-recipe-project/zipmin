package com.project.zipmin.swagger.like;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeCountFailResponse")
public class LikeCountFailResponse {
	
    @Schema(example = "LIKE_COUNT_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "좋아요 집계 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
