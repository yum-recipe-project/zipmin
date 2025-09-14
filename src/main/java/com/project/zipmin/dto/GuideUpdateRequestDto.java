package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideUpdateRequestDto {
	
	private int id;
    private String title;
    private String subtitle;
    private String category;
    private String content;
    private int userId;
    
}


