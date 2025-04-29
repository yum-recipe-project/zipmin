package com.project.zipmin.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.entity.User;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.util.CustomOAuth2User;
import com.project.zipmin.util.GoogleResponse;
import com.project.zipmin.util.KakaoResponse;
import com.project.zipmin.util.NaverResponse;
import com.project.zipmin.util.OAuth2Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {		
		// 부모 클래스의 메서드를 사용하여 객체 생성
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		// 제공자
		String registration = userRequest.getClientRegistration().getRegistrationId();
		
		// 제공자별로 객체를 구현하여 OAuth2Response 타입으로 반환할 예정
		OAuth2Response oAuth2Response = null;
		
		// 제공자별 분기 처리
		if ("kakao".equals(registration)) {
			oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
		}
		else if ("naver".equals(registration)) {
			oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
		}
		else if ("google".equals(registration)) {
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
		}
		else {
			return null;
		}
		
		// 사용자명을 제공자_회원아이디 형식으로 만들어 저장 (이 값은 redis에서도 key로 쓰일 예정)
		// String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
		// 아이디를 제공자_회원아이디 형식으로 만들어 저장 (이 값은 redis에서도 key로 쓰일 예정)
		String id = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
		
		// 넘어온 회원정보가 이미 테이블에 존재하는지 확인
		User userData = userRepository.findById(id).orElse(null);
		
		// 존재하지 않는다면 회원정보를 저장하고 CustomOAuth2User 반환
		if (userData == null) {
			User user = new User();
			user.setId(id);
			user.setName(oAuth2Response.getName());
			user.setNickname(oAuth2Response.getName());
			user.setEmail(oAuth2Response.getEmail());
			user.setAvatar(oAuth2Response.getAvatar());
			user.setAuth("ROLE_USER");
			
			userRepository.save(user);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(id);
			userDTO.setName(oAuth2Response.getName());
			userDTO.setNickname(oAuth2Response.getName());
			userDTO.setEmail(oAuth2Response.getEmail());
			userDTO.setAvatar(oAuth2Response.getAvatar());
			userDTO.setAuth("ROLE_USER");
			
			return new CustomOAuth2User(userDTO);
		}
		// 회원정보가 존재한다면 조회된 데이터로 반환
		else {
			userData.setName(oAuth2Response.getName());
			userData.setEmail(oAuth2Response.getEmail());
			
			userRepository.save(userData);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(userData.getId());
			userDTO.setName(userData.getName());
			userDTO.setNickname(userData.getNickname());
			userDTO.setEmail(userData.getEmail());
			userDTO.setAvatar(userData.getAvatar());
			userDTO.setAuth("ROLE_USER");
			
			return new CustomOAuth2User(userDTO);
		}
	}
	
	
	
	
	
	
	
	
}
