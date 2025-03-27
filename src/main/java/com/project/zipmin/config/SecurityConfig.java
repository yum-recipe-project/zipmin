package com.project.zipmin.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.project.zipmin.exception.AuthFailureHandler;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Autowired
	public AuthFailureHandler authFailureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((crsf) -> crsf.disable())
			.cors((cors) -> cors.disable())
			.authorizeHttpRequests((request) -> request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/user/**").permitAll()
					.requestMatchers("/recipe/**").permitAll()
					.requestMatchers("/kitchen/**").permitAll()
					.requestMatchers("/chompessor/**").permitAll()
					.requestMatchers("/cooking/**").permitAll()
					.requestMatchers("/fridge/**").permitAll()
					.requestMatchers("/mypage/**").permitAll()
					.requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
					.anyRequest().authenticated()
				);
		http.formLogin((formLogin) -> formLogin
				.loginPage("/user/login.do")
				// .loginProcessingUrl("")
				.defaultSuccessUrl("/", false)
				.failureHandler(authFailureHandler) 
				.usernameParameter("id")
				.passwordParameter("password") 
				.permitAll());
		http.logout((logout) -> logout
				// .logoutUrl("")
				// .logoutSuccessUrl("/")
				.permitAll());
		
		return http.build();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT id, password, enable FROM users WHERE id = ?")
			.authoritiesByUsernameQuery("SELECT id, auth FROM users WHERE id = ?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
