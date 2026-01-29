package com.project.zipmin.dto.chomp;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MegazineCreateResponseDto {
	
	private int id;
	private String title;
	private Date closedate;
	private String content;
	private String image;
	private String userId;
	
	private String nickname;
	
}
