<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/recipe/recipe-review-content.css">
	</head>
	
	<body>
		<input type="hidden" id="review_count" value="">
		
		<div class="review_wrap">
			<!-- 리뷰 헤더 -->
			<div class="review_header">
				<div class="review_count">
					<span>리뷰</span>
					<span>7</span>
				</div>
				<div class="review_order">
					<button class="active">최신순</button>
					<button>도움순</button>
				</div>
				<script>
					/**
					 * 리뷰 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
					 * (ajax로 불러오기 때문에 더보기 버튼 상황에 따라서 적절히 변경해야할 수도 있음 아니면 view-recipe에 전역변수 선언해서 하거나)
					 */
					const tabItems = document.querySelectorAll('.review_order button');
				    const contentItems = document.querySelectorAll('.review_list');
					
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
			
			<!-- 리뷰 작성 -->
			<div class="review_write">
				<!-- 로그인 하지 않은 경우 -->
				<c:if test="${ false }">
					<a href="/member/login.do">
						<span>리뷰 작성을 위해 로그인을 해주세요.</span>
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
						
						<!-- 별점 (별점 선택하는 함수 여러가지 이유로 분리 못함) -->
						<div id="starGroup" class="star_group">
							<img src="/images/recipe/star_full.png" class="star" data-value="1">
							<img src="/images/recipe/star_outline.png" class="star" data-value="2">
							<img src="/images/recipe/star_outline.png" class="star" data-value="3">
							<img src="/images/recipe/star_outline.png" class="star" data-value="4">
							<img src="/images/recipe/star_outline.png" class="star" data-value="5">
							<input type="hidden" id="starInput" name="rating" value="1">
						</div>
						<script>
							const stars = document.querySelectorAll('#starGroup .star');
							const starInput = document.getElementById('starInput');
							
							stars.forEach(star => {
								star.addEventListener('click', function() {
									starInput.value = this.getAttribute('data-value');
									stars.forEach(s => {
										const starValue = Number(s.getAttribute('data-value'));
										s.src = starValue <= this.getAttribute('data-value') ? '/images/recipe/star_full.png' : '/images/recipe/star_outline.png';
									});
								});
							});
						</script>
						
						<!-- 리뷰 입력창 -->
						<div class="review_input">
							<textarea id="reviewInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
							<span>400</span>
						</div>
						<div class="write_btn">
							<button type="submit" id="reviewButton" class="disable" onclick="" disabled>작성하기</button>
						</div>
						<script>
							/**
							 * 리뷰 입력창의 폼 값을 검증하는 함수
							 */
							const reviewInput = document.getElementById("reviewInput");
							const reviewButton = document.getElementById("reviewButton");
						    reviewInput.addEventListener("input", function() {
						        if (reviewInput.value.trim() !== "") {
						            reviewButton.classList.remove("disable");
									reviewButton.removeAttribute("disabled");
						        }
						        else {
						            reviewButton.classList.add("disable");
									reviewButton.setAttribute("disabled", "true");
						        }
						    });
						</script>
					</form>
				</c:if>
			</div>
			
			<!-- 리뷰 목록 -->
			<c:if test="${ true }">
				<ul class="review_list">
					<!-- 리뷰를 돌면서 original idx랑 comment idx랑 일치하면 -->
					<%-- <c:foreach> --%>
						<c:if test="${ true }">
							<li class="review">
								<img src="/images/common/black.png">
								<div class="review_inner">
									<!-- 리뷰 헤더 -->
									<div class="review_header">
										<div class="review_writer">
											<span>아잠만</span>
											<span>2025.02.11</span>
										</div>
										<c:if test="${ true }">
											<div class="review_action">
												<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeReviewModal"
													onclick="openEditRecipeReviewModal();">
													수정
												</a>
												<script>
													/**
													 * 레시피 리뷰를 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
													 */
													function openEditRecipeReviewModal() {
														
													}
												</script>
												<a href="">삭제</a>
											</div>
										</c:if>
									</div>
									<!-- 리뷰 별점 -->
									<div class="review_score">
										<div class="star">
											<%-- <c:forEach> --%>
												<img src="/images/recipe/star_full.png">
											<%-- </c:forEach> --%>
											<%-- <c:forEach> --%>
												<img src="/images/recipe/star_empty.png">
											<%-- </c:forEach> --%>
										</div>
										<p>3</p>
									</div>
									<!-- 리뷰 내용 -->
									<p class="review_content">
										좋은 레시피 감사합니다
									</p>
									<!-- 리뷰 좋아요 버튼 -->
									<div class="like_review_btn">
										<p>이 리뷰가 도움이 되었다면 꾹!</p>
										<button onclick="">
											<c:if test="${ true }">
												<img src="/images/recipe/thumb_up_full.png">
											</c:if>
											<c:if test="${ false }">
												<img src="/images/recipe/thumb_up_empty.png">
											</c:if>
											<p>5</p>
										</button>
									</div>
								</div>
							</li>
						</c:if>
					<%-- </c:foreach> --%>
				</ul>
			</c:if>
			
			<div class="more_review_btn">
				<button>
					<span>더보기</span>
					<img src="/images/recipe/arrow_down_black.png">
				</button>
			</div>
		</div>
		
		<!-- 리뷰 수정 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeReviewModal">
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