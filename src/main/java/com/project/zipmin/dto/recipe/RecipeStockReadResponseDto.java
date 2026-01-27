package com.project.zipmin.dto.recipe;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeStockReadResponseDto {

	private Integer id;
	private String name;
	private Integer amount;
	private String unit;
	private String note;
	private Integer recipeId;
	
}
