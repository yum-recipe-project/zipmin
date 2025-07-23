package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeForbiddenResponse")
public class LikeForbiddenResponse {
	
    @Schema(example = "LIKE_FORBIDDEN_ACCESS", description = "에러 코드")
    public String code;

    @Schema(example = "권한 없는 사용자의 접근", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
