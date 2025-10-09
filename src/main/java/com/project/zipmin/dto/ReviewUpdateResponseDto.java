package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewUpdateResponseDto {
	
	private Integer id;        // 리뷰 ID
	private Date postdate;     // 작성/수정 일자
	private Integer score;     // 수정된 별점
	private String content;    // 수정된 리뷰 내용
	private Integer recipeId;  // 레시피 ID
	private Integer userId;    // 작성자 ID
	private String nickname;   // 작성자 닉네임
}
