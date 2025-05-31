package com.project.zipmin.oauth2;

public interface OAuth2Response {
	
	// 제공자
	String getProvider();

	// 제공자에서 발급해준 ID
	String getProviderId();
	
	// 이메일
	String getEmail();
	
	// 사용자 이름
	String getName();
}
