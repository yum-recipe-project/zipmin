package com.project.zipmin.swagger;

import com.project.zipmin.dto.UserReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserReadListSuccessResponse")
public class UserReadListSuccessResponse {
	
    @Schema(example = "USER_READ_LIST_SUCCESS", description = "응답 코드")
    public String code;

    @Schema(example = "사용자 목록 조회 성공", description = "응답 메시지")
    public String message;

    @Schema(implementation = UserReadResponseDto.class, description = "응답 데이터")
    public Object data;

}