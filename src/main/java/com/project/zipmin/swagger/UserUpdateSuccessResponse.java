package com.project.zipmin.swagger;

import com.project.zipmin.dto.UserUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserUpdateSuccessResponse")
public class UserUpdateSuccessResponse {
	
    @Schema(example = "USER_UPDATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "사용자 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = UserUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}