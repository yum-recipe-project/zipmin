package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteRecordDuplicateResponse")
public class VoteRecordDuplicateResponse {
	
    @Schema(example = "VOTE_RECORD_DUPLICATE", description = "에러 코드")
    public String code;

    @Schema(example = "투표 중복 참여 시도", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
