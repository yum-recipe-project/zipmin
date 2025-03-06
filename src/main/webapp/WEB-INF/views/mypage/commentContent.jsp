<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/mypage/comment-content.css">
	</head>
	<body>
		<input type="hidden" id="commentCount" value="">
		
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
		
	</body>
</html>