<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/list-guide.css">
		<script src="/js/kitchen/list-guide.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="guide_header">
					<h2>키친가이드 목록</h2>
					<div class="tab">
						<a class="btn_tab active" href="">
							<span>전체</span>
						</a>
						<a class="btn_tab" href="">
							<span>손질법</span>
						</a>
						<a class="btn_tab" href="">
							<span>보관법</span>
						</a>
						<a class="btn_tab" href="">
							<span>요리 정보</span>
						</a>
						<a class="btn_tab" href="">
							<span>기타 정보</span>
						</a>
					</div>
				</div>
				
				<div class="guide_content">
				
					<div class="guide_util">
						<p class="total">총 23개</p>
						<div class="guide_sort">
							<button class="btn_sort active">최신순</button>
							<button class="btn_sort">인기순</button>
						</div>
					</div>
						
						
				</div>
			
			
			
			
			
			
			
			
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>