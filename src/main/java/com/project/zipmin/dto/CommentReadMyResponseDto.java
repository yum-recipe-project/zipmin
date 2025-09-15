package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentReadMyResponseDto {
	
	private Integer id;
	private Date postdate;
	private String content;
	private String tablename;
	private Integer recodenum;
	private Integer commId;
	private Integer userId;
	
	private String nickname;
	private String title;
}
