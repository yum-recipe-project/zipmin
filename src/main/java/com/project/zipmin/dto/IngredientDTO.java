package com.project.zipmin.dto;

import lombok.Data;

@Data
public class IngredientDTO {
	private int ingr_idx;
	private String name;
	private int amount;
	private String unit;
	private String note;
	private int recipe_ref;
	private String member_ref;
}
