package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private int comm_idx;
	private int comm_ref;
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	private Boolean rewrite;
	private int member_ref;
}
