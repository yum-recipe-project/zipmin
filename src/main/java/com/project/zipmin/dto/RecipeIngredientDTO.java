package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeIngredientDTO {
	private int id;
	private String name;
	private int amount;
	private String unit;
	private String note;
	private int recipeId;
}
