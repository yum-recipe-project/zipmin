package com.project.zipmin.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeStepDTO {
	private int id;
	private String imageUrl;
	private String content;
	private int recipeId;
}
