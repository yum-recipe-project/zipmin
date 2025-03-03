package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassScheduleDTO {
	private int id;
	private int starttime;
	private int endtime;
	private String title;
	private int classId;
}
