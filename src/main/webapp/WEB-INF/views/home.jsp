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
					
					<a href="/kitchen/viewGuide.do">키친가이드 상세보기</a>
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