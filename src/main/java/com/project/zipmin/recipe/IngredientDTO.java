package com.project.zipmin.recipe;

import lombok.Data;

@Data
public class IngredientDTO {
	private Integer ingredient_idx;
	private String name;
	private Integer amount;
	private String unit;
	private String note;
	private Integer recipe_ref;
}
