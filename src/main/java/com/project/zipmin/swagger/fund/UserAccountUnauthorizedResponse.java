package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserAccountUnauthorizedResponse")
public class UserAccountUnauthorizedResponse {

    @Schema(example = "USER_ACCOUNT_UNAUTHORIZED", description = "성공 코드")
    public String code;

    @Schema(example = "로그인되지 않은 사용자", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
