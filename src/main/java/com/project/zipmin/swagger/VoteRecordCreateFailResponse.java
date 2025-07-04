package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteRecordCreateFailResponse")
public class VoteRecordCreateFailResponse {
	
    @Schema(example = "VOTE_RECORD_CREATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "투표 기록 작성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
