package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDTO {
	private int id;
	private String username;
	private String password;
	private String name;
	private String nickname;
	private String tel;
	private String email;
	private String avatar;
	private int point;
	private int revenue;
	private String role;
	private int enable;
}
