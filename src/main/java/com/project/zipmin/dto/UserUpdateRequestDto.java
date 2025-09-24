package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserUpdateRequestDto {
	
	private Integer id;
	private String password;
	private String username;
	private String name;
	private String nickname;
	private String tel;
	private String email;
	
	private String avatar;
	private String introduce;
	
}
