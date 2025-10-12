<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/find-password-result.css">
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div id="emailWrap" class="result_wrap">
					<div>
						<h2>유효기간이<br/>만료되었습니다</h2>
						<h5>비밀번호 변경 유효 시간이 만료되었습니다. 비밀번호 찾기를 다시 진행해주세요.</h5>
						<a href="/user/findAccount.do?mode=password" class="btn_primary_wide">비밀번호 찾기</a>
						<span class="go_login">
							<a href="/user/login.do">로그인하러 가기</a>
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