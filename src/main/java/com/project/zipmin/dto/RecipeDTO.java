package com.project.zipmin.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecipeDTO {
	// 기본 컬럼
	private int id;
	private int image;
	private String title;
	private String level;
	private String time;
	private String spicy;
	private String introduce;
	private int portion;
	private String tip;
	private String user_id;
	
	// 추가 컬럼
	private List<CategoryDTO> category_list;
	private UserDTO member;
	private List<IngredientDTO> ingredient_list;
	private List<RecipeStepDTO> step_list;
	private Integer follower_count;
	private Boolean isLike;
	private Boolean isReport;
	private Boolean isFollow;
	private Integer review_count;
	private Integer comment_count;
}
