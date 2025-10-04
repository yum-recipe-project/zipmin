<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/find-password-result.css">
		<script src="/js/user/find-password-result.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<form id="sendEmailForm" class="result_wrap">
					<div>
						<h2>아이디 ・ 비밀번호 찾기</h2>
						<h3>비밀번호 재설정을 위한 링크가 전송됩니다</h3>
						<h5>등록된 이메일을 통해 비밀번호 재설정 링크를 받아볼 수 있습니다.</h5>
						<button type="submit" class="btn_primary_wide">이메일 받기</button>
						<span class="go_login">
							<a href="/user/login.do">로그인하러 가기</a>
						</span>
					</div>
				</form>
				
				<!-- 하단 정보 -->
				<div class="buttom_info">
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
	</body>
</html>