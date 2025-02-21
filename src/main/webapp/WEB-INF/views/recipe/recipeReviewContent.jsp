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
		<input type="hidden" id="reviewCount" value="">
		
		<!-- 리뷰 목록 -->
		<c:if test="${ true }">
			<ul class="review_list">
				<!-- 리뷰를 돌면서 original idx랑 comment idx랑 일치하면 -->
				<%-- <c:foreach> --%>
					<c:if test="${ true }">
						<li class="review">
							<img class="review_avatar" src="/images/common/test.png">
							<div class="review_inner">
								<!-- 리뷰 헤더 -->
								<div class="review_info">
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
									<button class="btn_like" onclick="">
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
		<script>
			/**
			 * 레시피 리뷰를 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
			 */
			function openEditRecipeReviewModal() {
				
			}
		</script>
		
		<!-- 레시피의 리뷰 수정 모달창 -->
		<form method="post" action="" onsubmit="">
			<div class="modal" id="editRecipeReviewModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>수정하기</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							<div class="form-group">
		    					<label>별점</label>
								<div id="editStarGroup" class="star_group">
									<img src="/images/recipe/star_full.png" class="star" data-value="1">
									<img src="/images/recipe/star_outline.png" class="star" data-value="2">
									<img src="/images/recipe/star_outline.png" class="star" data-value="3">
									<img src="/images/recipe/star_outline.png" class="star" data-value="4">
									<img src="/images/recipe/star_outline.png" class="star" data-value="5">
									<input type="hidden" id="editStarInput" name="rating" value="1">
								</div>
							</div>
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="editReviewContentInput" name="content" style="height: 90px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn" data-bs-dismiss="modal">닫기</button>
							<button type="submit" id="editReviewButton" class="btn btn-disable" disabled>작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script>
			/*
			 *
			 */
			const editStars = document.querySelectorAll('#editStarGroup .star');
			const editStarInput = document.getElementById('editStarInput');
			
			editStars.forEach(star => {
				star.addEventListener('click', function() {
					starInput.value = this.getAttribute('data-value');
					editStars.forEach(s => {
						const starValue = Number(s.getAttribute('data-value'));
						s.src = starValue <= this.getAttribute('data-value') ? '/images/recipe/star_full.png' : '/images/recipe/star_outline.png';
					});
				});
			});
			
			/**
			 * 내용 입력창의 폼값을 검증하는 함수
			 */
			const editReviewContentInput = document.getElementById("editReviewContentInput");
			const editReviewButton = document.getElementById("editReviewButton");
			editReviewContentInput.addEventListener("input", function() {
		        if (editReviewContentInput.value.trim() !== "") {
		        	editReviewButton.classList.remove("btn-disable");
		        	editReviewButton.classList.add("btn-primary");
		        	editReviewButton.removeAttribute("disabled");
		        }
		        else {
		        	editReviewButton.classList.remove("btn-primary");
		        	editReviewButton.classList.add("btn-disable");
		        	editReviewButton.setAttribute("disabled", "true");
		        }
		    });
		</script>
	</body>
</html>