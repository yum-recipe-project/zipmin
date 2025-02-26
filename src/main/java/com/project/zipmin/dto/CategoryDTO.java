package com.project.zipmin.dto;

import lombok.Data;

@Data
public class CategoryDTO {
	private int id;
	private String type; // 종류별, 재료별, 상황별, 방법별
	private String tag; // 내용
	private int recipe_id;
}