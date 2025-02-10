<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/guide-view.css">
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="guide_wrap">
					<!-- 제목 -->
					<div class="title">
						<span>제주 바다향을 담은</span>
						<h2>뿔 소라 손질법</h2>
						<div class="guide_writer">
							<div class="image">
								<!-- <img src=""> -->
							</div>
							<span>아잠만</span>
							<span> ・ </span>
							<span>저장</span>
							<span>26</span>
							<span> ・ </span>
							<span>2025.02.10</span>
						</div>
						
						<!-- 저장 버튼 -->
						<div class="btn_wrap">
							<button onclick="">
								<img src="/images/kitchen/star.png"> 저장
							</button>
						</div>
					</div>
					
					<!-- 내용 -->
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
						<button class="list_btn" onclick="location.href=''">목록으로</button>
					</div>
				</div>
				
				<!--  댓글 -->
				<div class="comment_wrap">
					
					<!-- 댓글 수와 정렬 순서 -->
					<div class="comment_header">
						<div class="comment_count">
							<span>댓글</span>
							<span>4</span>
						</div>
						<div class="comment_order">
							<button class="active">최신순</button>
							<button>오래된순</button>
						</div>
					</div>
					
					<!-- 댓글 작성 -->
					
					
					<!-- 댓글 -->
					
					
					
					
					
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>