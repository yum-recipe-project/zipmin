package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.zipmin.entity.Chomp;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChompDTO {
	private int id;
	private String category;
	private String title;
	
	private ChompVoteDTO chompVoteDTO;
	private ChompMegazineDTO chompMegazineDTO;
	private ChompEventDTO chompEventDTO;
}