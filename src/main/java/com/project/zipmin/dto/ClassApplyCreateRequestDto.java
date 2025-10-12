package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyCreateRequestDto {
	
	private String reason;
	private String question;
	private Integer selected;
	private Integer attend;
	private Integer userId;
	private Integer classId;
	
}

