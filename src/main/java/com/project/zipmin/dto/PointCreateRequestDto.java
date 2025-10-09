package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PointCreateRequestDto {
	
    private String impUid;
    // 가맹점에서 생성한 고유 주문번호
    private String merchantUid;
    private int amount;
    private String buyerName;
    private String buyerTel;
    private int userId;
	
}
