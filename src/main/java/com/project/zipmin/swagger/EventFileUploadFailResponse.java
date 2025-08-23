package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventFileUploadFailResponse")
public class EventFileUploadFailResponse {
	
    @Schema(example = "EVENT_FILE_UPLOAD_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "이벤트 파일 업로드 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
