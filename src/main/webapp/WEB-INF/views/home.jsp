<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="common/head.jsp" %>
		<link rel="stylesheet" href="/css/home.css">
		<script src="/js/home.js"></script>
	</head>
	
	<body>
		<%@include file="common/header.jsp" %>
		
		<main id="container">
		
			<!-- 메인 베너 -->
			<div class="main_banner">
				<div class="banner_inner">
					<img src="/images/home/main_mascot1.png"/>
					<div class="title">
						<h3>오늘 <span class="meal"></span> <span class="roulette"></span> 땡겨요</h3>
						<a class="roulette_link">모든 레시피 보러가기&nbsp;&nbsp;→</a>
					</div>
					<img src="/images/home/main_mascot2.png"/>
				</div>
			</div>
				
				<!-- 카테고리 메뉴 -->
				<div class="category_menu">
					<div class="type">
						<h2>카테고리로 레시피 검색!</h2>
						<br/>
						<ul class="category_list">
							<li>
								<img class="image" src="/images/home/bap.png">
								<p>밥/죽/떡</p>
							</li>
							<li>
								<img class="image" src="/images/home/soup.png">
								<p>국/탕</p>
							</li>
							<li>
								<img class="image" src="/images/home/pasta.png">
								<p>양식</p>
							</li>
							<li>
								<img class="image" src="/images/home/china.png">
								<p>중식</p>
							</li>
							<li>
								<img class="image" src="/images/home/pigfeet.png">
								<p>돼지고기</p>
							</li>
							<li>
								<img class="image" src="/images/home/chicken.png">
								<p>닭고기</p>
							</li>
							<li>
								<img class="image" src="/images/home/sushi.png">
								<p>회</p>
							</li>
							<li>
								<img class="image" src="/images/home/noodle.png">
								<p>면/만두</p>
							</li>
							<li>
								<img class="image" src="/images/home/lunch.png">
								<p>도시락</p>
							</li>
							<li>
								<img class="image" src="/images/home/night.png">
								<p>야식</p>
							</li>
							
						</ul>
					</div>
					<div class="case">
						<ul class="category_list">
							<li>
								<img class="image" src="/images/home/rice.png">
								<p>가공식품류</p>
							</li>
							<li>
								<img class="image" src="/images/home/burger.png">
								<p>해장</p>
							</li>
							<li>
								<img class="image" src="/images/home/life.png">
								<p>일상</p>
							</li>
							<li>
								<img class="image" src="/images/home/flower.png">
								<p>축하</p>
							</li>
							<li>
								<img class="image" src="/images/home/speed.png">
								<p>초스피드</p>
							</li>
							<li>
								<img class="image" src="/images/home/snackfood.png">
								<p>간식</p>
							</li>
							<li>
								<img class="image" src="/images/home/dessert.png">
								<p>빵</p>
							</li>
							<li>
								<img class="image" src="/images/home/cake.png">
								<p>디저트</p>
							</li>
							<li>
								<img class="image" src="/images/home/drink.png">
								<p>차/음료/술</p>
							</li>
							<li>
								<img class="image" src="/images/home/etc.png">
								<p>기타</p>
							</li>
						</ul>
					</div>	
				</div>
				
				<!-- 레시피 랭킹  -->
				<div class="recipe_ranking">
					<!-- 랭킹 헤더 -->
					<div class="ranking_header">
						<h2>현재 뜨고 있는 레시피</h2>
						<span>집밥은 역시 집밥의 민족</span>
						<button class="btn_sort" onclick="location.href='/recipe/listRecipe.do'">
							더보기
							<div class="btn_img"></div>
						</button>
					</div>
				    
				    <!-- 랭킹 리스트 -->
					<ul class="recipe_list"></ul>
				</div>
				
				<!-- 키친 가이드 랭킹 -->
				<div class="guide_ranking">
					<!-- 랭킹 헤더 -->
					<div class="ranking_header">
				        <h2>키친 가이드로 요리 스킬 UP!</h2>
						<span>집밥을 만들기 위한 꿀팁 대방출</span>
				        <button class="btn_sort" onclick="location.href='/kitchen/listGuide.do'">
				            더보기
				            <div class="btn_img"></div>
				        </button>
				    </div>
				    
				    <!-- 랭킹 리스트 -->
					<ul class="guide_list"></ul>
					
					<!-- 레시피 추천 -->
					<div class="clear_recipe">
						<ul class="pick_list">
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
							<li>
								<div class="recipe">
									<span>1</span>
									<p>김치볶음밥</p>
								</div>
							</li>
						</ul>
					</div>
				</div>
			
				<h2>마이페이지</h2>
				<button onclick="location.href='/mypage/profile.do';">프로필</button>
				<br/><br/>
				
				<h2>관리자페이지 테스트</h2>
				<button onclick="location.href='/admin/home.do';">관리자 페이지</button>
				<br/><br/>
				
		</main>
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>