package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private int review_idx; // 기본키
	// private Date postdate;
	private String postdate;
	private String member_ref; // 작성자
	private int recipe_ref; // 어떤 레시피에
	private int score; // 별점
	private String content; // 내용
}
