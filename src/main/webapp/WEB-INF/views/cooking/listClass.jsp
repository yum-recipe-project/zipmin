<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/cooking/list-class.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/cooking/list-class.js"></script>
		<script src="/js/common/pagination.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="class_header">
					<h2>쿠킹클래스</h2>
					<div class="tab">
						<a class="btn_tab active" data-tab="" href="">
							<span>전체</span>
						</a>
						<a class="btn_tab" data-tab="한식" href="">
							<span>한식</span>
						</a>
						<a class="btn_tab" data-tab="양식" href="">
							<span>양식</span>
						</a>
						<a class="btn_tab" data-tab="중식" href="">
							<span>중식</span>
						</a>
						<a class="btn_tab" data-tab="일식" href="">
							<span>일식</span>
						</a>
						<a class="btn_tab" data-tab="제과제빵" href="">
							<span>제과제빵</span>
						</a>
						<a class="btn_tab" data-tab="음료" href="">
							<span>음료</span>
						</a>
						<a class="btn_tab" data-tab="기타" href="">
							<span>기타</span>
						</a>
					</div>
				</div>
			
				<div class="class_content">
					<div class="class_util">
						<p class="total"></p>
						<div class="class_sort">
							<button class="btn_sort active" data-status="">전체</button>
							<button class="btn_sort" data-status="open">모집중</button>
							<button class="btn_sort" data-status="close">마감</button>
						</div>
					</div>
					
					<!-- 클래스 목록 -->
					<ul class="class_list"></ul>
				</div>
					
				<!-- 페이지네이션 -->
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