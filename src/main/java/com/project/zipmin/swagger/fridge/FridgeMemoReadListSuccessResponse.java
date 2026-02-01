package com.project.zipmin.swagger.fridge;

import com.project.zipmin.dto.fridge.MemoReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeMemoReadListSuccessResponse")
public class FridgeMemoReadListSuccessResponse {
	
    @Schema(example = "FRIDGE_MEMO_READ_LIST_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "장보기 메모 목록 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = MemoReadResponseDto.class, description = "응답 데이터")
    public Object data;

}