<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/view-forum.css">
		<script src="/js/chompessor/view-forum.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
			<main id="container">
				<div class="content">
					<!-- 포럼 -->
					<div class="forum_wrap">
						<!-- 포럼 헤더 -->
						<div class="forum_header">
							<span>아이스크림</span>
							<h2>당신의 녹차 아이스크림에 투표하세요</h2>
							<div class="forum_info">
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
						
						<!-- 포럼 내용 -->
						<div class="forum_content">
							<ul>
								<li>
									<div class="forum_checkbox_wrap">
										<input class="checkbox_group" type="checkbox" id="vote1">
										<label for="vote1">배스킨라빈스 녹차 아이스크림</label>
									</div>
								</li>
								<li>
									<div class="forum_checkbox_wrap">
										<input class="checkbox_group" type="checkbox" id="vote2">
										<label for="vote2">하겐다즈 녹차 아이스크림</label>
									</div>
								</li>
								<li>
									<div class="forum_checkbox_wrap">
										<input class="checkbox_group" type="checkbox" id="vote3">
										<label for="vote3">나뚜르 녹차 아이스크림</label>
									</div>
								</li>
							</ul>
							<div class="btn_wrap">
								<button onclick="location.href='/';">목록으로</button>
								<button type="submit" onclick="">투표하기</button>
							</div>
						</div>
					</div>
					
					<!-- 댓글 -->
					<div id="forumCommentContent"></div>

				</div>
				
			</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>