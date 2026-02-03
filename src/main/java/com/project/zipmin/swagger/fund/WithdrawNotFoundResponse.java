package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WithdrawNotFoundResponse")
public class WithdrawNotFoundResponse {

    @Schema(example = "WITHDRAW_NOT_FOUND", description = "성공 코드")
    public String code;

    @Schema(example = "해당 출금을 찾을 수 없음", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
