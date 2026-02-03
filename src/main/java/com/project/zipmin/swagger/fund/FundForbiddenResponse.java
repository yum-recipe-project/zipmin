package com.project.zipmin.swagger.fund;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundForbiddenResponse")
public class FundForbiddenResponse {
	
    @Schema(example = "FUND_FORBIDDEN", description = "에러 코드")
    public String code;

    @Schema(example = "권한 없는 사용자", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
