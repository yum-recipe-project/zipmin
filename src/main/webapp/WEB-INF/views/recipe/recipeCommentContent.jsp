<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/recipe/recipe-comment-content.css">
	</head>
	<body>
		<input type="hidden" id="comment_count" value="">
		
		<div class="comment_wrap">
			<!-- 댓글 헤더 -->
			<div class="comment_header">
				<div class="comment_count">
					<span>댓글</span>
					<span>7</span>
				</div>
				<div class="comment_order">
					<button class="active">최신순</button>
					<button>오래된순</button>
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
							<img src="/images/common/black.png">
							<span>아잠만</span>
						</div>
						<div class="comment_input">
							<textarea id="commentInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
							<span>400</span>
						</div>
						<div class="write_btn">
							<button type="submit" id="commentButton" class="disable" onclick="" disabled>작성하기</button>
						</div>
					</form>
				</c:if>
			</div>
			
			<!-- 댓글 목록 -->
			<c:if test="${ true }">
				<ul class="comment_list">
					<!-- 댓글을 돌면서 original idx랑 comment idx랑 일치하면 -->
					<%-- <c:foreach> --%>
						<c:if test="${ true }">
							<li>
								<!-- 댓글 헤더 -->
								<div class="comment_header">
									<div class="comment_writer">
										<img src="/images/common/black.png">
										<span>아잠만</span>
										<span>2025.02.11</span>
									</div>
									<c:if test="${ true }">
										<div class="comment_action">
											<a href="">수정</a>
											<a href="">삭제</a>
										</div>
									</c:if>
								</div>
								<!-- 댓글 내용 -->
								<p class="comment_content">
									녹차 아이스크림은 배스킨라빈스가 최고입니다
								</p>
								<!-- 대댓글 쓰기 버튼 -->
								<a class="write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#subcommentWriteModal"
									onclick="openSubcommentWriteModal();">
									<span>답글 쓰기</span>
								</a>
							</li>
						</c:if>
						
						<!-- 대댓글 -->
						<ul class="subcomment_list">
							<!-- 댓글 테이블을 돌면서 orginal idx랑 comment idx랑 다르고 (=대댓글이고) && 이 댓글에 해당하는 대댓글이면 -->
							<%-- <c:forEach> --%>
								<c:if test="${ true }">
									<li>
										<img src="/images/chompessor/arrow_right.png">
										<div class="subcomment">
											<!-- 대댓글 헤더 -->
											<div class="subcomment_header">
												<div class="subcomment_writer">
													<img src="/images/common/black.png">
													<span>아잠만</span>
													<span>2025.02.11</span>
												</div>
												<c:if test="${ true }">
													<div class="subcomment_action">
														<a href="">수정</a>
														<a href="">삭제</a>
													</div>
												</c:if>
											</div>
											<!-- 대댓글 내용 -->
											<p class="subcomment_content">
												나뚜르가 최곤데 뭘 모르시네요
											</p>
										</div>
									</li>
								</c:if>
							<%-- </c:forEach> --%>
						</ul>
					<%-- </c:foreach> --%>
				</ul>
			</c:if>
			
			<div class="more_comment_btn">
				<button>
					<span>더보기</span>
					<img src="/images/recipe/arrow_down_black.png">
				</button>
			</div>
		</div>
	</body>
</html>