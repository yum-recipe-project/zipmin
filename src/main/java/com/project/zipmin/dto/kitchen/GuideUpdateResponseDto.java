package com.project.zipmin.dto.kitchen;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideUpdateResponseDto {
	
	private int id;        
    private String title;
    private String subtitle;
    private String category;
    private String content;
    private Date posstdate;
    private int userId;
    
}


