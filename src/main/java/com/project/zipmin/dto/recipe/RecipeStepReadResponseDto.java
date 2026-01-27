package com.project.zipmin.dto.recipe;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeStepReadResponseDto {

	private int id;
	private String image;
	private String content;
	private int recipeId;
	
}
