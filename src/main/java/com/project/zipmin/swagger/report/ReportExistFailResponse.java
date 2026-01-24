package com.project.zipmin.swagger.report;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReportExistFailResponse")
public class ReportExistFailResponse {
	
    @Schema(example = "REPORT_EXIST_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "신고 여부 확인 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
