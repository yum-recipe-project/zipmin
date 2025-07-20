package com.project.zipmin.swagger;

import com.project.zipmin.dto.UserCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserCreateSuccessResponse")
public class UserCreateSuccessResponse {
	
    @Schema(example = "USER_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "사용자 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = UserCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}