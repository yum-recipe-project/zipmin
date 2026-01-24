package com.project.zipmin.dto.chomp;

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
	private String content;
	private String category;
	private String image;
	private int userId;
	
	private int choiceId;
	private List<VoteChoiceReadResponseDto> choiceList;
	
	private int recordcount;
	
	private boolean isOpened;
	private boolean isVoted;
}