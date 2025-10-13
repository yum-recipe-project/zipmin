package com.project.zipmin.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.filter.CustomAdminLoginFilter;
import com.project.zipmin.filter.CustomLoginFilter;
import com.project.zipmin.filter.CustomLogoutFilter;
import com.project.zipmin.filter.JwtFilter;
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
				
				// 모든 출처에서 요청 허용 (http://localhost:3000와 같이 주소로 허용 가능)
				configuration.setAllowedOrigins(List.of(
					    "http://localhost:8586",
					    "http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586"
					)
				);
				// HTTP 메소드 (GET, POST 등 모든 요청)의 요청을 허용
				// configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				// 인증 정보 (쿠키, 인증 토큰 등)의 전송을 허용
				configuration.setAllowCredentials(true);
				// 모든 HTTP 헤더의 요청을 허용
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				// 최대 우효기간 설정
				configuration.setMaxAge(3600L);
				// 브라우저가 접근할 수 있도록 특정 응답 헤더를 노출
				// configuration.setExposedHeaders(Collections.singletonList("access"));
				configuration.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));
				
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
					response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(AuthErrorCode.AUTH_TOKEN_INVALID)));
				}
			)
		);
		
		// 기본 로그인용 필터
		CustomLoginFilter userLoginFilter = new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper, reissueService);
		userLoginFilter.setFilterProcessesUrl("/login");

		// 관리자 로그인용 필터
		CustomAdminLoginFilter adminLoginFilter = new CustomAdminLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper, reissueService);
		adminLoginFilter.setFilterProcessesUrl("/admin/login");

		http.addFilterAt(userLoginFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterAt(adminLoginFilter, UsernamePasswordAuthenticationFilter.class);

		// oauth2
		http.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(UserInfoEndpointConfig -> UserInfoEndpointConfig.userService(customOAuth2UserService))
				.successHandler(customOAuthSuccessHandler)
			);
		
		// 경로별 인가 작업
		http.authorizeHttpRequests((auth) -> auth
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/", "/login", "/admin/login", "/logout", "/oauth2-jwt-header", "/reissue").permitAll()
				.requestMatchers("/user/**").permitAll()
				.requestMatchers("/users/**").permitAll()
				
				.requestMatchers("/recipe/**").permitAll()
				.requestMatchers("/recipes", "/recipes/**").permitAll()
				
				.requestMatchers("/kitchen/**").permitAll()
				.requestMatchers("/guides/**").permitAll()
				
				.requestMatchers("/chompessor/**").permitAll()
				.requestMatchers("/chomp", "/chomp/**", "/votes", "/votes/**", "/megazines", "/megazines/**", "events", "/events/**").permitAll()
				
				.requestMatchers("/comments", "/comments/**").permitAll()
				
				.requestMatchers("/cooking/**").permitAll()
				.requestMatchers("/classes", "/classes/**").permitAll()
				
				.requestMatchers("/fridge/**").permitAll()
				
				.requestMatchers("/mypage.do").permitAll()
				.requestMatchers("/mypage/**").permitAll()
				
				.requestMatchers("/admin/login.do", "/admin/home.do", "/admin/listChomp.do", "/admin/listClass.do", "/admin/listComment.do").permitAll()
				.requestMatchers("/admin/listGuide.do", "/admin/listRecipe.do", "/admin/listReview.do", "/admin/listUser.do").permitAll()
				.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
				.requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/assets/**", "/files/**").permitAll()
				
				// Swagger 관련 경로 모두 허용
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**"
                ).permitAll()
				
				.anyRequest().authenticated()
			);
		
		// 세션 설정 (JWT 토큰 기반으로 움직이기 때문에 Session을 STATELESS 하도록 설정)
		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		
		return http.build();
	}
}
