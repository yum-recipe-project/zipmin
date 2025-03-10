<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
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
						<span>4개</span>
					</div>
					
					<!-- 내 댓글 -->
					<c:if test="${ true }">
						<ul class="mycomment_list">
							<!-- 내가 작성한 댓글을 돌면서 -->
							<%-- <c:forEach> --%>
								<li>
									<div class="mycomment_title">
										<c:if test="${ true }">
											<a href="/chompessor/viewForum.do">당신의 녹차 아이스크림에 투표하세요</a>
										</c:if>
										<p class="board">음식 월드컵</p>
									</div>
									<div class="mycomment">
										<div class="comment_info">
											<div class="comment_writer">
												<img src="/images/common/test.png">
												<span>아잠만</span>
												<span>2025.02.11</span>
											</div>
											<div class="comment_action">
												<!-- 어떤 게시판에 달린 댓글인지에 따라 다른 모달창 오픈 -->
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal"
													onclick="openCommentModal();">
													수정
												</a>
												<a href="">삭제</a>
											</div>
										</div>
										<!-- 댓글 내용 -->
										<p class="comment_content">
											검은 부분을 누르면 이빨과 침샘을 더 쉽게 제거할 수 있어요
										</p>
									</div>
								</li>
							<%-- </c:forEach> --%>
						</ul>
					</c:if>
					
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
			<%@include file="../modal/editGuideCommentModal.jsp" %>
			
			<!-- 투표의 댓글 수정 모달창 -->
			<%@include file="../modal/editVoteCommentModal.jsp" %>
			
			<!-- 매거진의 댓글 수정 모달창 -->
			<%@include file="../modal/editMegazineCommentModal.jsp" %>
			
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>