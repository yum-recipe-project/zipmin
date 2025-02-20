package com.project.zipmin.recipe;

import lombok.Data;

@Data
public class CategoryDTO {
	private String type;
	private String situation;
	private String ingredient;
	private String way;
	private int recipe_ref;
}
