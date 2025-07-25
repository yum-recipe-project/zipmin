<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/common/comment.css">
		<link rel="stylesheet" href="/css/mypage/comment.css">
		<script src="/js/mypage/comment.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="mycomment_wrap">
					<!-- 내 댓글 헤더 -->
					<div class="mycomment_header">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>내 댓글</h2>
					</div>
					
					<!-- 내 댓글 개수 -->
					<div class="mycomment_count">
						<span></span>
					</div>
					
					<!-- 내 댓글 -->
					<ul class="mycomment_list"></ul>
					
					<!-- 더보기 버튼 -->
					<div class="more_comment_btn">
						<button class="btn_more">
							<span>더보기</span>
							<img src="/images/mypage/arrow_down.png">
						</button>
					</div>
				</div>
			</div>
			
			<!-- 키친가이드의 댓글 수정 모달창 -->
			<%@include file="../modal/editCommentModal.jsp" %>
			
			
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>