package com.project.zipmin.dto;

import lombok.Data;

@Data
public class LikeDTO {
	private int id;
	private String user_id; // 누가 (유저 id)
	private String tablename; // 테이블 이름 (member, comment, recipe, guide, )
	private int recodenum; // 어떤 게시물을
}