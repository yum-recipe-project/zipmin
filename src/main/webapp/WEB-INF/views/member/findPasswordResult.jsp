<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/find-password-result.css">
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="result_wrap">
					<div>
						<h2>아이디 ・ 비밀번호 찾기</h2>
						<h3>비밀번호 재설정을 위한 링크가 전송됩니다</h3>
						<h5>등록된 이메일을 통해 비밀번호 재설정 링크를 받아볼 수 있습니다.</h5>
						<a class="send_btn" href="">이메일로 받기</a>
						<span class="go_login">
							<a href="/member/login.do">로그인하러 가기</a>
						</span>
					</div>
				</div>
				
				<!-- 하단 정보 -->
				<div class="buttom_info">
					<p class="copy">© Yum Recipe Project</p>
				</div>
			
			
			</div>
		
		</main>
	</body>
</html>