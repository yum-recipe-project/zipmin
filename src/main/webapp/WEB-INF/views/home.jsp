<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의 민족</title>
		<%@include file="common/head.jsp" %>
		<link rel="stylesheet" href="/css/home.css">
		<script src="/js/home.js"></script>
	</head>
	
	<body>
		<%@include file="common/header.jsp" %>
		<main id="container">
			<div class="content">
				<!-- 메인 배너 -->
				<div class="main_banner">
					<img src="/images/home/main_mascot.png"/>
					<div class="txt">
						<p class="title">오늘 저녁 <span>마라탕</span> 땡겨요</p>
						<button class="btn_sort" onclick="location.href='/recipe/listRecipe.do'">
				            마라탕 레시피 보러가기
				            <div class="btn_img"></div>
				        </button>
					</div>
				</div>
				
				<!-- 카테고리 메뉴 -->
				<div class="category_menu">
					<div class="type">
						<h2>종류별</h2>
						<ul class="category_list">
							<li>
								<div class="image"></div>
								<p>치킨</p>
							</li>
							<li>
								<div class="image"></div>
								<p>중식</p>
							</li>
							<li>
								<div class="image"></div>
								<p>돈까스</p>
							</li>
							<li>
								<div class="image"></div>
								<p>회</p>
							</li>
							<li>
								<div class="image"></div>
								<p>피자</p>
							</li>
							<li>
								<div class="image"></div>
								<p>찜</p>
							</li>
							<li>
								<div class="image"></div>
								<p>탕</p>
							</li>
							<li>
								<div class="image"></div>
								<p>족발</p>
							</li>
							<li>
								<div class="image"></div>
								<p>보쌈</p>
							</li>
							<li>
								<div class="image"></div>
								<p>중식</p>
							</li>
						</ul>
					</div>
					<div class="case">
						<h2>상황별</h2>
						<ul class="category_list">
							<li>
								<div class="image"></div>
								<p>명절</p>
							</li>
							<li>
								<div class="image"></div>
								<p>술안주</p>
							</li>
							<li>
								<div class="image"></div>
								<p>다이어트</p>
							</li>
							<li>
								<div class="image"></div>
								<p>해장</p>
							</li>
						</ul>
					</div>	
				</div>
				
				<!-- 레시피 랭킹  -->
				<div class="recipe_ranking">
					
					<!-- 랭킹 헤더 -->
					<div class="ranking_header">
						<div class="txt">
							<span>집밥은 역시 집밥의 민족</span>
					        <h2>현재 뜨고 있는 레시피</h2>
						</div>
					    <div class="dropdown">
					        <button class="btn_sort" id="recipeSortBtn">
					            찜 많은 순
					            <div class="btn_img"></div>
					        </button>
					        <ul class="dropdown_menu">
					            <li>찜 많은 순</li>
					            <li>별점 높은 순</li>
					        </ul>
					    </div>
				    </div>
				    
				    <!-- 랭킹 리스트 -->
					<ul class="recipe_list">
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능합니당. 뒤부터는 말줄임표</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능...</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능합니당. 뒤부터는 말줄임표</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능합니당. 뒤부터는 말줄임표</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능합니당. 뒤부터는 말줄임표</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/recipe/viewRecipe.do">
					            <div class="recipe_card">
					                <div class="image"></div>
					                <p>먹짱이 김치볶음밥이고 두줄까지는 가능...</p>
					                <span>초급 / 30분 / 아주 매움</span>
					            </div>
					        </a>
					    </li>
					</ul>
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
					<ul class="guide_list">
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					    <li>
					        <a href="/kitchen/viewGuide.do">
					            <div class="guide_card">
					                <div class="image"></div>
					                <p>칼에 버터 안 묻히고 소분 하는 법</p>
					                <span>스크랩 29</span>
					            </div>
					        </a>
					    </li>
					</ul>
				</div>
			
				<h2>회원</h2>
				<button onclick="location.href='/user/verifyPassword.do';">비밀번호 확인</button>
				<br/><br/>
				
				<h2>마이페이지</h2>
				<button onclick="location.href='/mypage/profile.do';">프로필</button>
				<br/><br/>
				
				<h2>관리자페이지 테스트</h2>
				<button onclick="location.href='/admin/home.do';">관리자 페이지</button>
				<br/><br/>
				
			</div>
		</main>
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>