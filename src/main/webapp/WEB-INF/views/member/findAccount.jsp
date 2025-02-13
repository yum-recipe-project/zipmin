<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/find-account.css">
		<script src="/js/member/find-account.js"></script>
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
						<form class="tab_content">
							<div class="name_field">
								<label>이름</label>
								<input type="text" id="nameInput" name="" value="" placeholder="이름 입력">
								<p id="nameHint">이름을 입력해주세요.</p>
							</div>
							<div class="phone_field">
								<label>휴대폰 번호</label>
								<input type="text" id="phoneInput" name="" value="" placeholder="- 없이 휴대폰 번호 입력">
								<p id="phoneHint">휴대폰 번호를 입력해주세요.</p>
							</div>
							<button type="submit" class="submit_btn">아이디 찾기</button>
							<a href="/member/findAccount/idResult.do">아이디 결과 페이지</a>
						</form>
						
						<!-- 비밀번호 찾기 -->
						<form class="tab_content">
							<div class="id_field">
								<label>아이디</label>
								<input type="text" id="idInput" name="" value="" placeholder="아이디 입력">
								<p id="idHint">아이디를 입력해주세요.</p>
							</div>
							<div class="email_field">
								<label>이메일</label>
								<input type="email" id="emailInput" name="" value="" placeholder="이메일 입력">
								<p id="emailHint">이메일을 입력해주세요.</p>
							</div>
							<button type="submit" class="submit_btn">인증하기</button>
							<a href="/member/findAccount/passwordResult.do">임시 비밀번호 받기 페이지로 이동</a>
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