package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "USERS")
public class User {
	@Id
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String avatar;
	private int point;
	private int revenue;
	private String auth;
	private int enable;
}
