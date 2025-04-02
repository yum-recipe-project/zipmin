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
						<a class="btn_tab active" data-category="all" href="">
							<span>전체</span>
						</a>
						<a class="btn_tab" data-category="vote" href="">
							<span>투표</span>
						</a>
						<a class="btn_tab" data-category="megazine" href="">
							<span>매거진</span>
						</a>
						<a class="btn_tab" data-category="event" href="">
							<span>이벤트</span>
						</a>
					</div>
				</div>
			
				<div class="forum_content">
				
					<!-- 쩝쩝박사 정렬 요소 -->
					<div class="forum_util">
						<p>투표에 참여해보세요!</p>
						<div class="forum_sort">
							<button class="btn_sort active">진행중</button>
							<button class="btn_sort">진행예정</button>
							<button class="btn_sort">마감</button>
						</div>
					</div>
					
					<!-- 목록 -->
					<c:if test="${ true }">
						<ul class="forum_list" id="chomp">
							<%-- <c:foreach> --%>
								<li class="forum">
									<a href="/chompessor/viewVote.do">
										<div class="forum_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="forum_info">
											<p class="type">투표</p>
											<h5 class="title" data-id="1">당신의 녹차 아이스크림에 투표하세요</h5>
											<div class="info">
												<p class="ing_flag">모집중</p>
												<p class="date">2025.03.04 - 2025.04.03</p>
											</div>
										</div>
									</a>
								</li>
							<%-- </c:foreach> --%>
						</ul>
					</c:if>
				
				</div>
			
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