/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
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
})






/**
 * 레시피 리뷰에 데이터를 설정하는 함수
 */
fetch("http://localhost:8586/recipes/1/reviews", {
	method: "GET"
})
.then(response => response.json())
.then(data => {
	const reviewListElement = document.getElementById("reviewList");
	if(reviewListElement) console.log("있음");
	reviewListElement.innerHTML = data.map((review) => `
		<li class="review">
			<img class="review_avatar" src="/images/common/test.png">
			<div class="review_inner">
				<!-- 리뷰 헤더 -->
				<div class="review_info">
					<div class="review_writer">
						<span>아잠만</span>
						<span>${ review.postdate }</span>
					</div>
					/* 조건 */
						<div class="review_action">
							<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeReviewModal"
								onclick="openEditRecipeReviewModal();">
								수정
							</a>
							<a href="">삭제</a>
						</div>
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
				<p class="review_content">${ review.content }</p>
				<!-- 리뷰 좋아요 버튼 -->
				<div class="like_review_btn">
					<p>이 리뷰가 도움이 되었다면 꾹!</p>
					<button class="btn_like" onclick="">
							<img src="/images/recipe/thumb_up_full.png">
							<img src="/images/recipe/thumb_up_empty.png">
						<p>5</p>
					</button>
				</div>
			</div>
		</li>
	`).join("");
})
.catch(error => console.log(error));