package com.project.zipmin.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeDTO {
	// 기본 컬럼
	private int id;
	private int imageUrl;
	private String title;
	private String level;
	private String time;
	private String spicy;
	private String introduce;
	private int portion;
	private String tip;
	private String youtubeUrl;
	private String userId;
	
	// 추가 컬럼
	private List<RecipeCategoryDTO> categoryList;
	private UserDto member;
	private List<RecipeIngredientDTO> ingredientList;
	private List<RecipeStepDTO> stepList;
	private Integer followerCount;
	private Boolean isLike;
	private Boolean isReport;
	private Boolean isFollow;
	private Integer reviewCount;
	private Integer commentCount;
}
