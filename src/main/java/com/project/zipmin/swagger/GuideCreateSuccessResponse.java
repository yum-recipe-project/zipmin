package com.project.zipmin.swagger;

import com.project.zipmin.dto.GuideCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideCreateSuccessResponse")
public class GuideCreateSuccessResponse {
	
    @Schema(example = "KITCHEN_CREATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "키친가이드 작성 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = GuideCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
