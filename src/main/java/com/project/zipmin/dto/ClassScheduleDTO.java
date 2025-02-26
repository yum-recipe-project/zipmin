package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ClassScheduleDTO {
	private int id;
	private int starttime;
	private int endtime;
	private String title;
	private int class_id;
}
