<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/chompessor/forum-comment-content.css">
	</head>
	<body>
		<input type="hidden" id="commentCount" value="">
		
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
			<script>
				/**
				 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
				 */
				const tabItems = document.querySelectorAll('.comment_order button');
				const contentItems = document.querySelectorAll('.comment_list');
			    // 탭 클릭 이벤트 설정
			    tabItems.forEach((item, index) => {
			        item.addEventListener("click", function(event) {
			            event.preventDefault();
			            
			            tabItems.forEach(button => button.classList.remove('active'));
			            this.classList.add('active');
			            
			            contentItems.forEach(content => content.style.display = 'none');
			            contentItems[index].style.display = 'block';
			        });
			    });
			    // 기본으로 첫번째 활성화
			    tabItems.forEach(button => button.classList.remove('active'));
			    contentItems.forEach(content => content.style.display = 'none');
			    tabItems[0].classList.add('active');
			    contentItems[0].style.display = 'block';
			</script>
			
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
			<script>
				/**
				 * 댓글 작성 폼의 focus 여부에 따라 입력창을 활성화하는 함수
				 */
				const commentInput = document.getElementById("commentInput");
				const commentInputBorder = document.querySelector('.comment_input');
				commentInput.addEventListener('focus', function() {
					commentInputBorder.classList.add('focus');
				});
				commentInput.addEventListener('blur', function() {
					commentInputBorder.classList.remove('focus');
				});
				
				/**
				 * 댓글 작성 폼값을 검증하는 함수 (댓글 내용 미작성 시 버튼 비활성화)
				 */
				const commentButton = document.getElementById("commentButton")
				commentInput.addEventListener("input", function() {
					const isCommentEmpty = commentInput.value.trim() === "";
					commentButton.classList.toggle("disable", isCommentEmpty);
					commentButton.disabled = isCommentEmpty;
				});
			</script>
			
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
								<a class="write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeForumSubcommentModal"
									onclick="openWriteForumSubcommentModal();">
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
										</div>
									</li>
								</c:if>
							<%-- </c:forEach> --%>
						</ul>
					<%-- </c:foreach> --%>
				</ul>
			</c:if>
			<script>
				/**
				 * 포럼의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
				 */
				function openEditForumCommentModal() {
					
				}
				/**
				 * 포럼의 대댓글을 작성하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
				 */
				function openWriteForumSubcommentModal() {
					
				}
				/**
				 * 포럼의 대댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
				 */
				function openEditForumSubcommentModal() {
					
				}
			</script>
			
			<!-- 더보기 버튼 viewForum 쪽으로 빼야할 수도 있음 -->
			<div class="more_comment_btn">
				<button>
					<span>더보기</span>
					<img src="/images/chompessor/arrow_down.png">
				</button>
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
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editForumCommentContentInput = document.getElementById("editForumCommentContentInput");
			const editForumCommentButton = document.getElementById("editForumCommentButton");
			editForumCommentContentInput.addEventListener("input", function() {
				const isEditForumCommentContentInputEmpty = editForumCommentContentInput.value.trim() === "";
				editForumCommentButton.classList.toggle("btn-disable", isEditForumCommentContentInputEmpty);
				editForumCommentButton.classList.toggle("btn-primary", !isEditForumCommentContentInputEmpty);
				editForumCommentButton.disabled = isEditForumCommentContentInputEmpty;
		    });
		</script>
		
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
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const writeForumSubcommentContentInput = document.getElementById("writeForumSubcommentContentInput");
			const writeForumSubcommentButton = document.getElementById("writeForumSubcommentButton");
			writeForumSubcommentContentInput.addEventListener("input", function() {
				const isWriteForumSubcommentContentInputEmpty = writeForumSubcommentContentInput.value.trim() === "";
				writeForumSubcommentButton.classList.toggle("btn-disable", isWriteForumSubcommentContentInputEmpty);
				writeForumSubcommentButton.classList.toggle("btn-primary", !isWriteForumSubcommentContentInputEmpty);
				writeForumSubcommentButton.disabled = isWriteForumSubcommentContentInputEmpty;
		    });
		</script>
		
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
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editForumSubcommentContentInput = document.getElementById("editForumSubcommentContentInput");
			const editForumSubcommentButton = document.getElementById("editForumSubcommentButton");
			editForumSubcommentContentInput.addEventListener("input", function() {
				const isEditForumSubcommentContentInputEmpty = editForumSubcommentContentInput.value.trim() === "";
				editForumSubcommentButton.classList.toggle("btn-disable", isEditForumSubcommentContentInputEmpty);
				editForumSubcommentButton.classList.toggle("btn-primary", !isEditForumSubcommentContentInputEmpty);
				editForumSubcommentButton.disabled = isEditForumSubcommentContentInputEmpty;
			});
		</script>
		
		
		
		
	</body>
</html>