<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/profile.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/mypage/profile.js"></script>
		<script src="/js/common/pagination.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
					
				<!-- 유저 정보 -->
				<div id="userWrap" class="user_wrap">
					<div class="user">
						<span class="user_avatar"></span>
						<div class="user_info">
							<div class="user_title">
								<h2 class="user_nickname"></h2>
								<button class="btn btn_dark">팔로우</button>
							</div>
							<p class="user_introduce">안녕하세요 아잠만입니다 레시피 열심히 작성할게요 !!</p>
							<div class="user_count">
								<p>팔로우</p><span class="likecount"></span>
								<p>•</p>
								<p>레시피</p><span class="recipecount"></span>
							</div>
						</div>
					</div>
					<div class="user_link"></div>
				</div>
				
				<!-- 네비게이션 버튼 -->
				<div class="nav_wrap">
					<ul>
						<li class="btn_tab"><a href="" data-tab="recipe" class="active"><span>레시피</span></a></li>
						<li class="btn_tab"><a href="" data-tab="cooking"><span>클래스</span></a></li>
					</ul>
				</div>
				
				<!-- 사용자 레시피 -->
				<div id="recipeWrap" class="recipe_wrap tab_content">
					<!-- 제목 -->
					<div class="recipe_util">
						<p class="recipe_header"></p>
						<div class="recipe_sort">
							<button class="btn_sort active" data-sort="postdate-desc">최신순</button>
							<button class="btn_sort" data-sort="likecount-desc">인기순</button>
							<button class="btn_sort" data-sort="reviewscore-desc">별점순</button>
						</div>
					</div>
					
					<!-- 레시피 목록 -->
					<ul class="recipe_list"></ul>
					
					<!-- 레시피 페이지네이션 -->
					<div class="pagination_wrap">
						<div class="pagination">
						    <ul></ul>
						</div>
					</div>
				</div>
				
				<!-- 사용자 클래스 -->
				<div id="classWrap" class="class_wrap tab_content">
					<!-- 제목 -->
					<div class="class_util">
						<p class="class_header"></p>
						<div class="class_sort">
							<button class="btn_sort active" data-status="">전체</button>
							<button class="btn_sort" data-status="open">모집중</button>
							<button class="btn_sort" data-status="close">마감</button>
						</div>
					</div>
					
					<!-- 클래스 목록 -->
					<ul class="class_list"></ul>
					
					<!-- 클래스 페이지네이션 -->
					<div class="pagination_wrap">
						<div class="pagination">
						    <ul></ul>
						</div>
					</div>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>