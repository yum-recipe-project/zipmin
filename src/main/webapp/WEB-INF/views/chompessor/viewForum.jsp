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
														<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportForumCommentModal"
															onclick="openReportForumCommentModal();">
															신고
														</a>
														<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editForumCommentModal"
															onclick="openEditForumCommentModal();">
															수정
														</a>
														<a href="">삭제</a>
													</div>
												</c:if>
											</div>
											<!-- 댓글 내용 -->
											<p class="comment_content">
												녹차 아이스크림은 배스킨라빈스가 최고입니다
											</p>
											<!-- 대댓글 쓰기 버튼 -->
											<div class="comment_tool">
												<button class="btn_tool write_subcomment_btn">
													<img src="/images/recipe/thumb_up_full.png">
						                            <img src="/images/recipe/thumb_up_empty.png">
						                            <p>3</p>
												</button>
												<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeForumSubcommentModal"
													onclick="openWriteForumSubcommentModal();">
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
																	<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportForumSubcommentModal"
																		onclick="openReportWriteForumSubcommentModal();">
																		신고
																	</a>
																	<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editForumSubcommentModal"
																		onclick="openWriteForumSubcommentModal();">
																		수정
																	</a>
																	<a href="">삭제</a>
																</div>
															</c:if>
														</div>
														<!-- 대댓글 내용 -->
														<p class="subcomment_content">
															나뚜르가 최곤데 뭘 모르시네요
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
						
						<!-- 댓글 더보기 버튼 -->
						<div class="more_comment_btn">
							<button class="btn_more">
								<span>더보기</span>
								<img src="/images/chompessor/arrow_down.png">
							</button>
						</div>
					</div>
				</div>
				
				<!--  포럼의 댓글 수정 모달창 -->
				<form method="post" action="" onsubmit="">
					<div class="modal" id="editForumCommentModal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5>수정하기</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label>내용</label>
										<textarea class="form-control" id="editForumCommentContentInput" name="content" style="height: 90px;"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
									<button type="submit" id="editForumCommentButton" class="btn btn-disable" disabled>작성하기</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				
				<!-- 포럼의 대댓글 작성 모달창 -->
				<form method="post" action="" onsubmit="">
					<div class="modal" id="writeForumSubcommentModal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5>작성하기</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label>내용</label>
										<textarea class="form-control" id="writeForumSubcommentContentInput" name="content" style="height: 90px;"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
									<button type="submit" id="writeForumSubcommentButton" class="btn btn-disable" disabled>작성하기</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				
				<!-- 포럼의 대댓글 수정 모달창 -->
				<form method="post" action="" onsubmit="">
					<div class="modal" id="editForumSubcommentModal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5>수정하기</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label>내용</label>
										<textarea class="form-control" id="editForumSubcommentContentInput" name="content" style="height: 90px;"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
									<button type="submit" id="editForumSubcommentButton" class="btn btn-disable" disabled>작성하기</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				
				
				<!-- 댓글 신고 모달창 -->
				<form id="reportForumCommentForm" onsubmit="return validateReportForumCommentForm();">
					<div class="modal" id="reportForumCommentModal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5>신고하기</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
								</div>
								<div class="modal-body">
									<label>댓글 신고 사유</label>
									<div class="report">
										<div class="reason">
											<div class="form-radio">
												<div>
													<input type="radio" id="reason1" name="reason" value="">
													<label for="reason1">정당/정치인 비하 및 선거운동</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason2" name="reason" value="">
													<label for="reason2">유출/사칭/사기</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason3" name="reason" value="">
													<label for="reason3">욕설/비하</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason4" name="reason" value="">
													<label for="reason4">낚시/놀람/도배</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason5" name="reason" value="">
													<label for="reason5">상업적 광고 및 판매</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason6" name="reason" value="">
													<label for="reason6">불법촬영물 등의 유통</label>
												</div>
											</div>
										</div>
									</div>
									<div class="form-info">
										<p>
											신고는 반대의견을 나타내는 기능이 아닙니다.
											신고 사유에 맞지 않는 신고를 했을 경우, 해당 신고는 처리되지 않습니다.
										</p>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
									<button type="submit" class="btn btn-primary">신고하기</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				
				
				<!-- 대댓글 신고 모달창 -->
				<form id="reportForumSubcommentForm" onsubmit="return validateReportForumSubcommentForm();">
					<div class="modal" id="reportForumSubcommentModal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5>신고하기</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
								</div>
								<div class="modal-body">
									<label>대댓글 신고 사유</label>
									<div class="report">
										<div class="reason">
											<div class="form-radio">
												<div>
													<input type="radio" id="reason1" name="reason" value="">
													<label for="reason1">정당/정치인 비하 및 선거운동</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason2" name="reason" value="">
													<label for="reason2">유출/사칭/사기</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason3" name="reason" value="">
													<label for="reason3">욕설/비하</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason4" name="reason" value="">
													<label for="reason4">낚시/놀람/도배</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason5" name="reason" value="">
													<label for="reason5">상업적 광고 및 판매</label>
												</div>
											</div>
											<div class="form-radio">
												<div>
													<input type="radio" id="reason6" name="reason" value="">
													<label for="reason6">불법촬영물 등의 유통</label>
												</div>
											</div>
										</div>
									</div>
									<div class="form-info">
										<p>
											신고는 반대의견을 나타내는 기능이 아닙니다.
											신고 사유에 맞지 않는 신고를 했을 경우, 해당 신고는 처리되지 않습니다.
										</p>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
									<button type="submit" class="btn btn-primary">신고하기</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>