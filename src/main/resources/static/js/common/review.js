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
 * 리뷰 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabReviewItems = document.querySelectorAll('.review_order button');
	const contentReviewItems = document.querySelectorAll('.review_list');
	// 탭 클릭 이벤트 설정
	tabReviewItems.forEach((item, index) => {
		item.addEventListener("click", function(event) {
			event.preventDefault();
            
			tabReviewItems.forEach(button => button.classList.remove('active'));
			this.classList.add('active');
            
			contentReviewItems.forEach(content => content.style.display = 'none');
			contentReviewItems[index].style.display = 'block';
        });
	});
	// 기본으로 첫번째 활성화
	tabReviewItems.forEach(button => button.classList.remove('active'));
	contentReviewItems.forEach(content => content.style.display = 'none');
	tabReviewItems[0].classList.add('active');
	contentReviewItems[0].style.display = 'block';
});




/**
 * 리뷰 작성 폼의 별점을 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
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
});



/**
 * 리뷰 작성 폼의 focus 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reviewInput = document.getElementById("reviewInput");
	const reviewInputBorder = document.querySelector('.review_input');
	reviewInput.addEventListener('focus', function() {
		reviewInputBorder.classList.add('focus');
	});
	reviewInput.addEventListener('blur', function() {
		reviewInputBorder.classList.remove('focus');
	});
});



/**
 * 리뷰 작성 폼값을 검증하는 함수 (댓글 내용 미작성 시 버튼 비활성화)
 */
document.addEventListener('DOMContentLoaded', function() {
	const reviewButton = document.getElementById("reviewButton");
	reviewInput.addEventListener("input", function() {
		const isReviewEmpty = reviewInput.value.trim() === "";
		reviewButton.classList.toggle("disable", isReviewEmpty);
		reviewButton.disabled = isReviewEmpty;
	});
});





/**
 * 레시피 리뷰에 데이터를 설정하는 함수
 */
/*
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
				<p class="review_content">${ review.content }</p>
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
*/



/**************** 여기까지 테스트 ***********/