package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ReportDTO {
	private Integer recipe_ref;
	private Integer comment_ref;
	private String review_ref;
	private String member_ref;
	private String reason;
}