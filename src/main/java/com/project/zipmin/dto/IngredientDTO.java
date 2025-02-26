package com.project.zipmin.dto;

import lombok.Data;

@Data
public class IngredientDTO {
	private int id;
	private String name;
	private int amount;
	private String unit;
	private String note;
	private int recipe_id;
	private String user_id;
}
