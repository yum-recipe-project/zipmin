package com.project.zipmin.swagger;

import com.project.zipmin.dto.VoteRecordCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteRecordCreateSuccessResponse")
public class VoteRecordCreateSuccessResponse {
	
    @Schema(example = "VOTE_RECORD_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "투표 기록 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = VoteRecordCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
