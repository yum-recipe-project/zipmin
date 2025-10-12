package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewCreateRequestDto {
	
//	private Date postdate;
	private Integer score;   
    private String content;  
    private Integer recipeId;
    private Integer userId;
}
