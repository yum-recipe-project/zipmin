package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FridgeReadResponseDto {
	
	private int id;
	private String image;
	private String name;
	private String category;
	private String zone;
	private int userId;
	
	private boolean isLiked;
}
