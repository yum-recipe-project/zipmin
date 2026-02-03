package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WithdrawCreateSuccessResponse")
public class WithdrawCreateSuccessResponse {

    @Schema(example = "WITHDRAW_CREATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "출금 작성 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
