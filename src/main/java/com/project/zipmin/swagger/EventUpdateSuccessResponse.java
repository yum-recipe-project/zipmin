package com.project.zipmin.swagger;

import com.project.zipmin.dto.EventUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventUpdateSuccessResponse")
public class EventUpdateSuccessResponse {
	
    @Schema(example = "EVENT_UPDATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = EventUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}