package com.project.zipmin.exception;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		// 비밀번호가 일치하지 않을 때 던지는 예외
		if (exception instanceof BadCredentialsException) {
			countLoginFailure(request.getParameter("id"));
		}
		// 시스템 문제로 내부 인증 관련 처리 요청을 할 수 없는 예외
		else if (exception instanceof InternalAuthenticationServiceException) {
			
		}
		// 인증 요구가 거부됐을 때의 예외
		else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			System.err.println("에러남");
		}
		// 인증 거부 - 잠긴 계정
		else if (exception instanceof LockedException) {
			System.err.println("에러남");
		}
		// 인증 거부 - 계정 비활성화
		else if (exception instanceof DisabledException) {
			System.err.println("에러남");
		}
		// 인증 거부 - 계정 유효기간 만료
		else if (exception instanceof AccountExpiredException) {
			System.err.println("에러남");
		}
		// 인증 거부 - 비밀번호 유효기간 만료
		else if (exception instanceof CredentialsExpiredException) {
			System.err.println("에러남");
		}
		
		request.getRequestDispatcher("/user/login.do").forward(request, response);
	}
	
	// 로그인 실패 횟수 카운트
	public void countLoginFailure(String id) {
		// 틀린 횟수 업데이트하고 일정 횟수 이상이면 계정 잠금처리
	}
}
