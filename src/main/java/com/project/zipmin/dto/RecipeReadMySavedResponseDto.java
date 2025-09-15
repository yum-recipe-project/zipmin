package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeReadMySavedResponseDto {
	
	private Integer id;               
	private String image;             
	private String title;             
	private String cooklevel;         
	private String cooktime;          
	private String spicy;             
	private Double reviewscore;       
	private int reviewcount;          
	private int likecount;            
	private int commentcount;         
	private Date postdate;            
	private String username;          
	private String nickname;          
	
}
