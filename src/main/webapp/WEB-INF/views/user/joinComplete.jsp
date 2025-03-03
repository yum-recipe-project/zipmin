<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/member/join-complete.css">
	</head>
	
	<body>
		<main id="container">
			<div class="content">
				<div class="complete_wrap">
					<div>
						<h2>집밥의민족 가입을<br/>축하합니다!</h2>
						<h5>
							집밥의민족 계정이 생성되었습니다.<br/>
							회원정보를 확인한 후 회원가입을 완료해주세요.
						</h5>
						<div class="join_info">
							<h3>회원 정보</h3>
							<dl>
								<dt>아이디</dt>
								<dd>jhrchicken</dd>
								<dt>이름</dt>
								<dd>정하림</dd>
								<dt>닉네임</dt>
								<dd>아잠만</dd>
								<dt>휴대폰번호</dt>
								<dd>010-0000-0000</dd>
								<dt>이메일</dt>
								<dd>aaa@naver.com</dd>
							</dl>
						</div>
						
						<!-- 수정 알림문 -->
						<p class="edit_info">• 위 내용은 <b>집밥의민족 회원 정보</b>에서 수정할 수 있습니다</p>
						
						<!-- 완료버튼 -->
						<a href="/" class="btn_primary_wide">완료</a>
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