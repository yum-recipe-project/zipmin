package com.project.zipmin.dto.fund;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentCreateResponseDto {
	
	private int id;
	private String username;
	private int point;
	
}
