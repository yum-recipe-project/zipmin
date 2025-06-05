<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의 민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/user-info.css">
		<script src="/js/user/user-info.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="info_wrap">
					<h2>회원정보</h2>
					
					<!-- 아이디 정보 -->
					<div class="account_info">
						<h3>아이디</h3>
						<div class="username_field">
							<p class="username"></p>
						</div>
					</div>
					
					<!-- 기본 정보 -->		
					<div class="user_info">
						<div class="info_title">
							<h3>기본정보</h3>
							<div class="edit_btn_wrap" id="edit-basic-info-btn">
								<img class="edit_btn" src="/images/user/edit_user.png">
								<a href="#">수정</a>
							</div>
						</div>
						
						<form id="basic-info-form">
							<div class="name_field">
								<label>이름</label>
								<p class="name">하하</p>
								<input type="text" name="name" value="" placeholder="이름 입력">
								<p class="name_hint">이름을 입력해주세요</p>
							</div>
							<div class="nickname_field">
								<label>닉네임</label>
								<p class="nickname"></p>
								<input type="text" name="nickname" value="" placeholder="닉네임 입력">
								<p class="nickname_hint">닉네임을 입력해주세요</p>
							</div>
							<div class="tel_field">
								<label>휴대폰 번호</label>
								<p class="tel"></p>
								<input type="text" name="tel" value="" placeholder="- 없이 휴대폰 번호 입력">
								<p class="tel_hint">휴대폰 번호를 입력해주세요</p>
							</div>
							<div id="submit-basic-info-btn">
								<button type="submit" class="btn_primary_wide">저장</button>
							</div>
						</form>
					</div>
							
					
						<!-- 이메일 정보 -->
						<div class="user_info">
							<div class="info_title">
								<h3>이메일</h3>
								<div class="edit_btn_wrap" id="edit-email-info-btn">
									<img class="edit_btn" src="/images/user/edit_user.png">
									<a href="#">수정</a>
								</div>
							</div>
														
							<!-- 이메일 수정폼 -->
							<form id="email-info-form">
								<div class="email_field">
									<label>이메일</label>
									<p class="email"></p>
									<input type="email" name="email" value="" placeholder="이메일 입력">
									<p class="email_hint">이메일을 입력해주세요.</p>
								</div>
								<div class="email_warning">
									<img src="/images/user/error.png">
									<p>이메일로 비밀번호 변경 링크 등이 발송됩니다. 개인정보 보호를 위해 정확한 메일 정보를 입력해주세요.</p>
								</div>
								<div id="submit-email-info-btn">
									<button type="submit" class="btn_primary_wide">저장</button>
								</div>
							</form>
						</div>
						
						<!-- 회원탈퇴 버튼 -->
						<div class="btn_wrap">
							<button id="user_delete_btn" type="submit" class="btn_outline">탈퇴하기</button>
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