package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAppliedClassResponseDto {
	
	private int id;
	private String title;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private String image;
	
	private int selected;
	
	private boolean isOpened;
	private boolean isPlanned;
}
