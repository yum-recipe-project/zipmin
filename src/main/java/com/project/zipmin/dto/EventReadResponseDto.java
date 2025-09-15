package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventReadResponseDto {
	
	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	private String image;
	private int userId;
	
	private int commentcount;
	
}
