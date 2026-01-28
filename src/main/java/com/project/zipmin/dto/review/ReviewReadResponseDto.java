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
	private String tablename;
	private int recodenum;
	private int userId;
	
	// 리뷰 정보
	private int likecount;
	private int reportcount;
	private boolean isLiked;
	
	// 레시피 정보
	private String title;
	
	// 사용자 정보
	private String username;
	private String nickname;
	private String avatar;
	
}
