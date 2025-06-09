<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/list-guide.css">
		<link rel="stylesheet" href="/css/common/pagination.css">
		<script src="/js/kitchen/list-guide.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="guide_header">
					<h2>키친가이드</h2>
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
				
					<!-- 가이드 정렬 요소 -->
					<div class="guide_util">
						<p class="total">총 23개</p>
						<div class="guide_sort">
							<button class="btn_sort active">최신순</button>
							<button class="btn_sort">인기순</button>
						</div>
					</div>
					
					<!-- debug: 데이터 확인용 -->
					<div id="guideList"></div>
					
					<!-- 키친가이드 목록 -->
<!-- 					<ul class="guide_list"> -->
<!-- 						<li> -->
<!-- 							<a href="/kitchen/viewGuide.do"> -->
<!-- 								<div class="guide_item"> -->
	
<!-- 									<div class="guide_details"> -->
<!-- 										<div class="guide_top"> -->
<!-- 											<span>제주 바다향을 담은</span> -->
<!-- 											찜 버튼 -->
<!-- 											<button class="favorite_btn"></button> -->
<!-- 										</div> -->
										
<!-- 										<span>뿔 소라 손질법</span> -->
										
<!-- 										<div class="info"> -->
<!-- 											<p>스크랩 39</p> -->
<!-- 											<p>2025.02.08</p> -->
<!-- 										</div> -->
										
<!-- 										<div class="writer"> -->
<!-- 											프로필 이미지 -->
<%-- 											<c:if test="${ false }"> --%>
<!-- 												<img src="/images/common/hh.jpg"> -->
<%-- 											</c:if> --%>
<%-- 											<c:if test="${ true }"> --%>
<!-- 												<span class="profile_img"></span> -->
<%-- 											</c:if> --%>
											
<!-- 											<p>아잠만</p> -->
<!-- 										</div> -->
<!-- 									</div> -->
									
<!-- 								</div>	 -->
<!-- 							</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
						<!-- 키친가이드 목록 -->
						<ul class="guide_list"></ul>

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