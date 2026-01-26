package com.project.zipmin.dto.kitchen;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideCreateRequestDto {
	
	private String title;
	private String subtitle;
	private String category;
	private Date postdate;
	private String content;
	private int userId;
	
}