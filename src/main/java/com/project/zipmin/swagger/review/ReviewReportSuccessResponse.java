package com.project.zipmin.swagger.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewReportSuccessResponse")
public class ReviewReportSuccessResponse {
	
    @Schema(example = "REVIEW_REPORT_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 신고 성공", description = "성공 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}