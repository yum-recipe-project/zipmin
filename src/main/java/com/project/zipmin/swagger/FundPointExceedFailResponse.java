package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundPointExceedFailResponse")
public class FundPointExceedFailResponse {
	
    @Schema(example = "FUND_POINT_EXCEED", description = "에러 코드")
    public String code;

    @Schema(example = "보유 포인트를 초과한 후원", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
