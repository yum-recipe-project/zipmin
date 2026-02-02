package com.project.zipmin.dto.fund;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentCreateRequestDto {
	
	private String impUid;
	// 가맹점에서 생성한 고유 주문번호
	private String merchantUid;
	private int amount;
	private String name;
	private String tel;
	private int userId;
	
}
