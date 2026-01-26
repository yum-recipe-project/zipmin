package com.project.zipmin.swagger.like;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeDuplicatedResponse")
public class LikeDuplicatedResponse {
	
    @Schema(example = "LIKE_DUPLICATED", description = "에러 코드")
    public String code;

    @Schema(example = "좋아요 중복 시도", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
