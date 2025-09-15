package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserCorrectPassworResponse")
public class UserCorrectPassworResponse {
	
    @Schema(example = "USER_CORRECT_PASSWORD", description = "에러 코드")
    public String code;

    @Schema(example = "비밀번호 일치", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
