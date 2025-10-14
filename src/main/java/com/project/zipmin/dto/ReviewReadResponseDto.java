package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewReadResponseDto {
	private int id;             // 리뷰 고유 ID
    private Date postdate;      // 작성일
    private int score;          // 평점
    private String content;     // 리뷰 내용
    private String username;    // 작성자 계정명
    private String nickname;    // 작성자 닉네임
    private int recipeId;
    private int userId;  
    
    private int likecount; // 좋아요 개수
	private int reportcount; // 신고 개수
	private boolean isLiked; // 좋아요 여부
	private String title; // 좋아요 여부
}
