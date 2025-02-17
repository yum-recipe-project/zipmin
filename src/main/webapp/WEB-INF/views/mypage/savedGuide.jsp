<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/saved-guide.css">
		<script src="/js/mypage/saved-guide.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="guide_wrap">
				
					<!-- 가이드 제목 -->
					<div class="guide_title">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>저장한 키친가이드</h2>
					</div>
					
					<!-- 키친가이드 개수 -->
					<div class="guide_count">
						<span>4개</span>
					</div>
					
					<!-- 키친가이드 목록 -->
					<ul class="guide_list">
						<li>
							<div class="guide_item">
								<!-- 저장한 가이드 정보 -->
								<div class="guide_details">
								
									<div class="guide_top">
										<span>제주 바다향을 담은</span>
										<!-- 찜 버튼 -->
										<button class="favorite_btn"></button>
									</div>
									
									
									<span>뿔 소라 손질법</span>
									
									
									<div class="info">
										<p>스크랩 39</p>
										<p>2025.02.08</p>
									</div>
									
									<div class="writer">
										<!-- 프로필 이미지 -->
										<c:if test="${ false }">
											<img src="/images/common/hh.jpg">
										</c:if>
										<c:if test="${ true }">
											<span class="profile_img"></span>
										</c:if>
										
										<p>아잠만</p>
									</div>
								</div>
								
							</div>	
						</li>
						
						<li>
							<div class="guide_item">
								<!-- 저장한 가이드 정보 -->
								<div class="guide_details">
								
									<div class="guide_top">
										<span>제주 바다향을 담은</span>
										<!-- 찜 버튼 -->
										<button class="favorite_btn"></button>
									</div>
									
									
									<span>뿔 소라 손질법</span>
									
									
									<div class="info">
										<p>스크랩 39</p>
										<p>2025.02.08</p>
									</div>
									
									<div class="writer">
										<!-- 프로필 이미지 -->
										<c:if test="${ false }">
											<img src="/images/common/hh.jpg">
										</c:if>
										<c:if test="${ true }">
											<span class="profile_img"></span>
										</c:if>
										
										<p>아잠만</p>
									</div>
								</div>
								
							</div>	
						</li>
						
						
						
					</ul>
				
				
					<!-- 더보기 버튼 -->
					<div class="more_btn">
						<button>
							<span>더보기</span>
							<img src="/images/mypage/arrow_down.png">
						</button>
					</div>
				
				
				</div>			
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>