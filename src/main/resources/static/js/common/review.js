
/**
 * 레시피 리뷰에 데이터를 설정하는 함수 (******** 테스트 ***********)
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
 * 리뷰 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeReviewContent = document.getElementById("writeReviewContent");
	const reviewInputBorder = document.querySelector('.review_input');
	writeReviewContent.addEventListener('focus', function() {
		reviewInputBorder.classList.add('focus');
	});
	writeReviewContent.addEventListener('blur', function() {
		reviewInputBorder.classList.remove('focus');
	});
});



/**
 * 리뷰를 작성하는 폼의 별점을 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeReviewStarStarGroup = document.querySelectorAll('#writeReviewStarGroup .star');
	const writeReviewStar = document.getElementById('editReviewStar');

	writeReviewStarStarGroup.forEach(starI => {
		starI.addEventListener('click', function() {
			writeReviewStar.value = this.getAttribute('data-value');
			stargroup.forEach(starJ => {
				const value = Number(starJ.getAttribute('data-value'));
				starJ.src = value <= this.getAttribute('data-value') ? '/images/common/star_full.png' : '/images/common/star_outline.png';
			});
		});
	});
});



/**
 * 리뷰 작성 폼값을 검증하고 버튼을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeReviewContent = document.getElementById("writeReviewContent");
	const writeReviewButton = document.querySelector("#writeReviewForm button[type='submit']");
	writeReviewContent.addEventListener("input", function() {
		const isReviewCommentEmpty = writeReviewContent.value.trim() === "";
		writeReviewButton.classList.toggle("disable", isReviewCommentEmpty);
		writeReviewButton.disabled = isReviewCommentEmpty;
	});
});



/**
 * 리뷰를 수정하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editReviewContent = document.getElementById("editReviewContent");
	const editReviewButton = document.querySelector("#editReviewForm button[type='submit']");
	editReviewContent.addEventListener("input", function() {
		const isReviewEmpty = editReviewContent.value.trim() === "";
		editReviewButton.classList.toggle("disabled", isReviewEmpty);
	});
});



/**
 * 리뷰를 신고하는 모달창의 신고 사유를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reasonRadio = document.querySelectorAll("#reportReviewForm input[name='reason']");
	const submitButton = document.querySelector("#reportReviewForm button[type='submit']");

	reasonRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
});
