<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/view-guide.css">
		<link rel="stylesheet" href="/css/common/comment.css">
		<script src="/js/kitchen/view-guide.js"></script>
		<script src="/js/common/comment.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<!-- 가이드 -->
				<div class="guide_wrap">
					 <!-- 가이드 헤더 -->
	                <div class="guide_header">
	                    <span class="subtitle"></span>
	                    <h2 class="title"></h2>
	                    <div class="guide_writer"></div>
	                    <div class="btn_wrap"></div>
	                </div>
					
					<!-- 가이드 내용 -->
					<p class="guide_content"></p>
					
					<!-- 목록 버튼 -->
					<div class="list_btn">
						<button class="btn_outline" onclick="location.href='/kitchen/listGuide.do'">목록으로</button>
					</div>
				</div>
				
				<!-- 댓글 -->
				<%@include file="../common/comment.jsp" %>
			</div>
			
			<!-- 댓글 수정 모달창 -->
			<%@include file="../modal/editCommentModal.jsp" %>
			
			<!-- 댓글 신고 모달창 -->
			<%@include file="../modal/reportCommentModal.jsp" %>
			
			<!-- 투표의 대댓글 작성 모달창 -->
			<%@include file="../modal/writeSubcommentModal.jsp" %>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>