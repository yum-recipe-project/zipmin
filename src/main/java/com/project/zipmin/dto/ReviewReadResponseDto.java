package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewReadResponseDto {
	private int id;
	// private Date postdate;
	private String postdate;
	private int score;
	private String content;
	private int recipeId;
	private String userId;
}
