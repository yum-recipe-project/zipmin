package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyStatusUpdateRequestDto {
	
	private Integer id;
	private String selected;
	private String attend;
	private Integer classId;
	
}

