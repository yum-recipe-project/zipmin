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
										<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeCommentModal"
											onclick="openEditRecipeCommentModal();">
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
							<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeRecipeSubcommentModal"
								onclick="openWriteRecipeSubcommentModal();">
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
													<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeSubcommentModal"
														onclick="openEditRecipeSubcommentModal();">
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
			 * 레시피의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
			 */
			function openEditRecipeCommentModal() {
				
			}
			/**
			 * 레시피의 대댓글을 작성하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
			 */
			function openWriteRecipeSubcommentModal() {
				
			}
			/**
			 * 레시피의 대댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
			 */
			function openEditRecipeSubcommentModal() {
				
			}
		</script>
		
		<!-- 레시피의 댓글 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeCommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>수정하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="editRecipeCommentContentInput" name="content" style="height: 90px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
							<button type="submit" id="editRecipeCommentButton" class="btn btn-disable" disabled>작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editRecipeCommentContentInput = document.getElementById("editRecipeCommentContentInput");
			const editRecipeCommentButton = document.getElementById("editRecipeCommentButton");
			editRecipeCommentContentInput.addEventListener("input", function() {
				const isEditRecipeCommentContentInputEmpty = editRecipeCommentContentInput.value.trim() === "";
				editRecipeCommentButton.classList.toggle("btn-disable", isEditRecipeCommentContentInputEmpty);
				editRecipeCommentButton.classList.toggle("btn-primary", !isEditRecipeCommentContentInputEmpty);
				editRecipeCommentButton.disabled = isEditRecipeCommentContentInputEmpty;
		    });
		</script>
		
		<!-- 레시피의 대댓글 작성 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="writeRecipeSubcommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>작성하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="writeRecipeSubcommentContentInput" name="content" style="height: 90px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
							<button type="submit" id="writeRecipeSubcommentButton" class="btn btn-disable" disabled>작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const writeRecipeSubcommentContentInput = document.getElementById("writeRecipeSubcommentContentInput");
			const writeRecipeSubcommentButton = document.getElementById("writeRecipeSubcommentButton");
			writeRecipeSubcommentContentInput.addEventListener("input", function() {
				const isWriteRecipeSubcommentContentInputEmpty = writeRecipeSubcommentContentInput.value.trim() === "";
				writeRecipeSubcommentButton.classList.toggle("btn-disable", isWriteRecipeSubcommentContentInputEmpty);
				writeRecipeSubcommentButton.classList.toggle("btn-primary", !isWriteRecipeSubcommentContentInputEmpty);
				writeRecipeSubcommentButton.disabled = isWriteRecipeSubcommentContentInputEmpty;
		    });
		</script>
		
		<!-- 레시피의 대댓글 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeSubcommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>수정하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="editRecipeSubcommentContentInput" name="content" style="height: 90px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
							<button type="submit" id="editRecipeSubcommentButton" class="btn btn-disable" disabled>작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editRecipeSubcommentContentInput = document.getElementById("editRecipeSubcommentContentInput");
			const editRecipeSubcommentButton = document.getElementById("editRecipeSubcommentButton");
			editRecipeSubcommentContentInput.addEventListener("input", function() {
				const isEditRecipeSubcommentContentInputEmpty = editRecipeSubcommentContentInput.value.trim() === "";
				editRecipeSubcommentButton.classList.toggle("btn-disable", isEditRecipeSubcommentContentInputEmpty);
				editRecipeSubcommentButton.classList.toggle("btn-primary", !isEditRecipeSubcommentContentInputEmpty);
				editRecipeSubcommentButton.disabled = isEditRecipeSubcommentContentInputEmpty;
			});
		</script>
	</body>
</html>