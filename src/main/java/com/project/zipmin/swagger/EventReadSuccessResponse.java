package com.project.zipmin.swagger;

import com.project.zipmin.dto.VoteReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventReadSuccessResponse")
public class EventReadSuccessResponse {
	
    @Schema(example = "EVENT_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = VoteReadResponseDto.class, description = "응답 데이터")
    public Object data;

}