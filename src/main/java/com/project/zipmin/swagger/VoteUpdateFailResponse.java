package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteUpdateFailResponse")
public class VoteUpdateFailResponse {
	
    @Schema(example = "VOTE_UPDATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "투표 수정 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}