<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/applied-class.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/mypage/applied-class.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div id="AppliedClassWrap" class="class_wrap">

					<div class="class_header">
						<a href="/mypage.do">
							<span>
								내 정보 관리
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>클래스 신청 현황</h2>
					</div>
					
					<div class="class_util">
						<p class="total"></p>
						<div class="class_sort">
							<button class="btn_sort active" data-status="">전체</button>
							<button class="btn_sort" data-status="open">진행중</button>
							<button class="btn_sort" data-status="close">마감</button>
						</div>
					</div>
					
					<div class="class_absent">
						<button>
							<span>최근 60일 0회 결석</span>
						</button>
					</div>
					
					<!-- 클래스 목록 -->
					<ul class="class_list"></ul>
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