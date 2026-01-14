package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FundCreateRequestDto {

	private Integer funderId;
	private Integer fundeeId;
	private Integer recipeId;
    private Integer point;
	private Date funddate;
}
