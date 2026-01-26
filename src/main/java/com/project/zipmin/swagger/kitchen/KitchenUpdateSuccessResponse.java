package com.project.zipmin.swagger.kitchen;

import com.project.zipmin.dto.kitchen.GuideUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "KitchenUpdateSuccessResponse")
public class KitchenUpdateSuccessResponse {
	
    @Schema(example = "KITCHEN_UPDATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "키친가이드 수정 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = GuideUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}