package com.project.zipmin.swagger;

import com.project.zipmin.dto.VoteUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteUpdateFailResponse")
public class VoteUpdateSuccessResponse {
	
    @Schema(example = "VOTE_UPDATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "투표 수정 실패", description = "에러 메시지")
    public String message;

    @Schema(implementation = VoteUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}