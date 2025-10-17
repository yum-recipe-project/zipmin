package com.project.zipmin.swagger;

import com.project.zipmin.dto.FundSupportResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundCreateSuccessResponse")
public class FundSupportSuccessResponse {

    @Schema(example = "FUND_COMPLETE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "후원 성공.", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundSupportResponseDto.class, description = "응답 데이터")
    public Object data;

}
