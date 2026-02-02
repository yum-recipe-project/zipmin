package com.project.zipmin.dto.cooking;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyStatusUpdateRequestDto {
	
	private int id;
	private int selected;
	private int attend;
	private int classId;
	
}

