package com.project.zipmin.dto.fund;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountUpdateRequestDto {

	private int id;
	private String bank;     
	private String accountnum;   
	private String name;         
	private int userId;      
	
}
