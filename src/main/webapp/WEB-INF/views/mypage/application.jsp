<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/application.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/mypage/application.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div class="apply_wrap">
					<!-- 신청서 헤더 -->
					<div class="apply_header">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>한식 입문 클래스 신청서</h2>
					</div>
					
					<!-- 신청서 유틸 -->
					<div class="apply_util">
						<p class="total"></p>
						<div class="apply_sort">
							<button class="btn_sort active" data-sort="-1">전체</button>
							<button class="btn_sort" data-sort="1">선정</button>
							<button class="btn_sort" data-sort="0">대기</button>
						</div>
					</div>
					
					<!-- 신청서 목록 -->
					<ul class="apply_list"></ul>
				</div>
				
				<div class="pagination_wrap">
					<div class="pagination">
					    <ul></ul>
					</div>
				</div>
			</div>
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>