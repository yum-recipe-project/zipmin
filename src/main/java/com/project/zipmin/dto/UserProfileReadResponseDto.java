package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileReadResponseDto {
	
	private int id;
	private String nickname;
	private String avatar;
	private String introduce;
	private String link;
	private String point;
	
	private int likecount;
	private boolean isLiked;
}
