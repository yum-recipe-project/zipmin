<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/verify-password.css">
		<script src="/js/member/verify-password.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="verify_wrap">
					<div>
						<h2>비밀번호 확인</h2>
						<h3>비밀번호를 입력해주세요</h3>
						<h5>회원님의 개인정보를 안전하게 보호하기 위해 비밀번호를 다시 한번 확인합니다.</h5>
					</div>
					
					<form>
						<div class="password_field">
							<label>비밀번호</label>
							<input type="text" id="passwordInput" name="" value="" placeholder="비밀번호 입력">
							<p id="passwordHint">비밀번호를 입력해주세요.</p>
						</div>
						<button type="submit" class="btn_primary_wide">확인</button>
						<span class="forgot_password">
							<a href="/member/findAccount.do?mode=password">비밀번호를 잊으셨나요?</a>
						</span>
					</form>
				</div>
				
				<!-- 하단 정보 -->
				<div class="buttom_info">
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
		
	</body>
</html>