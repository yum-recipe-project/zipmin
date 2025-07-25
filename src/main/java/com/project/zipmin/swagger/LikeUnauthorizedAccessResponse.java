package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeUnauthorizedAccessResponse")
public class LikeUnauthorizedAccessResponse {
	
    @Schema(example = "LIKE_UNAUTHORIZED_ACCESS", description = "에러 코드")
    public String code;

    @Schema(example = "로그인되지 않은 사용자", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
