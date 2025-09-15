package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassScheduleReadResponseDto {
	
	private int id;
	private Date starttime;
	private Date endtime;
	private String title;
	private int classId;
	
}
