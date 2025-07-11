package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MegazineCreateResponseDto {
	
	private int id;
	private String title;
	private Date postdate;
	private String content;
	
	private ChompReadResponseDto chompDto;
	private UserReadResponseDto userDto;
	
}
