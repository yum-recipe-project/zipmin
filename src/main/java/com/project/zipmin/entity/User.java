package com.project.zipmin.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
	
	private String introduce;
	private String link;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	private String provider;
	@Column(name = "PROVIDER_ID")
	private String providerId;
	private String refresh;
	private String expiration;
	private int enable;
	
	private int point;
	private int revenue;
	
	// 정적 팩토리 메서드
	public static User createUser(String username, Role role) {
        User user = new User();
        user.username = username;
        user.role = role;
        return user;
    }
	public static User createUser(String username, String password, Role role) {
		User user = new User();
		user.username = username;
		user.password = password;
		user.role = role;
		return user;
	}
	public static User createUser(String username, String name, String nickname, String email, Role role, String provider, String providerId) {
		User user = new User();
		user.username = username;
		user.name = name;
		user.nickname = nickname;
		user.email = email;
		user.role = role;
		user.provider = provider;
		user.providerId = providerId;
		user.enable = 1;
		return user;
	}
	
	// dirty checking 사용
	public void updateUser(String username, String name) {
		this.username = username;
		this.name = name;
	}
	
	public void updateRefresh(String refresh, String expiration) {
		this.refresh = refresh;
		this.expiration = expiration;
	}
}