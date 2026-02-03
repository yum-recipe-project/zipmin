package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WithdrawInvalidPointResponse")
public class WithdrawInvalidPointResponse {

    @Schema(example = "WITHDRAW_INVALID_INPUT", description = "성공 코드")
    public String code;

    @Schema(example = "포인트가 유효하지 않음", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
