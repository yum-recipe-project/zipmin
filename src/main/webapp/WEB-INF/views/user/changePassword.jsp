<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/change-password.css">
		<script src="/js/user/change-password.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="change_wrap">
					<div>
						<h2>비밀번호 변경</h2>
						<form>
							<div class="old_password_field">
								<label>현재 비밀번호</label>
								<input type="text" id="oldPasswordInput" name="" value="" placeholder="현재 비밀번호 입력">
								<p id="oldPasswordHint">비밀번호를 입력해주세요.</p>
							</div>
							<div class="new_password_field">
								<label>새 비밀번호</label>
								<label class="sub_label">영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상</label>
								<input type="password" id="newPasswordInput" name="" value="" placeholder="비밀번호 입력">
								<p id="newPasswordHint">비밀번호를 입력해주세요.</p>
							</div>
							<div class="check_password_field">
								<label>현재 비밀번호</label>
								<input type="password" id="checkPasswordInput" name="" value="" placeholder="현재 비밀번호 입력">
								<p id="checkPasswordHint">비밀번호를 입력해주세요.</p>
							</div>
							<div class="notice">
								<p>・&nbsp;&nbsp;원활한 이용을 위해 안전한 비밀번호로 설정해 주세요.</p>
								<p>・&nbsp;&nbsp;비밀번호 변경 후 변경된 비밀번호를 입력하여 다시 로그인 해주세요.</p>
							</div>
							
							<!-- 버튼 -->
							<button type="submit" class="btn_primary_wide">다음</button>
						</form>
					</div>
					
					<!-- 하단 정보 -->
					<div class="buttom_info">
						<p class="copy">© Yum Recipe Project</p>
					</div>
				</div>
			</div>
		</main>
	</body>
</html>