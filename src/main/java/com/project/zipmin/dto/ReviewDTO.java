package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private int id; // 기본키
	// private Date postdate;
	private String postdate;
	private String user_id; // 작성자
	private int recipe_id; // 어떤 레시피에
	private int score; // 별점
	private String content; // 내용
}
