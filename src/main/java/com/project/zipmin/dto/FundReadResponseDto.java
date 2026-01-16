package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FundReadResponseDto {
	
	private int id;           
	private int funderId;  
	private int fundeeId;
	private int recipeId;       
	private int point;           
	private Date funddate;
	
	private String nickname;  
	private String title;
	private String image;
}
