package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeReadMySavedResponseDto {
	
	private Integer id;                 // 레시피 ID
	private String image;               // 대표 이미지
	private String title;               // 레시피 제목
	private String cooklevel;           // 난이도
	private String cooktime;            // 조리 시간
	private String spicy;               // 매운 정도
	private Double reviewscore;         // 평균 리뷰 점수
	private int reviewcount;            // 리뷰 개수
	private int likecount;              // 좋아요 수
	private int commentcount;           // 댓글 수
	private Date postdate;              // 작성일
	private String username;            // 작성자 아이디
	private String nickname;            // 작성자 닉네임
	
}
