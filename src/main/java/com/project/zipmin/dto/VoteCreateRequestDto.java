package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteCreateRequestDto {

	private String title;
	private Date opendate;
	private Date closedate;
	private int chompId;
	private int userId;
	
	private List<VoteChoiceCreateRequestDto> choices;
	
}
