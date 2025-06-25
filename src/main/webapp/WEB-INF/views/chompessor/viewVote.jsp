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
		<link rel="stylesheet" href="/css/common/review.css">
		<script src="/js/chompessor/view-vote.js"></script>
		<script src="/js/common/comment.js"></script>
		<script src="/js/common/review.js"></script>
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
						<h2>당신의 녹차 아이스크림에 투표하세요</h2>
						<div class="vote_info">
							<div class="period">
								<span>2025.01.03 - 2024.02.11</span>
							</div>
							<span>참여</span>
							<span>26</span>
							<span> ・ </span>
							<span>댓글</span>
							<span>235개</span>
						</div>
					</div>
					
					<!-- 투표 내용 -->
					<form name="" class="vote_form">
						<ul>
							<li>
								<div class="vote_checkbox_wrap">
									<input class="checkbox_group" type="checkbox" id="vote1">
									<label for="vote1">배스킨라빈스 녹차 아이스크림</label>
								</div>
							</li>
							<li>
								<div class="vote_checkbox_wrap">
									<input class="checkbox_group" type="checkbox" id="vote2">
									<label for="vote2">하겐다즈 녹차 아이스크림</label>
								</div>
							</li>
							<li>
								<div class="vote_checkbox_wrap">
									<input class="checkbox_group" type="checkbox" id="vote3">
									<label for="vote3">나뚜르 녹차 아이스크림</label>
								</div>
							</li>
						</ul>
						<div class="btn_wrap">
							<button class="btn_outline" onclick="location.href='/chompessor/listChomp.do';">목록으로</button>
							<button class="btn_primary" type="submit" onclick="">투표하기</button>
						</div>
					</form>
					
					<!-- 투표 결과 -->
					<div class="vote_result">
						<ul>
							<li>
								<div class="vote_option_wrap">
									<h5>베스킨라빈스 녹차 아이스크림</h5>
									<span>138명</span>
									<h3>66.3%</h3>
								</div>
							</li>
							<li>
								<div class="vote_option_wrap select">
									<h5><img src="/images/chompessor/check_blue.png">하겐다즈 녹차 아이스크림</h5>
									<span>138명</span>
									<h3>66.3%</h3>
								</div>
							</li>
							<li>
								<div class="vote_option_wrap">
									<h5>나뚜르 녹차 아이스크림</h5>
									<span>138명</span>
									<h3>66.3%</h3>
								</div>
							</li>
						</ul>
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