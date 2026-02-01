package com.project.zipmin.swagger.fridge;

import com.project.zipmin.dto.fridge.MemoUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeMemoUpdateSuccessResponse")
public class FridgeMemoUpdateSuccessResponse {
	
    @Schema(example = "FRIDGE_MEMO_UPDATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "장보기 메모 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = MemoUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}