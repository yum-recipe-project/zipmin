package com.project.zipmin.swagger.report;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReportUnauthorizedResponse")
public class ReportUnauthorizedResponse {
	
    @Schema(example = "REPORT_UNAUTHORIZED", description = "에러 코드")
    public String code;

    @Schema(example = "로그인되지 않은 사용자", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
