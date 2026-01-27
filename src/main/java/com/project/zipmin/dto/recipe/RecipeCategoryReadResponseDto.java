package com.project.zipmin.dto.recipe;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeCategoryReadResponseDto {

	private int id;
	private String type;
	private String tag;
	private int recipeId;
	
}