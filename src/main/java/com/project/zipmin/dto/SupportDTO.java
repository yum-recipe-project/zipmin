package com.project.zipmin.dto;

import lombok.Data;

@Data
public class SupportDTO {
	private int id;
	private String sponsorId; // 누가
	private String recipe_id; // 어떤 레시피에
	private int point; // 몇 포인트를
	private String receiverId; // 누구한테
}
