package com.project.zipmin.swagger.chomp;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventUnauthorizedResponse")
public class EventUnauthorizedResponse {
	
    @Schema(example = "EVENT_UNAUTHORIZED", description = "에러 코드")
    public String code;

    @Schema(example = "로그인되지 않은 사용자", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
