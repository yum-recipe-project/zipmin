package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserClassReadResponseDto {
	
	private int id;
	private String title;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private String image;
	private int approval;
	private int userId;

	private boolean isOpened;
	private boolean isEvented;
	
}
