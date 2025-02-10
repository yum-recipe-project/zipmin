<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/common-head.jsp" %>
		<link rel="stylesheet" href="/css/member/findIdResult.css">
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="result_wrap">
					<div>
						<h2>아이디 ・ 비밀번호 찾기</h2>
						<h5>인증된 휴대폰 번호로 가입한 아이디입니다. 아이디 확인 후 로그인을 진행해 주세요.</h5>
						<h3>jhrchicken</h3>
						<a class="move_btn" href="/member/login.do">로그인하러 가기</a>
						<span class="forgot_password">
							<a href="/member/findAccount.do?mode=password">비밀번호를 잊으셨나요?</a>
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