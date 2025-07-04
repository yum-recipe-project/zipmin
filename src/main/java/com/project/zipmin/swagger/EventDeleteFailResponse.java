package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventDeleteFailResponse")
public class EventDeleteFailResponse {
	
    @Schema(example = "EVENT_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
