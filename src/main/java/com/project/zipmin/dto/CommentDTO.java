package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private int comm_idx;
	private int comm_ref; // 자기 참조
	private Date postdate;
	private String content;
	private String boardname;
	private Boolean rewrite;
	private int board_ref;
	private int member_ref;
}
