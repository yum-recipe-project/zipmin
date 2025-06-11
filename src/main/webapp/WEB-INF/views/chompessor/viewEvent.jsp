<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/view-event.css">
		<script src="/js/chompessor/view-event.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<!-- 이벤트 -->
				<div class="event_wrap">
					<!-- 이벤트 헤더 -->
					<div class="event_header">
						<span>이벤트</span>
						<h2>3월 한정! 후원 포인트 이체 수수료 무료</h2>
						<div class="event_info">
							<div class="period">
								<span>2025.03.01 - 2024.03.31</span>
							</div>
							<span>댓글</span>
							<span>235개</span>
						</div>
					</div>
					
					<!-- 이벤트 내용 -->
					<p class="event_content">
						3월 기간 한정<br/>
						후원포인트 이체 수수료 무료 이벤트<br/>
						기간 한정으로 드리는 파격 혜택!<br/>
						지금 후원받은 포인트를 이체하시면<br/>
						이체 수수료가 무료!<br/>
					</p>
					<!-- 이벤트 이미지 -->
					<img src="/images/common/test.png">
					
					
					<!-- 목록 버튼 -->
					<div class="list_btn">
						<button class="btn_outline" onclick="location.href='/chompessor/listChomp.do'">목록으로</button>
					</div>
					
				</div>
				
				<!-- 댓글 -->
				<%@include file="../common/comment.jsp" %>
			</div>
			
			<!-- 댓글 수정 모달창 -->
			<%@include file="../modal/editCommentModal.jsp" %>
			
			<!-- 댓글 신고 모달창 -->
			<%@include file="../modal/reportCommentModal.jsp" %>
			
			<!-- 대댓글 작성 모달창 -->
			<%@include file="../modal/writeSubcommentModal.jsp" %>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>