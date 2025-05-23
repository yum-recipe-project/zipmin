package com.project.zipmin.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.project.zipmin.handler.AuthFailureHandler;
import com.project.zipmin.handler.AuthSuccessHandler;
import com.project.zipmin.jwt.CustomOAuth2UserService;
import com.project.zipmin.jwt.JWTFilter;
import com.project.zipmin.jwt.JWTUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JWTUtil jwtUtil;
	private final AuthFailureHandler authFailureHandler;
	private final AuthSuccessHandler authSuccessHandler;
	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// cors 설정
		http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration configuration = new CorsConfiguration();
				
				// 모든 출처에서 요청 허용 (http://localhost:3000와 깉이 주소로 허용 가능)
				configuration.setAllowedOrigins(Collections.singletonList("*"));
				// HTTP 메소드 (GET, POST 등 모든 요청)의 요청을 허용
				configuration.setAllowedMethods(Collections.singletonList("*"));
				// 인증 정보 (쿠키, 인증 토큰 등)의 전송을 허용
				configuration.setAllowCredentials(true);
				// 모든 HTTP 헤더의 요청을 허용
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				// 최대 우효기간 설정
				configuration.setMaxAge(3600L);
				
				// 브라우저가 접근할 수 있도록 특정 응답 헤더를 노출 (여기서는 "Set-Cookie"와 "Authorization")
				configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
				configuration.setExposedHeaders(Collections.singletonList("Authorization"));
				
				return configuration;
			}
		}));
		
		// csrf 비활성화
		http.csrf((crsf) -> crsf.disable());
		
		// Form 로그인 방식 비활성화 (일단 비활성화 하고)
		http.formLogin((auth) -> auth.disable());
		/* http.formLogin((formLogin) -> formLogin
				.loginPage("/user/login.do")
				.defaultSuccessUrl("/", false)
				.failureHandler(authFailureHandler) 
				.usernameParameter("id")
				.passwordParameter("password") 
				.permitAll()); */
		
		// HTTP Basic 인증 방식 비활성화 (매 요청마다 id와 pwd를 보내는 방식으로 인증하는 httpBasic을 사용하지 않겠다는 의미)
		http.httpBasic((auth) -> auth.disable());
		
		// JWT Filter (JWT인증을 사용할 수 있도록 addfilterBefore를 통해 JWTFilter를 UsernamePasswordAuthenticationFilter 전에 실행하도록 위치 지정)
		http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		// oauth2
		http.oauth2Login(oauth2 -> oauth2
				.successHandler(authSuccessHandler)
				.userInfoEndpoint(UserInfoEndpointConfig -> UserInfoEndpointConfig.userService(customOAuth2UserService))
			);
		
		// 경로별 인가 작업
		http.authorizeHttpRequests((auth) -> auth
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/user/**").permitAll()
				.requestMatchers("/recipe/**").permitAll()
				.requestMatchers("/kitchen/**").permitAll()
				.requestMatchers("/chomp/**").permitAll()
				.requestMatchers("/chompessor/**").permitAll()
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
	
//	@Autowired
//	private DataSource dataSource;
//	
//	@Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.usersByUsernameQuery("SELECT id, password, enable FROM users WHERE id = ?")
//			.authoritiesByUsernameQuery("SELECT id, auth FROM users WHERE id = ?")
//			.passwordEncoder(new BCryptPasswordEncoder());
//	}
//	
//	public PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
}
