package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.zipmin.entity.Chomp;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChompResponseDTO {
	private int id;
	private String category;
	private String title;
	
	private VoteResponseDTO chompVoteDTO;
	private MegazineResponseDTO chompMegazineDTO;
	private EventResponseDTO chompEventDTO;
}