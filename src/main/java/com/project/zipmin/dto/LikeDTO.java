package com.project.zipmin.dto;

import lombok.Data;

@Data
public class LikeDTO {
	private int like_idx;
	private String member_ref; // 누가 (유저 id)
	private String tablename; // 테이블 이름 (member, comment, recipe, guide, )
	private int recodenum; // 어떤 게시물을
}