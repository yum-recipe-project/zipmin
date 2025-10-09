package com.project.zipmin.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.zipmin.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	
	private final UserDto userDto;
	
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userDto.getRole();
			}
		});
		return collection;
	}
	
	public int getId() {
		return userDto.getId();
	}
	
	@Override
	public String getName() {
		return userDto.getName();
	}
	
	public String getUsername() {
		return userDto.getUsername();
	}
	
	public String getNickname() {
		return userDto.getNickname();
	}
	
	public String getEmail() {
		return userDto.getEmail();
	}
	
	public String getAvatar() {
		return userDto.getAvatar();
	}
}
