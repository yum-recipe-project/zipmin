<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/chompessor/view-event.css">
		<script src="/js/chompessor/view-event.js"></script>
		<script src="/js/modal/edit-comment-modal.js"></script>
		<script src="/js/modal/write-subcomment-modal.js"></script>
		<script src="/js/modal/report-comment-modal.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<!-- 이벤트 -->
				<div class="event_wrap">
					<!-- 이벤트 헤더 -->
					<div class="event_header">
						<span>이벤트</span>
						<h2>3월 한정! 후원 포인트 이체 수수료 무료</h2>
						<div class="event_info">
							<div class="period">
								<span>2025.03.01 - 2024.03.31</span>
							</div>
							<span>댓글</span>
							<span>235개</span>
						</div>
					</div>
					
					<!-- 이벤트 내용 -->
					<p class="event_content">
						3월 기간 한정<br/>
						후원포인트 이체 수수료 무료 이벤트 🎉<br/>
						기간 한정으로 드리는 파격 혜택!<br/>
						지금 후원받은 포인트를 이체하시면<br/>
						이체 수수료가 무료!<br/>
					</p>
					<!-- 이벤트 이미지 -->
					<img src="/images/common/test.png">
					
					
					<!-- 목록 버튼 -->
					<div class="list_btn">
						<button class="btn_outline" onclick="location.href='/chompessor/listChomp.do'">목록으로</button>
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
							<button class="btn_sort_small">인기순</button>
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
					<c:if test="${ true }">
						<ul class="comment_list">
							<!-- 댓글을 돌면서 original idx랑 comment idx랑 일치하면 -->
							<%-- <c:foreach> --%>
								<c:if test="${ true }">
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
													<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportVoteCommentModal"
														onclick="openReportVoteCommentModal();">
														신고
													</a>
													<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editVoteCommentModal"
														onclick="openEditVoteCommentModal();">
														수정
													</a>
													<a href="">삭제</a>
												</div>
											</c:if>
										</div>
										<!-- 댓글 내용 -->
										<p class="comment_content">
											닉네임: 아잠만, 이벤트 참여하겠습니다
										</p>
										<!-- 대댓글 쓰기 버튼 -->
										<div class="comment_tool">
											<button class="btn_tool write_subcomment_btn">
												<img src="/images/recipe/thumb_up_full.png">
					                            <img src="/images/recipe/thumb_up_empty.png">
					                            <p>3</p>
											</button>
											<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeVoteSubcommentModal"
												onclick="openWriteVoteSubcommentModal();">
												<span>답글 쓰기</span>
											</a>
										</div>
									</li>
								</c:if>
								
								<!-- 대댓글 -->
								<ul class="subcomment_list">
									<!-- 댓글 테이블을 돌면서 orginal idx랑 comment idx랑 다르고 (=대댓글이고) && 이 댓글에 해당하는 대댓글이면 -->
									<%-- <c:forEach> --%>
										<c:if test="${ true }">
											<li class="subcomment">
												<img class="subcomment_arrow" src="/images/chompessor/arrow_right.png">
												<div class="subcomment_inner">
													<!-- 대댓글 헤더 -->
													<div class="subcomment_info">
														<div class="subcomment_writer">
															<img src="/images/common/test.png">
															<span>아잠만</span>
															<span>2025.02.11</span>
														</div>
														<c:if test="${ true }">
															<div class="subcomment_action">
																<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportVoteSubcommentModal"
																	onclick="openReportVoteSubcommentModal();">
																	신고
																</a>
																<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editVoteSubcommentModal"
																	onclick="openEditVoteSubcommentModal();">
																	수정
																</a>
																<a href="">삭제</a>
															</div>
														</c:if>
													</div>
													<!-- 대댓글 내용 -->
													<p class="subcomment_content">
														그냥 3월 동안 모두 해당되는 이벤트예요. 댓글 참여 아니라 댓글 안남기셔도 되는뎀..
													</p>
													<!-- 대댓글 좋아요 -->
													<div class="comment_tool">
														<button class="btn_tool write_subcomment_btn">
															<img src="/images/recipe/thumb_up_full.png">
								                            <img src="/images/recipe/thumb_up_empty.png">
								                            <p>3</p>
														</button>
													</div>
												</div>
											</li>
										</c:if>
									<%-- </c:forEach> --%>
								</ul>
							<%-- </c:foreach> --%>
						</ul>
					</c:if>
					
					
					
<!-- 					<ul class="test_comment_list"></ul> 댓글 목록 -->
<!-- 					<ul class="test_subcomment_list"></ul> 대댓글 목록 -->
					
					
					
					
					<!-- 댓글 더보기 버튼 -->
					<div class="more_comment_btn">
						<button class="btn_more">
							<span>더보기</span>
							<img src="/images/chompessor/arrow_down.png">
						</button>
					</div>
				</div>
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