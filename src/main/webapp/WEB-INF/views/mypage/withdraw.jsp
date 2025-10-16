<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/withdraw.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/mypage/withdraw.js"></script>
		<script src="/js/modal/change-account-modal.js"></script>
		<script src="/js/modal/withdraw-point-modal.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div class="withdraw_wrap">

					<div class="withdraw_header">
						<a href="/mypage.do">
							<span>
								내 정보 관리
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>출금 내역</h2>
					</div>
					
					<div class="withdraw_util">
					  	<p class="total">총 2개</p>
					</div>
					
					
					<!-- 수익 or 출금에 표시 -->
<!-- 					<div class="withdraw_point"> -->
<!-- 						<div class="point"> -->
<!-- 							<span>출금</span> -->
<!-- 							<span></span> -->
<!-- 						</div> -->
<!-- 						<div class="btn_wrap"> -->
<!-- 							<button class="btn_outline" data-bs-toggle="modal" data-bs-target="#changeAccountModal" -->
<!-- 								onclick="openChangeAccountModal()"> -->
<!-- 								출금 계좌 관리 -->
<!-- 							</button> -->
<!-- 							<button class="btn_dark" data-bs-toggle="modal" data-bs-target="#withdrawPointModal" -->
<!-- 								onclick="openWithdrawPointModal()"> -->
<!-- 								출금하기 -->
<!-- 							</button> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
					
					
					<ul class="withdraw_list"></ul>
					
				</div>
				
				<div class="pagination_wrap">
					<div class="pagination">
					    <ul></ul>
					</div>
				</div>
				
			</div>
			
			<!-- 출금 계좌 모달창 -->
			<%@include file="../modal/changeAccountModal.jsp" %>
			
			<!-- 출금 모달창 -->			
			<%@include file="../modal/withdrawPointModal.jsp" %>
			
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>