<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			
				<!-- 키친가이드 헤더 -->
				<div class="guide_header">
					<h2>키친가이드</h2>
					<div class="tab">
						<a class="btn_tab active" href="" data-category=""><span>전체</span></a>
						<a class="btn_tab" href="" data-category="preparation"><span>손질법</span></a>
						<a class="btn_tab" href="" data-category="storage"><span>보관법</span></a>
						<a class="btn_tab" href="" data-category="cooking"><span>요리 정보</span></a>
						<a class="btn_tab" href="" data-category="etc"><span>기타 정보</span></a>
					</div>
				</div>
				
				<!-- 키친가이드 내용 -->
				<div class="guide_content">
					<div class="guide_util">
						<p class="total"></p>
						<div class="guide_sort">
							<button class="btn_sort active" data-sort="id-desc">최신순</button>
							<button class="btn_sort" data-sort="likecount-desc">인기순</button>
						</div>
					</div>
					<ul class="guide_ul"></ul>
				</div>
	
				<div class="pagination_wrap"></div>
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>