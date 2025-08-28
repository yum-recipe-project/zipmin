package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeReadResponseDto {
	
	private Integer id;
	private String image;
	private String title;
	private String introduce;
	private Date postdate;
	private String cooklevel;
	private String cooktime;
	private String spicy;
	private String portion;
	private String tip;
	private String youtube;
	private Integer userId;
	
	private List<RecipeCategoryReadResponseDto> categoryList;
	private List<RecipeStockReadResponseDto> stockList;
	private List<RecipeStepReadResponseDto> stepList;
	
	private int commentcount;
	private int likecount;
	private int reportcount;
	private int reviewcount;
	private Double reviewscore;
	
	private String username;
	private String nickname;
	
	// 냉파용 (수정 필요)
	private Double rate;
}
