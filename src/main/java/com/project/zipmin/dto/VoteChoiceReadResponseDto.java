package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteChoiceReadResponseDto {
	
	private int id;
	private String choice;
	private int chompId;
	
	private int count;
	private double rate;
	
}
