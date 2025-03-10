/**
 * 리뷰 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabList = document.querySelectorAll('.review_order button');
	tabList.forEach(tabI => {
		tabI.addEventListener("click", function(event) {
			event.preventDefault();
			tabList.forEach(tabJ => tabJ.classList.remove('active'));
			this.classList.add('active');
        });
	});
});



/**
 * 리뷰 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeReviewContent = document.getElementById("writeReviewContent");
	const reviewContentBorder = document.querySelector('.review_input');
	writeReviewContent.addEventListener('focus', function() {
		reviewContentBorder.classList.add('focus');
	});
	writeReviewContent.addEventListener('blur', function() {
		reviewContentBorder.classList.remove('focus');
	});
});



/**
 * 리뷰를 작성하는 폼의 별점을 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeReviewStarGroup = document.querySelectorAll('#writeReviewStarGroup .star');
	const writeReviewStar = document.getElementById('writeReviewStar');

	writeReviewStarGroup.forEach(starI => {
		starI.addEventListener('click', function() {
			writeReviewStar.value = this.getAttribute('data-value');
			writeReviewStarGroup.forEach(starJ => {
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
 * 리뷰를 수정하는 폼의 별점을 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editReviewStarGroup = document.querySelectorAll('#editReviewStarGroup .star');
	const editReviewStar = document.getElementById('editReviewStar');

	editReviewStarGroup.forEach(starI => {
		starI.addEventListener('click', function() {
			editReviewStar.value = this.getAttribute('data-value');
			editReviewStarGroup.forEach(starJ => {
				const value = Number(starJ.getAttribute('data-value'));
				starJ.src = value <= this.getAttribute('data-value') ? '/images/common/star_full.png' : '/images/common/star_outline.png';
			});
		});
	});
});



/**
 * 리뷰를 수정하는 모달창의 폼값을 검증하고 버튼을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editReviewContent = document.getElementById("editReviewContent");
	const editReviewButton = document.querySelector("#editReviewForm button[type='submit']");
	editReviewContent.addEventListener("input", function() {
		const isReviewContentEmpty = editReviewContent.value.trim() === "";
		editReviewButton.classList.toggle("disabled", isReviewContentEmpty);
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
