package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(generator = "seq_user_id")
	@SequenceGenerator(name = "seq_user_id", sequenceName = "SEQ_USER_ID", allocationSize = 1)
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