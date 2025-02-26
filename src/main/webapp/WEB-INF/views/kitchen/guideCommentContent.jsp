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
							<button class="btn_tool write_subcomment_btn" href="">
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
		
		<!-- 신고 모달창 -->
		<form id="reportGuideCommentForm" onsubmit="return validateReportGuideCommentForm();">
			<div class="modal" id="reportGuideCommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>신고하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<label>키친 한마디 신고 사유</label>
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
		
		
	</body>
</html>