package com.project.zipmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.project.zipmin.exception.AuthFailureHandler;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
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
					.requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
					.requestMatchers("/users/**").permitAll()
					.anyRequest().authenticated()
				);
		http.formLogin((formLogin) -> formLogin
				.loginPage("/user/login.do")
				// .loginProcessingUrl("")
				.successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
				// .defaultSuccessUrl("/", false)
				// .failureHandler(AuthFailureHandler)
				.usernameParameter("id")
				.passwordParameter("password")
				.permitAll());
		http.logout((logout) -> logout
				// .logoutUrl("")
				// .logoutSuccessUrl("/")
				.permitAll());
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("1234"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("1234"))
				.roles("USER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
