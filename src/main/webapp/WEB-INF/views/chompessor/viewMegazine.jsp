<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/view-megazine.css">
		<link rel="stylesheet" href="/css/common/comment.css">
		<link rel="stylesheet" href="/css/common/review.css">
		<script src="/js/chompessor/view-megazine.js"></script>
		<script src="/js/common/comment.js"></script>
		<script src="/js/common/review.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div class="megazine_wrap">
					<!-- 매거진 헤더 -->
					<div class="megazine_header">
						<span class="megazine_category">매거진</span>
						<h2 class="megazine_title"></h2>
						<div class="megazine_writer">
							<img src="/images/common/test.png">
							<span><b>집밥의민족</b></span>
							<span> ・ </span>
							<span class="megazine_postdate"></span>
						</div>
					</div>
					
					<!-- 매거진 내용 -->
					<p class="megazine_content"></p>
					
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