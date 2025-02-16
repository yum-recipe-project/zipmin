<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/view-guide.css">
		<script src="/js/kitchen/view-guide.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<!-- 가이드 -->
				<div class="guide_wrap">
					<!-- 가이드 헤더 -->
					<div class="guide_header">
						<span>제주 바다향을 담은</span>
						<h2>뿔 소라 손질법</h2>
						<div class="guide_writer">
							<img src="/images/common/black.png">
							<span><b>아잠만</b></span>
							<span> ・ </span>
							<span>저장</span>
							<span>26</span>
							<span> ・ </span>
							<span>2025.02.10</span>
						</div>
						<div class="btn_wrap">
							<button onclick="">
								<img src="/images/kitchen/star.png"> 저장
							</button>
						</div>
					</div>
					
					<!-- 가이드 내용 -->
					<p class="guide_content">
						뿔소라는 양식이 불가능해<br/>
						오직 자연산으로만 채취 가능한데요.<br/>
						제주 해녀들이 직접 잡은 자연산 뿔소라!<br/>
						귀한 만큼 손질법을 제대로 알고 먹어야<br/>
						더 맛있게 즐길 수 있겠죠?<br/><br/>
						1. 포크나 젓가락으로 뿔소라 살을 콕 집어 돌려 뺀 후 껍질을 떼주세요.<br/>
						2. 몸통을 감싼 얇은 살과 내장을 제거해 주세요.<br/>
						3. 검은 부분을 눌러 이빨과 침샘을 제거해 주세요<br/><br/>
						그럼 끝입니다~
					</p>
					
					<!-- 목록 버튼 -->
					<div class="list_btn">
						<button onclick="location.href=''">목록으로</button>
					</div>
				</div>
				
				<!-- 댓글 -->
				<div id="guideCommentContent"></div>
				
				<!-- 댓글 더보기 버튼 -->
				<div class="more_comment_btn">
					<button>
						<span>더보기</span>
						<img src="/images/kitchen/arrow_down.png">
					</button>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>