package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideReadResponseDto {
	
	private int id;
	private String title;
	private String subtitle;
	private String category;
	private Date postdate;
	private String content;
	private int userId;
	
	private long likecount;
	private boolean likestatus;
	
}


