<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/kitchen/guide-comment-content.css">
	</head>
	<body>
		<input type="hidden" id="commentCount" value="">
		
		<!--  댓글 -->
		<div class="comment_wrap">
			<!-- 댓글 헤더 -->
			<div class="comment_header">
				<div class="comment_count">
					<span>키친 한마디</span>
					<span>4</span>
				</div>
				<div class="comment_order">
					<button class="active">최신순</button>
					<button>오래된순</button>
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
			</div>
			
			<!-- 댓글 작성 -->
			<div class="comment_write">
				<!-- 로그인 하지 않은 경우 -->
				<c:if test="${ false }">
					<a href="/member/login.do">
						<span>키친 한마디 작성을 위해 로그인을 해주세요.</span>
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
							<textarea id="commentInput" rows="2" maxlength="400" placeholder="나누고 싶은 좋은 방법이 있다면 키친 한마디을 남겨주세요!&#10;주제와 무관한 키친 한마디는 삭제될 수 있습니다."></textarea>
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
				const commentButton = document.getElementById("commentButton");
				commentInput.addEventListener("input", function() {
					const isCommentEmpty = commentInput.value.trim() === "";
			    	commentButton.classList.toggle("disable", isCommentEmpty);
			    	commentButton.disabled = isCommentEmpty;
			    });
			</script>
			
			<!-- 댓글 목록 -->
			<c:if test="${ true }">
				<ul class="comment_list">
					<!-- 댓글을 돌면서 -->
					<%-- <c:forEach> --%>
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
			
		</div>
		
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