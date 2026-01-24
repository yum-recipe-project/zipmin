package com.project.zipmin.dto.chomp;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteCreateResponseDto {
	
	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	private String category;
	private String image;
	private int userId;
	
}