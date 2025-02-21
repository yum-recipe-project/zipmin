package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private Integer comm_idx;
	private Integer comm_ref;
	private Date postdate;
	private String content;
	private String boardname;
	private Boolean rewrite;
	private Integer board_ref;
	private Integer member_ref;
}
