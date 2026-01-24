package com.project.zipmin.dto.chomp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VoteChoiceCreateRequestDto {

	private String choice;
	private int chompId;
	
}
