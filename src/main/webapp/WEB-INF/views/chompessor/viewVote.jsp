<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/view-vote.css">
		<link rel="stylesheet" href="/css/common/comment.css">
		<script src="/js/chompessor/view-vote.js"></script>
		<script src="/js/common/comment.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<!-- 투표 -->
				<div class="vote_wrap">
					<!-- 투표 헤더 -->
					<div class="vote_header">
						<span>아이스크림</span>
						<h2 class="vote_title"></h2>
						<div class="vote_info">
							<div class="period">
								<span class="vote_postdate"></span>
							</div>
							<span>참여</span>
							<span class="vote_total"></span>
						</div>
					</div>
					
					<!-- 투표 내용 -->
					<form name="" class="vote_form">
						<ul class="choice_list"></ul>
						<div class="btn_wrap">
							<button class="btn_outline" onclick="location.href='/chompessor/listChomp.do';">목록으로</button>
							<button class="btn_primary" type="submit" onclick="">투표하기</button>
						</div>
					</form>
					
					<!-- 투표 결과 -->
					<div class="vote_result">
						<ul class="record_list"></ul>
						<div class="btn_wrap">
							<button class="btn_outline" onclick="location.href='/chompessor/listChomp.do';">목록으로</button>
							<button class="btn_primary" type="submit" onclick="">다시 투표하기</button>
						</div>
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