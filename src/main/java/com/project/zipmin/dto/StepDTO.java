package com.project.zipmin.dto;


import lombok.Data;

@Data
public class StepDTO {
	private Integer step_idx;
	// private Integer index;
	private String description;
	private String image;
	private Integer recipe_ref;
}
