package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassReadMyApplyResponseDto {
	
	private int id;
	private int userId;
	private String title;
	private String place;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private int headcount;
	private String need;
	private String image;
	private String introduce;
	private int approval;
	
	// target 등등
	private List<ClassTargetReadResponseDto> targetList;
	private List<ClassScheduleReadResponseDto> scheduleList;
	private List<ClassTutorReadResponseDto> tutorList;
	private boolean applystatus;
}
