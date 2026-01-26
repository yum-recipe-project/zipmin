package com.project.zipmin.swagger;

import com.project.zipmin.dto.kitchen.GuideReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideReadSuccessResponse")
public class GuideReadSuccessResponse {
	
    @Schema(example = "KITCHEN_READ_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "키친가이드 상세 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = GuideReadResponseDto.class, description = "응답 데이터")
    public Object data;

}