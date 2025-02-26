package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private int id;
	private int comm_id; // id를 참조
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	private int user_id;
}