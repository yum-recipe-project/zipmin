package com.project.zipmin.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.zipmin.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	
	private final UserDTO userDTO;
	
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
				return userDTO.getAuth();
			}
		});
		return collection;
	}
	
	@Override
	public String getName() {
		return userDTO.getName();
	}
	
	public String getId() {
		return userDTO.getId();
	}
}
