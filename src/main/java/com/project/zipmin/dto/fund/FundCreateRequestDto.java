package com.project.zipmin.dto.fund;

import java.sql.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FundCreateRequestDto {

	private int point;
	private Date funddate;
	private int funderId;
	private int fundeeId;
	private int recipeId;
    
}
