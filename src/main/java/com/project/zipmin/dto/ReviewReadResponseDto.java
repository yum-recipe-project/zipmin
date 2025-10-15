package com.project.zipmin.dto;

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
    private int userId;
    
    private String title;
    private String username;
    private String nickname;
    private String avatar;
    
    private int likecount;
	private int reportcount;
	private boolean isLiked;
}
