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
		<link rel="stylesheet" href="/css/user/modify-user.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20,300,0,0&icon_names=edit_square" />
		<script src="/js/user/user-info.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="info_wrap">
					<div>
						<h2>회원정보</h2>
						
						<!-- 아이디 정보 -->
						<form>
							<div class="account_info">
								<h3>아이디</h3>
								<div class="id_field">
									<input type="hidden" id="idInput" name="" value="" placeholder="영문 혹은 영문+숫자, 4~20자">
									<p>dyboo1346</p>
								</div>
							</div>
						</form>
						
						<!-- 기본 정보 -->
						<form>
							<div class="member_info">
								<div class="info_title">
									<h3>기본정보</h3>
									<div class="edit_btn_wrap" id="basicModifyBtn">
										<img id="basicIcon" class="edit_btn" src="/images/user/edit_user.png">
										<a href="#" id="basicState">수정</a>
									</div>
								</div>
								<div id="basicInfo">
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
								<!-- 기본정보 수정폼 -->
								<div id="modifyBasicInfo" class="hidden">
									<div class="name_field">
										<label>이름</label>
										<input type="text" id="nameInput" name="" value="부다영" placeholder="이름 입력">
									</div>
									<div class="nickname_field">
										<div class="nickname_label">
											<label>닉네임</label>
										</div>
										<input type="text" id="nicknameInput" name="" value="외로운 참외" placeholder="닉네임 입력">
									</div>
									<div class="phone_field">
										<label>휴대폰 번호</label>
										<input type="text" id="phoneInput" name="" value="010-2084-0204" placeholder="- 없이 휴대폰 번호 입력">
									</div>
									<div class="email_btn_wrap">
										<button type="submit" class="save_btn">저장</button>
									</div>
								</div>
								
								
								
							</div>
						</form>
						
						<!-- 이메일 정보 -->
						<form>
							<div class="email_info">
								<div class="info_title">
									<h3>이메일</h3>
									<div class="edit_btn_wrap" id="emailModifyBtn">
										<img id="emailIcon" class="edit_btn"  src="/images/user/edit_user.png">
										<a href="#" id="emailState">수정</a>
									</div>
								</div>
								<div class="email_field">
									<input type="hidden" id="emailInput" name="" value="" placeholder="이메일 입력">
									<p id="emailText">dyboo1346@naver.com</p>
									
									<!-- 이메일 수정폼 -->
									<div id="modifyEmail" class="hidden">
										<div class="modify_email">
											<label>이메일</label>
											<input type="email" name="" value="" placeholder="이메일 입력">
										</div>
										<div class="email_warning">
											<img src="/images/user/error.png">
											<p>이메일로 비밀번호 변경 링크 등이 발송됩니다. 개인정보 보호를 위해 정확한 메일 정보를 입력해주세요.</p>
										</div>
										<div class="email_btn_wrap">
											<button type="submit" class="save_btn">저장</button>
										</div>
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
				
				<!-- 하단 정보 -->
				<div class="buttom_info">
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
	</body>
</html>