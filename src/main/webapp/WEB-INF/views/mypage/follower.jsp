<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/common/button.css">
		<link rel="stylesheet" href="/css/mypage/follower.css">
		<script src="/js/mypage/follower.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="follower_wrap">
					<!-- 팔로워 제목 -->
					<div class="follower_title">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>팔로워</h2>
					</div>
					
					<!-- 팔로워 수 -->
					<div class="follower_count">
						<span>7명</span>
					</div>
					
					<!-- 팔로워 목록 -->
					<ul class="follower_list">
<%-- 						<c:forEach> --%>
							<li>
								<div class="follower_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ false }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ true }">
										<span class="follower_img"></span>
									</c:if>
									
									<!-- 팔로워 정보 -->
									<div class="follower_info">
										<span>아잠만</span>
										<p>저는 세계 최강 요리사입니다. 저로말할 것 같으면 강릉함씨 32대손 함필규</p>
									</div>
									
									<button class="btn_outline_small followButton">
										<span>팔로잉</span>
									</button>
								</div>
							</li>
<%-- 						</c:forEach> --%>
							<!-- 디자인 확인용, 개발 후 삭제 -->
							<li>
								<div class="follower_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ true }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ false }">
										<span class="follower_img"></span>
									</c:if>
									
									<!-- 팔로워 정보 -->
									<div class="follower_info">
										<span>아잠만</span>
										<p>저는 세계 최강 요리사입니다람쥐</p>
									</div>
									
									<button class="btn_outline_small followButton">
										<span>팔로잉</span>
									</button>
								</div>
							</li>
							<li>
								<div class="follower_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ false }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ true }">
										<span class="follower_img"></span>
									</c:if>
									
									<!-- 팔로워 정보 -->
									<div class="follower_info">
										<span>아잠만</span>
										<p>저는 세계 최강 요리사입니다람쥐</p>
									</div>
									
									<button class="btn_outline_small followButton">
										<span>팔로잉</span>
									</button>
								</div>
							</li>
					</ul>
				
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>