package com.project.zipmin.swagger.fund;

import com.project.zipmin.dto.fund.FundCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaymentLookupFailResponse")
public class PaymentLookupFailResponse {

    @Schema(example = "PAYMENT_LOOKUP_FAIL", description = "성공 코드")
    public String code;

    @Schema(example = "포트원 결제 내역 조회 실패", description = "성공 메시지")
    public String message;

    @Schema(implementation = FundCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
