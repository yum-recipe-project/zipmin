package com.project.zipmin.swagger;

import com.project.zipmin.dto.VoteRecordCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteRecordDeleteSuccessResponse")
public class VoteRecordDeleteSuccessResponse {
	
    @Schema(example = "VOTE_RECORD_DELETE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "투표 기록 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = VoteRecordCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
