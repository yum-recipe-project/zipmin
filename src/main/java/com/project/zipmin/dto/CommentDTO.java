package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //NOTE: 테스트용
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDTO {
	private int id;
	// private int commId;
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	// private int userId;
	
	private UserDTO userDTO;
	private CommentDTO commentDTO;
}