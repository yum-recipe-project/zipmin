package com.project.zipmin.swagger;

import com.project.zipmin.dto.GuideUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideUpdateSuccessResponse")
public class GuideUpdateSuccessResponse {
	
    @Schema(example = "KITCHEN_UPDATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "키친가이드 수정 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = GuideUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}