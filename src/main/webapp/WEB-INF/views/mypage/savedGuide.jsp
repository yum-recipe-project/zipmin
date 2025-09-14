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
		            <div class="guide_title">
		                <a href="/mypage.do">
		                    <span>마이페이지<img src="/images/mypage/arrow_right.png"></span>
		                </a>
		                <h2>저장한 키친가이드</h2>
		            </div>
		
		            <!-- 키친가이드 개수 -->
		            <div class="guide_count">
		                <span id="guideCount">0개</span>
		            </div>
		
		            <!-- 키친가이드 목록 (JS에서 동적 생성) -->
		            <ul class="guide_list" id="savedGuideList"></ul>
		
		            <!-- 더보기 버튼 -->
					<div class="more_comment_btn">
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