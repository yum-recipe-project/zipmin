package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserIncorrectPassworResponse")
public class UserIncorrectPassworResponse {
	
    @Schema(example = "USER_INCORRECT_PASSWORD", description = "에러 코드")
    public String code;

    @Schema(example = "비밀번호 불일치", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
