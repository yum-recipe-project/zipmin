package com.project.zipmin.dto;


import lombok.Data;

@Data
public class StepDTO {
	private int step_idx;
	// private Integer index;
	private String description;
	private String image;
	private int recipe_ref;
}
