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
	</head>
	
	<body>
		<%@include file="common/header.jsp" %>
		<main id="container">
			<div class="content">
				<!-- 메인 배너 -->
				<div class="main_banner">
					<h2>회원</h2>
					<button onclick="location.href='/member/verifyPassword.do';">비밀번호 확인</button>
					<button onclick="location.href='/member/changePassword.do';">비밀번호 변경</button>
					<button onclick="location.href='/member/userInfo.do';">회원 정보</button>
					<br/><br/>
					
					<h2>레시피</h2>
					<button onclick="location.href='/recipe/listRecipe.do';">레시피 목록</button>
					<button onclick="location.href='/recipe/viewRecipe.do';">레시피 상세</button>
					<button onclick="location.href='/recipe/writeRecipe.do';">레시피 작성</button>
					<br/><br/>
					
					<h2>키친가이드</h2>
					<button onclick="location.href='/kitchen/listGuide.do';">키친가이드 목록</button>
					<button onclick="location.href='/kitchen/viewGuide.do';">키친가이드 상세</button>
					<br/><br/>
					
					<h2>쩝쩝박사</h2>
					<button onclick="location.href='/chompessor/listForum.do';">쩝쩝박사 목록</button>
					<button onclick="location.href='/chompessor/viewForum.do';">쩝쩝박사 상세</button>
					<br/><br/>
					
					<h2>나의 냉장고</h2>
					<button onclick="location.href='/fridge/viewFridge.do';">나의 냉장고</button>
					<button onclick="location.href='/fridge/viewMemo.do';">장보기 메모</button>
					<br/><br/>
					
					<h2>쿠킹클래스</h2>
					<button onclick="location.href='/cooking/listClass.do';">쿠킹클래스 목록</button>
					<button onclick="location.href='/cooking/viewClass.do';">쿠킹클래스 상세</button>
					<br/><br/>
					
					<h2>후원</h2>
					<br/><br/>
					
					<h2>마이페이지</h2>
					<button onclick="location.href='/mypage.do';">마이페이지</button>
					<button onclick="location.href='/mypage/profile.do';">프로필</button>
					<button onclick="location.href='/mypage/follower.do';">팔로워</button>
					<button onclick="location.href='/mypage/following.do';">팔로잉</button>
					<button onclick="location.href='/mypage/comment.do';">내 댓글</button>
					<button onclick="location.href='/mypage/review.do';">내 리뷰</button>
					<button onclick="location.href='/mypage/recipe.do';">내 레시피</button>
					<button onclick="location.href='/mypage/savedRecipe.do';">저장한 레시피</button>
					<button onclick="location.href='/mypage/savedGuide.do';">저장한 가이드</button>
					<button onclick="location.href='/mypage/appliedClass.do';">신청한 클래스</button>
					<br/><br/>
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
				
			</div>

				<!--  -->
				<div class="">
					<div class="inner">
					
					</div>
				</div>
		</main>
		
		
		
		
		
		
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>