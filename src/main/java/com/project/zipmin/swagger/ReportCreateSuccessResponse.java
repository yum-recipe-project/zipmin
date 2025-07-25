package com.project.zipmin.swagger;

import com.project.zipmin.dto.ReportCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReportCreateSuccessResponse")
public class ReportCreateSuccessResponse {
	
    @Schema(example = "REPORT_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "신고 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ReportCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
