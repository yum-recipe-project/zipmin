package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAccountUpdateRequestDto {

	private int userId;      
	private String bank;     
	private String accountnum;   
	private String name;         
	
}
