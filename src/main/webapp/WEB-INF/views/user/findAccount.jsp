<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/find-account.css">
		<script src="/js/user/find-account.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="find_wrap">
					<div>
						<h2>아이디 ・ 비밀번호 찾기</h2>
						<div class="tab_button">
							<button class="active">아이디 찾기</button>
							<button>비밀번호 찾기</button>
						</div>
						
						<!-- 아이디 찾기 -->
						<form id="findUsernameForm" class="tab_content">
							<div class="name_field">
								<label>이름</label>
								<input type="text" name="name" value="" placeholder="이름 입력">
								<p>이름을 입력해주세요.</p>
							</div>
							<div class="tel_field">
								<label>휴대폰 번호</label>
								<input type="text" name="tel" value="" placeholder="- 없이 휴대폰 번호 입력">
								<p>휴대폰 번호를 입력해주세요.</p>
							</div>
							<button type="submit" class="btn_primary_wide">아이디 찾기</button>
						</form>
						
						<!-- 비밀번호 찾기 -->
						<form id="findPasswordForm" class="tab_content">
							<div class="username_field">
								<label>아이디</label>
								<input type="text" name="username" value="" placeholder="아이디 입력">
								<p>아이디를 입력해주세요.</p>
							</div>
							<div class="email_field">
								<label>이메일</label>
								<input type="email" name="email" value="" placeholder="이메일 입력">
								<p>이메일을 입력해주세요.</p>
							</div>
							<button type="submit" class="btn_primary_wide">인증하기</button>
						</form>
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