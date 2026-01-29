package com.project.zipmin.dto.chomp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MegazineUpdateRequestDto {
	
	private int id;
	private String title;
	private String content;
	private String image;
	private int userId;
	
}
