<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/following.css">
		<script src="/js/mypage/following.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="following_wrap">
					<!-- 팔로잉 제목 -->
					<div class="following_title">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>팔로잉</h2>
					</div>
					
					<!-- 팔로잉 수 -->
					<div class="following_count">
						<span>3명</span>
					</div>
					
					<!-- 팔로잉 목록 -->
					<ul class="following_list">
<%-- 						<c:forEach> --%>
							<li>
								<div class="following_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ false }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ true }">
										<span class="following_img"></span>
									</c:if>
									
									<!-- 팔로잉 정보 -->
									<div class="following_info">
										<span>아잠만</span>
										<p>저는 세계 최강 요리사입니다. 저로말할 것 같으면 강릉함씨 32대손 함필규</p>
									</div>
									
									<button class="btn_primary followButton">
										<span>팔로우</span>
									</button>
								</div>
							</li>
<%-- 						</c:forEach> --%>
							<!-- 디자인 확인용, 개발 후 삭제 -->
							<li>
								<div class="following_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ true }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ false }">
										<span class="following_img"></span>
									</c:if>
									
									<!-- 팔로잉 정보 -->
									<div class="following_info">
										<span>아잠만</span>
										<p>저는 세계 최강 요리사입니다람쥐</p>
									</div>
									
									<button class="btn_primary followButton">
										<span>팔로우</span>
									</button>
								</div>
							</li>
							<li>
								<div class="following_box">
									<!-- 프로필 이미지 -->
									<c:if test="${ false }">
										<img src="/images/common/hh.jpg">
									</c:if>
									<c:if test="${ true }">
										<span class="following_img"></span>
									</c:if>
									
									<!-- 팔로잉 정보 -->
									<div class="following_info">
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