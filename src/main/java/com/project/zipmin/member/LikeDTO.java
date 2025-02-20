package com.project.zipmin.member;

import lombok.Data;

@Data
public class LikeDTO {
	private Integer recipe_ref;
	private Integer comment_ref;
	private String chef_ref;
	private String member_ref; // 누가
}
