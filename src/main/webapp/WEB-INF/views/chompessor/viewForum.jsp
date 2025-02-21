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
								<button class="btn_outline" onclick="location.href='/chompessor/listForum.do';">목록으로</button>
								<button class="btn_primary" type="submit" onclick="">투표하기</button>
							</div>
						</div>
					</div>
					
					<!-- 댓글 -->
					<div class="comment_wrap">
						<!-- 댓글 헤더 -->
						<div class="comment_header">
							<div class="comment_count">
								<span>댓글</span>
								<span>7</span>
							</div>
							<div class="comment_order">
								<button class="btn_sort_small active">최신순</button>
								<button class="btn_sort_small">오래된순</button>
							</div>
						</div>
						
						<!-- 댓글 작성 -->
						<div class="comment_write">
							<!-- 로그인 하지 않은 경우 -->
							<c:if test="${ false }">
								<a href="/member/login.do">
									<span>댓글 작성을 위해 로그인을 해주세요.</span>
									<span>400</span>
								</a>
							</c:if>
							<!-- 로그인 한 경우 -->
							<c:if test="${ true }">
								<form>
									<div class="login_user">
										<img src="/images/common/test.png">
										<span>아잠만</span>
									</div>
									<div class="comment_input">
										<textarea id="commentInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
										<span>400</span>
									</div>
									<div class="write_btn">
										<button class="btn_primary disable" type="submit" id="commentButton" class="disable" onclick="" disabled>작성하기</button>
									</div>
								</form>
							</c:if>
						</div>
						<!-- 댓글 목록 -->
						<div id="forumCommentContent"></div>
					</div>
					
					<!-- 댓글 더보기 버튼 -->
					<div class="more_comment_btn">
						<button class="btn_more">
							<span>더보기</span>
							<img src="/images/chompessor/arrow_down.png">
						</button>
					</div>

				</div>
			</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>