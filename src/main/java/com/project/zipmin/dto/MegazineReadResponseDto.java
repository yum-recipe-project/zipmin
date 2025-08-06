package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.ToString;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MegazineReadResponseDto {
	
	private int id;
	private String title;
	private Date postdate;
	private String content;
	
	private int commentcount;
}
