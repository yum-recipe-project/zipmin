<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/recipe/recipe-comment-content.css">
	</head>
	<body>
		<input type="hidden" id="comment_count" value="">
		
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
				<script>
					/**
					 * 댓글 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
					 * (ajax로 불러오기 때문에 더보기 버튼 상황에 따라서 적절히 변경해야할 수도 있음 아니면 view-recipe에 전역변수 선언해서 하거나)
					 */
					const tabCommentItems = document.querySelectorAll('.comment_order button');
				    const contentCommentItems = document.querySelectorAll('.comment_list');

				    // 탭 클릭 이벤트 설정
				    tabCommentItems.forEach((item, index) => {
				        item.addEventListener("click", function(event) {
				            event.preventDefault();
				            
				            tabCommentItems.forEach(button => button.classList.remove('active'));
				            this.classList.add('active');
				            
				            contentCommentItems.forEach(content => content.style.display = 'none');
				            contentCommentItems[index].style.display = 'block';
				        });
				    });
	
				    // 기본으로 첫번째 활성화
				    tabCommentItems.forEach(button => button.classList.remove('active'));
				    contentCommentItems.forEach(content => content.style.display = 'none');
	
				    tabCommentItems[0].classList.add('active');
				    contentCommentItems[0].style.display = 'block';
				</script>
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
						<script>
							/**
							 * 댓글 입력창의 폼 값을 검증하는 함수
							 */
							const commentInput = document.getElementById("commentInput");
							const commentButton = document.getElementById("commentButton");
							commentInput.addEventListener("input", function() {
						        if (commentInput.value.trim() !== "") {
						        	commentButton.classList.remove("disable");
						        	commentButton.removeAttribute("disabled");
						        }
						        else {
						        	commentButton.classList.add("disable");
						        	commentButton.setAttribute("disabled", "true");
						        }
						    });
						</script>
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
								<div class="comment_header">
									<div class="comment_writer">
										<img src="/images/common/black.png">
										<span>아잠만</span>
										<span>2025.02.11</span>
									</div>
									<c:if test="${ true }">
										<div class="comment_action">
											<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeCommentModal"
												onclick="openEditRecipeCommentModal();">
												수정
											</a>
											<script>
												/**
												 * 레시피의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
												 */
												function openEditRecipeCommentModal() {
													
												}
											</script>
											<a href="">삭제</a>
										</div>
									</c:if>
								</div>
								<!-- 댓글 내용 -->
								<p class="comment_content">
									녹차 아이스크림은 배스킨라빈스가 최고입니다
								</p>
								<!-- 대댓글 쓰기 버튼 -->
								<a class="write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeRecipeSubcommentModal"
									onclick="openWriteRecipeSubcommentModal();">
									<span>답글 쓰기</span>
								</a>
								<script>
									/**
									 * 레시피의 대댓글을 작성하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
									 */
									function openWriteRecipeSubcommentModal() {
										
									}
								</script>
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
													<img src="/images/common/black.png">
													<span>아잠만</span>
													<span>2025.02.11</span>
												</div>
												<c:if test="${ true }">
													<div class="subcomment_action">
														<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeSubcommentModal"
															onclick="openEditRecipeSubcommentModal();">
															수정
														</a>
														<script>
															/**
															 * 레시피의 대댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
															 */
															function openEditRecipeReviewModal() {
																
															}
														</script>
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
			
			<div class="more_comment_btn">
				<button>
					<span>더보기</span>
					<img src="/images/recipe/arrow_down_black.png">
				</button>
			</div>
		</div>
		
		<!-- 레시피의 댓글 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeCommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5></h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<textarea class="form-control" name="content" style="height: 100px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-primary">작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		<!-- 레시피의 대댓글 작성 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="writeRecipeSubcommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5></h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<textarea class="form-control" name="content" style="height: 100px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-primary">작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		<!-- 레시피의 대댓글 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeSubcommentModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5></h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<textarea class="form-control" name="content" style="height: 100px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-primary">작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>