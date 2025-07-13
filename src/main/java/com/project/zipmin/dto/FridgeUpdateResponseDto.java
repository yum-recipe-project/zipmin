package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FridgeUpdateResponseDto {
	
	private Integer id;
	private String imageUrl;
	private String name;
	private Integer amount;
	private String unit;
	private Date expdate;
	private String category;
	private Integer userId;
	
}
