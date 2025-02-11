<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의 민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/view-member-info.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20,300,0,0&icon_names=edit_square" />
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				
				<div class="info_wrap">
					<h1>회원 정보</h1>
					<div>
						<!-- 아이디/비밀번호 정보 -->
						<form>
							<div class="account_info">
								<h3>아이디</h3>
								<div class="id_field">
									<p>dyboo1346</p>
								</div>
							</div>
						</form>
						
						<!-- 기본 정보 -->
						<form>
							<div class="member_info">
								<div class="info_title">
									<h3>기본정보</h3>
									<div class="modify_btn_wrap">
										<img class="modify_btn" src="/images/mypage/modify_member.png">
										<a href="#">수정</a>
									</div>
								</div>
								<div class="name_field">
									<label>이름</label>
									<input type="hidden" id="nameInput" name="" value="" placeholder="이름 입력">
									<p>부다영</p>
								</div>
								<div class="nickname_field">
									<div class="nickname_label">
										<label>닉네임</label>
									</div>
									<input type="hidden" id="nicknameInput" name="" value="" placeholder="닉네임 입력">
									<p>외로운 참외</p>
								</div>
								<div class="phone_field">
									<label>휴대폰 번호</label>
									<input type="hidden" id="phoneInput" name="" value="" placeholder="- 없이 휴대폰 번호 입력">
									<p id="phoneHint">010-2084-0204</p>
								</div>
							</div>
						</form>
						
						<!-- 이메일 정보 -->
						<form>
							<div class="email_info">
								<div class="info_title">
									<h3>이메일</h3>
									<div class="modify_btn_wrap">
										<img class="modify_btn" src="/images/mypage/modify_member.png" alt="수정">
										<a href="#">수정</a>
									</div>
								</div>
								<div class="email_field">
<!-- 									<p>dyboo1346@naver.com</p> -->
									<!-- 이메일 수정 -->
									<div class="modify_email" style="display: none;">
										<label>이름</label>
										<input type="email" id="emailInput" name="" value="" placeholder="이메일 입력">
									</div>
								</div>
							</div>
						</form>
						
						<!-- 회원탈퇴 버튼 -->
						<div class="btn_wrap">
							<button type="submit" class="delete_btn">탈퇴하기</button>
						</div>
							
					</div>
				</div>
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>