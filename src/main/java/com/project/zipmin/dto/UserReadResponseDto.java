package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserReadResponseDto {
	
	private int id;
	private String username;
	private String name;
	private String nickname;
	private String avatar;
	private String tel;
	private String email;
	private String role;
	private String introduce;
	private String link;
	private int point;
	
	private int revenue;
	
}
