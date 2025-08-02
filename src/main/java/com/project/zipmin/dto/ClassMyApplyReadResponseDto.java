package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassMyApplyReadResponseDto {
	
	private int id;
	private String title;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private String image;
	
	private boolean applystatus;
	private int applyId;
}
