package com.project.zipmin.swagger;

import com.project.zipmin.dto.ClassReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CookingReadSuccessResponse")
public class CookingReadSuccessResponse {
	
    @Schema(example = "COOKING_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "쿠킹클래스 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ClassReadResponseDto.class, description = "응답 데이터")
    public Object data;

}