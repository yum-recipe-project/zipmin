<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/list-chomp.css">
		<script src="/js/chompessor/list-chomp.js"></script>
	</head>

	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				
				<div class="forum_header">
					<h2>쩝쩝박사</h2>
					<div class="tab">
						<a class="btn_tab active" data-tab="all" href="">
							<span>전체</span>
						</a>
						<a class="btn_tab" data-tab="vote" href="">
							<span>투표</span>
						</a>
						<a class="btn_tab" data-tab="megazine" href="">
							<span>매거진</span>
						</a>
						<a class="btn_tab" data-tab="event" href="">
							<span>이벤트</span>
						</a>
					</div>
				</div>
			
				<div class="forum_content">
				
					<!-- 쩝쩝박사 정렬 요소 -->
					<div class="forum_util">
						<p>투표와 이벤트에 참여해보세요!</p>
						<!-- <div class="forum_sort">
							<button class="btn_sort active" data-sort="all">전체</button>
							<button class="btn_sort" data-sort="open">진행중</button>
							<button class="btn_sort" data-sort="close">진행종료</button>
						</div> -->
					</div>
					
					<!-- 목록 -->
					<c:if test="${ true }">
						<ul class="forum_list" id="chomp"></ul>
					</c:if>
				</div>
				
				<!-- 더보기 버튼 -->
<!-- 				<div class="more_wrap">
					<button class="btn_more">
						<span>더보기</span>
						<img src="/images/mypage/arrow_down.png">
					</button>
				</div> -->
				
				<!-- 페이지네이션 -->
				<div class="pagination_wrap">
					<div class="pagination">
					    <ul>
					        <li><a href="#" class="prev"><img src="/images/common/chevron_left.png"></a></li>
					        <li><a href="#" class="page active">1</a></li>
					        <li><a href="#" class="page">2</a></li>
					        <li><a href="#" class="page">3</a></li>
					        <li><a href="#" class="page">4</a></li>
					        <li><a href="#" class="page">5</a></li>
					        <li><a href="#" class="next"><img src="/images/common/chevron_right.png"></a></li>
					    </ul>
					</div>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>