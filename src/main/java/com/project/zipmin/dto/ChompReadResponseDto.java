package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.zipmin.entity.Chomp;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChompReadResponseDto {
	
	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	private String category;
	private String image;
	private Integer userId;
	
	// 수정해야함
	private boolean voted;
	private int choiceId;
	private long total;
	
	
	
	
	
	
	
	private String status;
	private int commentcount;
	
	private List<VoteChoiceReadResponseDto> choiceList;
	private int recordcount;
}