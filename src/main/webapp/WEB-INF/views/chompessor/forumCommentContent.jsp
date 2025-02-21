<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
	</head>
	<body>
		<input type="hidden" id="commentCount" value="">
		
		<!-- 댓글 목록 -->
		<c:if test="${ true }">
			<ul class="comment_list">
				<!-- 댓글을 돌면서 original idx랑 comment idx랑 일치하면 -->
				<%-- <c:foreach> --%>
					<c:if test="${ true }">
						<li class="comment">
							<!-- 댓글 헤더 -->
							<div class="comment_header">
								<div class="comment_writer">
									<img src="/images/common/test.png">
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
							<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeForumSubcommentModal"
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
								<li class="subcomment">
									<img src="/images/chompessor/arrow_right.png">
									<div class="subcomment_inner">
										<!-- 대댓글 헤더 -->
										<div class="subcomment_header">
											<div class="subcomment_writer">
												<img src="/images/common/test.png">
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