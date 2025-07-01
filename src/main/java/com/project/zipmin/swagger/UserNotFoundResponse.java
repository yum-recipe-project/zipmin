package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserNotFoundResponse")
public class UserNotFoundResponse {

    @Schema(example = "USER_NOT_FOUND", description = "에러 코드")
    public String code;

    @Schema(example = "해당 사용자를 찾을 수 없음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
