package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDTO {
	private int id;
	private int commId;
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	private int userId;
}