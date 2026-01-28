package com.project.zipmin.dto.review;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewReadResponseDto {
	
	private int id;
	private Date postdate;
	private int score;
	private String content;
	// private int recipeId;
	private String tablename;
	private int recodenum;
	private int userId;
	
	// Review Info
	private int likecount;
	private int reportcount;
	private boolean isLiked;
	
	// Recipe Info
	private String title;
	
	// User Info
	private String username;
	private String nickname;
	private String avatar;
	
}
