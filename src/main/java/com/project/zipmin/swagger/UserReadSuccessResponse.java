package com.project.zipmin.swagger;

import com.project.zipmin.dto.UserReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserReadSuccessResponse")
public class UserReadSuccessResponse {
	
    @Schema(example = "USER_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "사용자 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = UserReadResponseDto.class, description = "응답 데이터")
    public Object data;

}