package com.project.zipmin.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeCreateRequestDto {
	
	private String title;
	private String image;
	private String introduce;
	private String cooklevel;
	private String cooktime;
	private String spicy;
	private String portion;
	private String tip;
	// private String youtube;
	private Integer userId;
	
	private List<RecipeCategoryCreateRequestDto> categoryDtoList;
	private List<RecipeStockCreateRequestDto> stockDtoList;
	private List<RecipeStepCreateRequestDto> stepDtoList;
	
}
