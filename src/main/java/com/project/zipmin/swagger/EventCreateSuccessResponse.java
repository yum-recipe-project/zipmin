package com.project.zipmin.swagger;

import com.project.zipmin.dto.EventCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventCreateSuccessResponse")
public class EventCreateSuccessResponse {
	
    @Schema(example = "EVENT_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = EventCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}