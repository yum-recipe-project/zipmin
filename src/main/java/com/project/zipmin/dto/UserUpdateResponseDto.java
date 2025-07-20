package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserUpdateResponseDto {
	private int id;
	private String username;
	private String name;
	private String nickname;
	private String tel;
	private String email;
	private String role;
}
