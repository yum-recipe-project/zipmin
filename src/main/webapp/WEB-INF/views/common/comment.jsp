<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="comment_wrap">
	<!-- 댓글 헤더 -->
	<div class="comment_header">
		<div class="comment_count">
			<span>댓글</span>
			<span>7</span>
		</div>
		<div class="comment_order">
			<button class="btn_sort_small active" data-sort="new">최신순</button>
			<button class="btn_sort_small" data-sort="old">오래된순</button>
			<button class="btn_sort_small" data-sort="hot">인기순</button>
		</div>
	</div>
	
	<!-- 댓글 작성 -->
	<form id="writeCommentForm" class="comment_write">
		<!-- 로그인 하지 않은 경우 -->
		<c:if test="${ false }">
			<a href="/member/login.do">
				<span>댓글 작성을 위해 로그인을 해주세요.</span>
				<span>400</span>
			</a>
		</c:if>
		<!-- 로그인 한 경우 -->
		<c:if test="${ true }">
			<div class="login_user">
				<img src="/images/common/test.png">
				<span>아잠만</span>
			</div>
			<div class="comment_input">
				<textarea id="writeCommentContent" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
				<span>400</span>
			</div>
			<div class="write_btn">
				<button class="btn_primary disable" type="submit" class="disable" disabled>작성하기</button>
			</div>
		</c:if>
	</form>
	
	<!-- 댓글 목록 -->
	<c:if test="${ true }">
		<ul class="comment_list">
			<!-- 댓글을 돌면서 original idx랑 comment idx랑 일치하면 -->
			<%-- <c:foreach> --%>
<%-- 			
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
									<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
										신고
									</a>
									<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">
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
								<img src="/images/common/thumb_up_full.png">
	                            <img src="/images/common/thumb_up_empty.png">
	                            <p>3</p>
							</button>
							<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeSubcommentModal">
								<span>답글 쓰기</span>
							</a>
						</div>
					</li>
				</c:if>
				
				<!-- 대댓글 -->
				<ul class="subcomment_list">
					<!-- 댓글 테이블을 돌면서 orginal idx랑 comment idx랑 다르고 (=대댓글이고) && 이 댓글에 해당하는 대댓글이면 -->
					<c:forEach>
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
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
													신고
												</a>
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">
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
											<img src="/images/common/thumb_up_full.png">
				                            <img src="/images/common/thumb_up_empty.png">
				                            <p>3</p>
										</button>
									</div>
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ul> --%>
			<%-- </c:foreach> --%>
		</ul>
	</c:if>
	
	<!-- 댓글 더보기 버튼 -->
	<div class="more_comment_btn">
		<button class="btn_more">
			<span>더보기</span>
			<img src="/images/common/arrow_down.png">
		</button>
	</div>
</div>