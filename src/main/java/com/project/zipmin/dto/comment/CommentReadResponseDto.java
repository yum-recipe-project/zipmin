package com.project.zipmin.dto.comment;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentReadResponseDto {
	
	private Integer id;
	private Date postdate;
	private String content;
	private String tablename;
	private Integer recodenum;
	private Integer commId;
	private Integer userId;
	
	// 작성자 정보
	private String username;
	private String nickname;
	private String avatar;
	private String role;
	
	// 게시글 정보
	private String title;
	
	private int likecount;
	private int reportcount;
	private boolean isLiked;
	private List<CommentReadResponseDto> subcommentList;
	
	
}
