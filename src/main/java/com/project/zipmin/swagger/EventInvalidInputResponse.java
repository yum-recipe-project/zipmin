package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventInvalidInputResponse")
public class EventInvalidInputResponse {
	
    @Schema(example = "EVENT_INVALID_INPUT", description = "에러 코드")
    public String code;

    @Schema(example = "입력값이 유효하지 않음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
