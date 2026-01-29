package com.project.zipmin.swagger.chomp;

import com.project.zipmin.dto.chomp.MegazineUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MegazineUpdateSuccessResponse")
public class MegazineUpdateSuccessResponse {
	
    @Schema(example = "MEGAZINE_UPDATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "매거진 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = MegazineUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}
