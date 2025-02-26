package com.project.zipmin.dto;


import lombok.Data;

@Data
public class RecipeStepDTO {
	private int id;
	private String image;
	private String content;
	private int recipe_id;
}
