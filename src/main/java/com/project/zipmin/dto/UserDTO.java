package com.project.zipmin.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String avatar; // 프사
	private int point;
	// 받은 포인트도 있어야 함
}
