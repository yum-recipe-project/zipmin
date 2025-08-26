package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassReadResponseDto {
	
	private int id;
	private String title;
	private String category;
	private String place;
	private Date postdate;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private int headcount;
	private String need;
	private String image;
	private String introduce;
	private int approval;
	private int userId;
	
	// target 등등 (수정 필요)
	private List<ClassTargetReadResponseDto> targetList;
	private List<ClassScheduleReadResponseDto> scheduleList;
	private List<ClassTutorReadResponseDto> tutorList;
	private boolean applystatus;
	
	
	
	
	private boolean isOpened;
	private String username;
	private String nickname;
	
	private int applycount;
}
