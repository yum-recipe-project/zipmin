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
				</div>
				
				<!-- 네비게이션 버튼 -->
				<div class="nav_wrap">
					<ul>
						<li class="btn_tab"><a href="" data-tab="recipe" class="active"><span>레시피</span></a></li>
						<li class="btn_tab"><a href="" data-tab="classes"><span>클래스</span></a></li>
					</ul>
				</div>
				
				<!-- 사용자 레시피 -->
				<div id="recipeWrap" class="recipe_wrap tab_content">
					<!-- 제목 -->
					<div class="recipe_util">
						<p class="recipe_header">레시피 3개</p>
						<div class="recipe_sort">
							<button class="btn_sort active" data-sort="postdate-desc">최신순</button>
							<button class="btn_sort" data-sort="likecount-desc">인기순</button>
							<button class="btn_sort" data-sort="reviewscore-desc">별점순</button>
						</div>
					</div>
					
					<!-- 레시피 목록 -->
					<ul class="recipe_list">
						<li class="recipe">
							<a href="">
								<img class="recipe_image" src="/images/common/te-st.png">
							</a>
							<div class="recipe_info">
								<h3 class="recipe_title">레시피 제목</h3>
								<p class="recipe_introduce">레시피를 소개할게요!!! 엄청 좋은 레시피입니다!! 줄을 넘겨볼게요 ㅎㅎㅎ 어떻게 되나요?????</p>
								<div class="recipe_detail">
									<div class="detail_item"><img src="/images/recipe/level.png"><p>중급</p></div>
									<div class="detail_item"><img src="/images/recipe/time.png"><p>10분</p></div>
									<div class="detail_item"><img src="/images/recipe/spicy.png"><p>아주 매움</p></div>
								</div>
								<div class="recipe_category">
									<a href="">어쩌구</a>
									<a href="">어쩌구</a>
								</div>
							</div>
						</li>
						<li class="recipe">
							<a href="">
								<img class="recipe_image" src="/images/common/te-st.png">
							</a>
							<div class="recipe_info">
								<h3 class="recipe_title">레시피 제목</h3>
								<p class="recipe_introduce">레시피를 소개할게요!!! 엄청 좋은 레시피입니다!! 줄을 넘겨볼게요 ㅎㅎㅎ 어떻게 되나요?????</p>
								<div class="recipe_detail">
									<div class="detail_item"><img src="/images/recipe/level.png"><p>중급</p></div>
									<div class="detail_item"><img src="/images/recipe/time.png"><p>10분</p></div>
									<div class="detail_item"><img src="/images/recipe/spicy.png"><p>아주 매움</p></div>
								</div>
								<div class="recipe_category">
									<a href="">어쩌구</a>
									<a href="">어쩌구</a>
								</div>
							</div>
						</li>
					</ul>
					
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
						<p class="class_header">클래스 2개</p>
						<div class="class_sort">
							<button class="btn_sort active" data-sort="postdate-desc">최신순</button>
							<button class="btn_sort" data-sort="applycount-desc">인기순</button>
						</div>
					</div>
					
					<ul class="class_list"></ul>
					
					<!-- 클래스 페이지네이션 -->
					<div class="pagination_wrap ">
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