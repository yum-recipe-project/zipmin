package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
public class FundCreateResponseDto {
	
	private int id;           
	private int funderId;  
	private int fundeeId;
	private int recipeId;       
	private int point;
	private Date funddate;

}
