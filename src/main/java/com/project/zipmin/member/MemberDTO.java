package com.project.zipmin.member;

import lombok.Data;

@Data
public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private Integer point;
	// 프로필 사진도 추가
}
