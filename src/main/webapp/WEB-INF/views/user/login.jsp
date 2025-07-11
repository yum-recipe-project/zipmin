<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/login.css">
		<script src="/js/user/login.js"></script>
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
					
					<!-- 자체 로그인 -->
					<form>
						<div class="username_field">
							<input type="text" name="username" value="" placeholder="아이디">
							<p>아이디를 입력해주세요.</p>
						</div>
						<div class="password_field">
							<input type="password" name="password" value="" placeholder="비밀번호">
							<p>비밀번호를 입력해주세요.</p>
						</div>
						<h6 class="alert">
							<img src="/images/user/alert.png">
							아이디 또는 비밀번호가 일치하지 않습니다.
						</h6>
						<div class="support">
							<!-- 아이디 저장 -->
							<div class="save_id">
								<div class="checkbox_wrap">
									<input type="checkbox" id="save-id" value="">
									<label for="save-id">아이디 저장</label>
								</div>
							</div>
							<!-- 비밀번호 찾기 및 회원가입 -->
							<div class="util">
								<a href="/user/findAccount.do">아이디 ・ 비밀번호 찾기</a>
								<a href="/user/join.do">회원가입</a>
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
							<!-- 사진 변경 필요!!!!!!! css 변경 필요!!!! -->
							<a href="/oauth2/authorization/google">
							  <img src="/images/user/kakao.png" alt="구글 로그인">
							</a>
							<a href="/oauth2/authorization/naver">
							  <img src="/images/user/naver.png" alt="네이버 로그인">
							</a>
						</div>
						
						
					</div>
					
					<!-- 출처 -->
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
	</body>
</html>