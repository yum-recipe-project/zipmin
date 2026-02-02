package com.project.zipmin.dto.fund;

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
	private int point;
	private Date funddate;
	private int funderId;  
	private int fundeeId;
	private int recipeId;       

}
