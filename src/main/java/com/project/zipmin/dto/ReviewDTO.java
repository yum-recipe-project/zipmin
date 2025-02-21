package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private int review_idx; // 기본키
	private String member_ref; // 작성자
	private int recipe_ref; // 어떤 레시피에
	private int score; // 별점
	private String content; // 내용
	private boolean rewrite; // 수정 여부
}
