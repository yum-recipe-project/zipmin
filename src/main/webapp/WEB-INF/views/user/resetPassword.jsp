<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/user/change-password.css">
		<script src="/js/user/reset-password.js"></script>
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="change_wrap">
					<div>
						<h2>새로운 비밀번호를 입력해주세요</h2>
						<form id="resetPasswordForm">
							<div class="new_password_field">
								<label>새 비밀번호</label>
								<label class="sub_label">영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상</label>
								<input type="password" name="newPassword" value="" placeholder="비밀번호 입력">
								<p>새 비밀번호를 입력해주세요.</p>
							</div>
							<div class="check_password_field">
								<label>새 비밀번호 확인</label>
								<input type="password" name="checkPassword" value="" placeholder="현재 비밀번호 입력">
								<p>비밀번호 확인을 입력해주세요.</p>
								<p>비밀번호가 일치하지 않습니다.</p>
							</div>
							<button type="submit" class="btn_primary_wide">변경하기</button>
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