package com.project.zipmin.swagger;

import com.project.zipmin.dto.chomp.VoteReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteReadSuccessResponse")
public class VoteReadSuccessResponse {
	
    @Schema(example = "VOTE_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "투표 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = VoteReadResponseDto.class, description = "응답 데이터")
    public Object data;

}