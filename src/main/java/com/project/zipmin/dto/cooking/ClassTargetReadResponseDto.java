package com.project.zipmin.dto.cooking;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassTargetReadResponseDto {
	
	private int id;
	private String content;
	private int classId;
	
}
