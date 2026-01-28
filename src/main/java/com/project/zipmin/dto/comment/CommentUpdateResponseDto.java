package com.project.zipmin.dto.comment;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentUpdateResponseDto {
	
	private int id;
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	private int commId;
	private int userId;
	
	private String nickname;
	private long likecount;
	private boolean likestatus;
	
}