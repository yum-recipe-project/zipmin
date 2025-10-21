package com.project.zipmin.swagger;

import com.project.zipmin.dto.MemoCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MemoCreateSuccessResponse")
public class MemoCreateSuccessResponse {
	
    @Schema(example = "MEMO_CREATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "장보기 메모 작성 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = MemoCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}