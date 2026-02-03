package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundReadListSuccessResponse")
public class FundReadListSuccessResponse {

    @Schema(example = "FUND_READ_LIST_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "후원 목록 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
