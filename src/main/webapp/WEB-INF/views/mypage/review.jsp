<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/review.css">
		<script src="/js/mypage/review.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="myreview_wrap">
					<!-- 내 리뷰 헤더 -->
					<div class="myreview_header">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>내 요리 후기</h2>
					</div>
					
					<!-- 내 리뷰 개수 -->
					<div class="myreview_count">
						<span>4개</span>
					</div>
					
					<!-- 내 리뷰 목록 -->
					<div id="myReviewContent"></div>
					
					<!-- 더보기 버튼 -->
					<div class="more_review_btn">
						<button class="btn_more">
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