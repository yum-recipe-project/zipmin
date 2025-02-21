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
				<!-- 댓글을 돌면서 -->
				<%-- <c:forEach> --%>
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
					</li>
				<%-- </c:forEach> --%>
			</ul>
		</c:if>
		<script>
			/**
			 * 레시피의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
			 */
			function openEditGuideCommentModal() {
				
			}
		</script>
		
		<!-- 키친가이드의 댓글 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editGuideCommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>수정하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="editGuideCommentContentInput" name="content" style="height: 90px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
							<button type="submit" id="editGuideCommentButton" class="btn btn-disable" disabled>작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script>
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editCommentContentInput = document.getElementById("editGuideCommentContentInput");
			const editCommentButton = document.getElementById("editGuideCommentButton");
			editCommentContentInput.addEventListener("input", function() {
		        if (editCommentContentInput.value.trim() !== "") {
		        	editCommentButton.classList.remove("btn-disable");
		        	editCommentButton.classList.add("btn-primary");
		        	editCommentButton.removeAttribute("disabled");
		        }
		        else {
		        	editCommentButton.classList.remove("btn-primary");
		        	editCommentButton.classList.add("btn-disable");
		        	editCommentButton.setAttribute("disabled", "true");
		        }
		    });
		</script>
		
	</body>
</html>