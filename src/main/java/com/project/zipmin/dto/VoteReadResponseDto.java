package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteReadResponseDto {
	
	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	
	private String status;
	private List<VoteChoiceReadResponseDto> choiceList;
	private boolean voted;
	private int choiceId;
	private long total;
	private int commentcount;
}