<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/login.css">
		<script src="/js/member/login.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="login_wrap">
					<div>
						<div class="title">
							<h5>집밥의민족</h5>
							<h2>통합로그인</h2>
						</div>
					</div>
					
					<!--  폼 -->
					<form>
						<div class="id_field">
							<input type="text" id="idInput" name="" value="" placeholder="아이디">
							<p id="idHint">아이디를 입력해주세요.</p>
						</div>
						<div class="password_field">
							<input type="password" id="passwordInput" name="" value="" placeholder="비밀번호">
							<p id="passwordHint">비밀번호를 입력해주세요.</p>
						</div>
						<div class="support">
							<!-- 아이디 저장 -->
							<div class="save_id">
								<div class="checkbox_wrap">
									<input type="checkbox" id="saveId" name="" value="">
									<label for="saveId">아이디 저장</label>
								</div>
							</div>
							<!-- 비밀번호 찾기 및 회원가입 -->
							<div class="util">
								<a href="/member/findAccount.do">아이디 ・ 비밀번호 찾기</a>
								<a href="/member/join.do">회원가입</a>
							</div>
						</div>
						<button type="submit" class="btn_primary_wide">로그인</button>
					</form>
					
					<!-- 소셜 로그인 -->
					<div class="social">
						<div class="text">
							집밥의민족 신규가입하고<br/><span>다양한 레시피</span> 만나보세요!
						</div>
						<div class="image">
							<img src="/images/member/naver.png">
							<img src="/images/member/kakao.png">
						</div>
					</div>
					
					<!-- 출처 -->
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
	</body>
</html>