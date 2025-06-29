package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteReadResponseDto {
	
	private int id;
	private Date opendate;
	private Date closedate;
	
	private String status;
	private ChompReadResponseDto chompDto;
	
}