package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserAccountReadSuccessResponse")
public class UserAccountReadSuccessResponse {

    @Schema(example = "USER_ACCOUNT_READ_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "계좌 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
