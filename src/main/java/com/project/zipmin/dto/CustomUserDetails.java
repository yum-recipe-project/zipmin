package com.project.zipmin.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.zipmin.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole().name();
			}
		});
		return collection;
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	public String getNickname() {
		return user.getNickname();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}
	
	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
	
	
}
