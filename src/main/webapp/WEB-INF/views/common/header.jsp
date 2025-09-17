<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header id="header">

	<!-- 헤더 상단 -->
	<div class="header_top">
		<div class="inner">
			<!-- 키친가이드 팁 -->
			<div class="cook_tip">
				<a href="#none">
					<span class="tip_text"></span>
				</a>
			</div>
			
			<!-- 유틸 메뉴 -->
			<div class="util_menu">
				<!-- 비로그인 상태 -->
				<div class="logout_state">
					<a href="/user/login.do">로그인</a>
					<div class="divider"></div>
					<a href="/user/join.do">회원가입</a>
				</div>
				<!-- 로그인 상태 -->
				<div class="login_state">
					<span class="user_name"></span>
					<div class="divider"></div>
					<a href="/mypage.do">마이페이지</a>
					<div class="divider"></div>
					<a href="" id="logout">로그아웃</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 헤더 하단 -->
	<div class="header_bottom">
		<div class="inner">
			<!-- 로고 -->
			<a class="logo" href="/">
				<img src="/images/common/logo.png">
			</a>
			<!-- 메뉴 -->
			<nav class="gnb">
				<ul>
					<li><a href="/recipe/listRecipe.do">레시피</a></li>
					<li><a href="/kitchen/listGuide.do">키친가이드</a></li>
					<li><a href="/chompessor/listChomp.do">쩝쩝박사</a></li>
					<li><a href="/cooking/listClass.do">쿠킹클래스</a></li>
					<li><a href="/fridge/viewFridge.do">나의 냉장고</a></li>
					<!-- <li><a href="/fridge/viewMemo.do">장보기메모</a></li> -->
				</ul>
			</nav>
			<!-- 검색창 -->
			<form class="search_form" data-type="recipe">
				<input type="text" class="search_word" placeholder="레시피를 검색하세요">
				<button type="submit" class="search_btn">
					<img src="/images/common/search.png">
				</button>
			</form>
			<form class="search_form" data-type="cooking">
				<input type="text" class="search_word" placeholder="쿠킹클래스를 검색하세요">
				<button type="submit" class="search_btn">
					<img src="/images/common/search.png">
				</button>
			</form>
			<form class="search_form" data-type="chompessor">
				<input type="text" class="search_word" placeholder="쩝쩝박사를 검색하세요">
				<button type="submit" class="search_btn">
					<img src="/images/common/search.png">
				</button>
			</form>
			<form class="search_form" data-type="kitchen">
				<input type="text" class="search_word" placeholder="키친가이드를 검색하세요">
				<button type="submit" class="search_btn">
					<img src="/images/common/search.png">
				</button>
			</form>
			<form class="search_form">
				<input type="text" class="search_word" placeholder="검색어를 입력하세요">
				<button type="submit" class="search_btn">
					<img src="/images/common/search.png">
				</button>
			</form>
		</div>
	</div>
</header>