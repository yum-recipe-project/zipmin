package com.project.zipmin.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.Role;
import com.project.zipmin.oauth2.CustomOAuth2User;
import com.project.zipmin.oauth2.GoogleResponse;
import com.project.zipmin.oauth2.NaverResponse;
import com.project.zipmin.oauth2.OAuth2Response;
import com.project.zipmin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// OAuth 2.0 인증을 통해 사용자 정보를 가져오는 역할 담당
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {		

		// 1. 유저 정보(attributes) 가져오기
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		// 2. resistrationId 가져오기
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		// 3. 유저 정보 dto 생성
		OAuth2Response oAuth2Response = switch (registrationId) {
		    case "naver" -> new NaverResponse(oAuth2User.getAttributes());
		    case "google" -> new GoogleResponse(oAuth2User.getAttributes());
		    default -> null;
		};
		
		// 리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 저장
		String username = oAuth2Response.getEmail();
		
		// 넘어온 회원정보가 이미 테이블에 존재하는지 확인
		User existData = userRepository.findByUsername(username);
		
		// 존재하지 않는다면 회원정보를 저장하고 CustomOAuth2User 반환
		if (existData == null) {
			User user = User.createUser(username, oAuth2Response.getName(), oAuth2Response.getName(), oAuth2Response.getEmail(), Role.ROLE_USER, oAuth2Response.getProvider(), oAuth2Response.getProviderId());

			user = userRepository.save(user);
			
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setUsername(username);
			userDto.setName(oAuth2Response.getName());
			userDto.setNickname(oAuth2Response.getName());
			userDto.setEmail(oAuth2Response.getEmail());
			userDto.setRole("ROLE_USER");
			
			return new CustomOAuth2User(userDto);
		}
		// 회원정보가 존재한다면 조회된 데이터로 반환
		else {
			existData.updateUser(oAuth2Response.getEmail(), oAuth2Response.getName());
			
			UserDto userDto = new UserDto();
			userDto.setId(existData.getId());
			userDto.setUsername(username);
			userDto.setName(existData.getName());
			userDto.setNickname(existData.getNickname());
			userDto.setEmail(existData.getEmail());
			userDto.setRole("ROLE_USER");
			
			return new CustomOAuth2User(userDto);
		}
	}
	
}
