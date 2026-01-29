package com.project.zipmin.dto.cooking;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyUpdateResponseDto {
	
	private int id;
	private Date applydate;
	private String reason;
	private String question;
	private int selected;
	private int attend;
	private int classId;
	private int userId;
	
}
