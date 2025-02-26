package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VoteDTO {
	private int id;
	private String title;
	private Date opendate;
	private Date closedate;
	// 카테고리?
}