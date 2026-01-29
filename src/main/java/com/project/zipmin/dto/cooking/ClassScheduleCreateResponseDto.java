package com.project.zipmin.dto.cooking;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassScheduleCreateResponseDto {
	
	private int id;		 
	private Date starttime; 
	private Date endtime; 
	private String title;   
	private int classId;	
	
}