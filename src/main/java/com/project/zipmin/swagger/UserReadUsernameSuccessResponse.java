package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserReadUsernameSuccessResponse")
public class UserReadUsernameSuccessResponse {
	
    @Schema(example = "USER_READ_USERNAME_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "사용자 아이디 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}