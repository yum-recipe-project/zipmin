package com.project.zipmin.swagger;

import com.project.zipmin.dto.MegazineReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MegazineReadSuccessResponse")
public class MegazineReadSuccessResponse {
	
    @Schema(example = "MEGAZINE_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "매거진 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = MegazineReadResponseDto.class, description = "응답 데이터")
    public Object data;

}