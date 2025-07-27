package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyCreateResponseDto {
	
	private int id;
	private Date applydate;
	private String reason;
	private String question;
	private int attend;
	private int userId;
	private int classId;
	
}

