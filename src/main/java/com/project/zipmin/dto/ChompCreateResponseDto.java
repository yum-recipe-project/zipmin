package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChompCreateResponseDto {

	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	private String category;
	
	
	// 수정해야함
	private List<VoteChoiceReadResponseDto> choiceList;
}
