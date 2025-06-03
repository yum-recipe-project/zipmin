package com.project.zipmin.config;

import java.util.Arrays;
import java.util.Collections;

import javax.sql.DataSource;

import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.filter.CustomLoginFilter;
import com.project.zipmin.filter.CustomLogoutFilter;
import com.project.zipmin.filter.JwtFilter;
import com.project.zipmin.handler.AuthFailureHandler;
import com.project.zipmin.handler.CustomOAuthSuccessHandler;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.service.CustomOAuth2UserService;
import com.project.zipmin.service.ReissueService;
import com.project.zipmin.util.JwtUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;
	private final CustomOAuthSuccessHandler customOAuthSuccessHandler;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final ObjectMapper objectMapper;
	private final ReissueService reissueService;
	private final UserRepository userRepository;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// cors 설정
		http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration configuration = new CorsConfiguration();
				
				// 모든 출처에서 요청 허용 (http://localhost:3000와 깉이 주소로 허용 가능)
				configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8586"));
				// HTTP 메소드 (GET, POST 등 모든 요청)의 요청을 허용
				configuration.setAllowedMethods(Collections.singletonList("*"));
				// 인증 정보 (쿠키, 인증 토큰 등)의 전송을 허용
				configuration.setAllowCredentials(true);
				// 모든 HTTP 헤더의 요청을 허용
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				// 최대 우효기간 설정
				configuration.setMaxAge(3600L);
				// 브라우저가 접근할 수 있도록 특정 응답 헤더를 노출
				configuration.setExposedHeaders(Collections.singletonList("access"));
				
				return configuration;
			}
		}));
		
		// csrf 비활성화
		http.csrf((crsf) -> crsf.disable());
		
		// Form 로그인 방식 비활성화
		http.formLogin((auth) -> auth.disable());
		
		// HTTP Basic 인증 방식 비활성화
		http.httpBasic((auth) -> auth.disable());
		
		// JWT Filter (JWT인증을 사용할 수 있도록 addfilterBefore를 통해 JWTFilter를 UsernamePasswordAuthenticationFilter 전에 실행하도록 위치 지정)
		http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		http.addFilterBefore(new CustomLogoutFilter(jwtUtil, reissueService, userRepository, objectMapper), LogoutFilter.class);
		
		http.exceptionHandling((exception) -> 
		exception
				.authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error("TOKEN_EXCEPTION", "유효하지 않은 인증입니다.")));
				}
			)
		);
		
		// 기본 로그인 필터인 UsernamePasswordAuthenticationFilter 자리에 커스텀 로그인 필터를 대체하거나 삽입
		http.addFilterAt(new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper, reissueService), UsernamePasswordAuthenticationFilter.class);
		
		// oauth2
		http.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(UserInfoEndpointConfig -> UserInfoEndpointConfig.userService(customOAuth2UserService))
				.successHandler(customOAuthSuccessHandler)
			);
		
		// 경로별 인가 작업
		http.authorizeHttpRequests((auth) -> auth
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/", "/login", "/logout", "/oauth2-jwt-header", "/reissue").permitAll()
				.requestMatchers("/user/**").permitAll()
				.requestMatchers("/users/**").permitAll()
				.requestMatchers("/recipe/**").permitAll()
				.requestMatchers("/kitchen/**").permitAll()
				
				.requestMatchers("/chompessor/**").permitAll()
				.requestMatchers("/chomp/**").hasRole("USER")
				
				.requestMatchers("/cooking/**").permitAll()
				.requestMatchers("/fridge/**").permitAll()
				.requestMatchers("/mypage/**").permitAll()
				.requestMatchers("/admin/**").permitAll()
				.requestMatchers("/megazines/**").permitAll()
				.requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/assets/**").permitAll()
				.anyRequest().authenticated()
			);
		
		// 세션 설정 (JWT 토큰 기반으로 움직이기 때문에 Session을 STATELESS 하도록 설정)
		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		
		return http.build();
	}
}
