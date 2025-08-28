package com.project.zipmin.swagger;

import com.project.zipmin.dto.EventReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventReadFailResponse")
public class EventReadFailResponse {
	
    @Schema(example = "EVENT_READ_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(implementation = EventReadResponseDto.class, description = "응답 데이터")
    public Object data;

}