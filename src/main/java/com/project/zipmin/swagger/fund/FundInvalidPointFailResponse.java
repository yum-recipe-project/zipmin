package com.project.zipmin.swagger.fund;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundInvalidPointFailResponse")
public class FundInvalidPointFailResponse {
	
    @Schema(example = "FUND_INVALID_POINT", description = "에러 코드")
    public String code;

    @Schema(example = "포인트가 유효하지 않음", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
