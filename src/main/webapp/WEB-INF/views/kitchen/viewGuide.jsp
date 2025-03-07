<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/view-guide.css">
		<script src="/js/kitchen/view-guide.js"></script>
		<script src="/js/modal/edit-guide-comment-modal.js"></script>
		<script src="/js/modal/report-guide-comment-modal.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<!-- 가이드 -->
				<div class="guide_wrap">
					<!-- 가이드 헤더 -->
					<div class="guide_header">
						<span>제주 바다향을 담은</span>
						<h2>뿔 소라 손질법</h2>
						<div class="guide_writer">
							<img src="/images/common/test.png">
							<span><b>관리자</b></span>
							<span> ・ </span>
							<span>저장</span>
							<span>26</span>
							<span> ・ </span>
							<span>2025.02.10</span>
						</div>
						<div class="btn_wrap">
							<button class="btn_tool" onclick="">
								<img src="/images/kitchen/star.png"> 저장
							</button>
						</div>
					</div>
					
					<!-- 가이드 내용 -->
					<p class="guide_content">
						뿔소라는 양식이 불가능해<br/>
						오직 자연산으로만 채취 가능한데요.<br/>
						제주 해녀들이 직접 잡은 자연산 뿔소라!<br/>
						귀한 만큼 손질법을 제대로 알고 먹어야<br/>
						더 맛있게 즐길 수 있겠죠?<br/><br/>
						1. 포크나 젓가락으로 뿔소라 살을 콕 집어 돌려 뺀 후 껍질을 떼주세요.<br/>
						2. 몸통을 감싼 얇은 살과 내장을 제거해 주세요.<br/>
						3. 검은 부분을 눌러 이빨과 침샘을 제거해 주세요<br/><br/>
						그럼 끝입니다~
					</p>
					
					<!-- 목록 버튼 -->
					<div class="list_btn">
						<button class="btn_outline" onclick="location.href='/kitchen/listGuide.do'">목록으로</button>
					</div>
				</div>
				
				<!-- 댓글 -->
				<div class="comment_wrap">
					<!-- 댓글 헤더 -->
					<div class="comment_header">
						<div class="comment_count">
							<span>키친 한마디</span>
							<span>4</span>
						</div>
						<div class="comment_order">
							<button class="btn_sort_small active">최신순</button>
							<button class="btn_sort_small">오래된순</button>
							<button class="btn_sort_small">인기순</button>
						</div>
					</div>
					
					<!-- 댓글 작성 -->
					<div class="comment_write">
						<!-- 로그인 하지 않은 경우 -->
						<c:if test="${ true }">
							<a href="/member/login.do">
								<span>키친 한마디 작성을 위해 로그인을 해주세요.</span>
								<span>400</span>
							</a>
						</c:if>
						<!-- 로그인 한 경우 -->
						<c:if test="${ false }">
							<form>
								<div class="login_user">
									<img src="/images/common/test.png">
									<span>아잠만</span>
								</div>
								<div class="comment_input">
									<textarea id="commentInput" rows="2" maxlength="400" placeholder="나누고 싶은 좋은 방법이 있다면 키친 노하우를 남겨주세요!&#10;주제와 무관한 키친 한마디는 삭제될 수 있습니다."></textarea>
									<span>400</span>
								</div>
								<div class="write_btn">
									<button class="btn_primary disable" type="submit" id="commentButton" onclick="" disabled>작성하기</button>
								</div>
							</form>
						</c:if>
					</div>
					<!-- 댓글 목록 -->
					<c:if test="${ true }">
						<ul class="comment_list">
							<!-- 댓글을 돌면서 -->
							<%-- <c:forEach> --%>
								<li class="comment">
									<!-- 댓글 헤더 -->
									<div class="comment_info">
										<div class="comment_writer">
											<img src="/images/common/test.png">
											<span>아잠만</span>
											<span>2025.02.11</span>
										</div>
										<c:if test="${ true }">
											<div class="comment_action">
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportGuideCommentModal"
													onclick="openReportGuideCommentModal();">
													신고
												</a>
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editGuideCommentModal"
													onclick="openEditGuideCommentModal();">
													수정
												</a>
												<a href="">삭제</a>
											</div>
										</c:if>
									</div>
									<!-- 댓글 내용 -->
									<p class="comment_content">
										검은 부분을 누르면 이빨과 침샘을 더 쉽게 제거할 수 있어요
									</p>
									<!-- 댓글 도구 -->
									<div class="comment_tool">
										<button class="btn_tool write_subcomment_btn">
											<img src="/images/recipe/thumb_up_full.png">
				                            <img src="/images/recipe/thumb_up_empty.png">
				                            <p>3</p>
										</button>
									</div>
								</li>
							<%-- </c:forEach> --%>
						</ul>
					</c:if>
					
					<!-- 댓글 더보기 버튼 -->
					<div class="more_comment_btn">
						<button class="btn_more">
							<span>더보기</span>
							<img src="/images/kitchen/arrow_down.png">
						</button>
					</div>
				</div>
			</div>
			
			<!-- 키친가이드의 댓글 수정 모달창 -->
			<%@include file="../modal/editGuideCommentModal.jsp" %>
			
			<!-- 키친가이드의 댓글 신고 모달창 -->
			<%@include file="../modal/reportGuideCommentModal.jsp" %>
			
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>