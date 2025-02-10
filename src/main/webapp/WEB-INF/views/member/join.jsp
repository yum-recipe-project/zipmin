<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/join.css">
		<script src="/js/member/join.js"></script>
	</head>
	<body>
		<main id="container">
			<div class="content">
				<div class="join_wrap">
					<div>
						<h3>회원 정보를 입력 후, 가입을 완료해주세요</h3>
						<form>
							<!-- 기본 정보 -->
							<div class="basic_info">
								<h3>기본정보</h3>
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
							</div>
							
							<!-- 회원 정보 -->
							<div class="member_info">
								<h3>회원정보</h3>
								<div class="id_field">
									<label>아이디</label>
									<input type="text" id="idInput" name="" value="" placeholder="영문 혹은 영문+숫자, 4~20자">
									<p id="idHint">아이디를 입력해주세요.</p>
								</div>
								<div class="password_field">
									<label>비밀번호</label>
									<label class="sub_label">영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상</label>
									<input type="password" id="passwordInput" name="" value="" placeholder="비밀번호">
									<input type="password" id="passwordInputCheck" name="" value="" placeholder="비밀번호 재입력">
									<p id="passwordHint">비밀번호를 입력해주세요.</p>
									<p id="passwordHintCheck">비밀번호 확인을 위해 한번 더 입력해주세요.</p>
								</div>
								<div class="nickname_field">
									<label>닉네임</label>
									<input type="text" id="nicknameInput" name="" value="" placeholder="닉네임 입력">
									<p id="nicknameHint">닉네임을 입력해주세요.</p>
								</div>
								<div class="email_field">
									<label>이메일</label>
									<input type="email" id="emailInput" name="" value="" placeholder="이메일 입력">
									<p id="emailHint">이메일을 입력해주세요.</p>
								</div>
							</div>
							
							<!-- 이메일 안내문 -->
							<div class="email_info">
								<img src="/images/member/error.png">
								<p>개인정보 보호를 위해 본인이 소유한 이메일을 정확히 입력해주세요.</p>
							</div>
							
							<!-- 다음 버튼 -->
							<button type="submit" class="next_btn">다음</button>
							<a href="/member/join/complete.do">다음</a>
						</form>
					</div>
				</div>
				
				<!-- 하단 정보 -->
				<div class="bottom_info">
					<p class="copy">© Yum Recipe Project</p>
				</div>
			</div>
		</main>
	</body>
</html>