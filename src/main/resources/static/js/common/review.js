

/***** comment.js의 전역변수를 그대로 사용하면 레시피 상세 페이지에서 에러가 남!! 주의 필요 *****/
/***** 클래스명도 안겹치게 주의 필요 *****/



/**
 * 리뷰 폼 값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 리뷰 정렬 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
	const tabList = document.querySelectorAll('.review_order button');
	tabList.forEach(tabI => {
		tabI.addEventListener('click', function(event) {
			event.preventDefault();
			tabList.forEach(tabJ => tabJ.classList.remove('active'));
			this.classList.add('active');
        });
	});
	
	// 리뷰 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
	const writeReviewContent = document.getElementById("writeReviewContent");
	const reviewContentBorder = document.querySelector('.review_input');
	writeReviewContent.addEventListener('focus', function() {
		reviewContentBorder.classList.add('focus');
	});
	writeReviewContent.addEventListener('blur', function() {
		reviewContentBorder.classList.remove('focus');
	});
	
	// 리뷰를 작성하는 폼의 별점을 선택하는 함수
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
	
	// 리뷰 작성 폼값을 검증하고 버튼을 활성화하는 함수
	const writeReviewButton = document.querySelector("#writeReviewForm button[type='submit']");
	writeReviewContent.addEventListener("input", function() {
		const isReviewCommentEmpty = writeReviewContent.value.trim() === "";
		writeReviewButton.classList.toggle("disable", isReviewCommentEmpty);
		writeReviewButton.disabled = isReviewCommentEmpty;
	});
	
	// 리뷰를 수정하는 폼의 별점을 선택하는 함수
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
	
	// 리뷰를 수정하는 모달창의 폼값을 검증하고 버튼을 활성화하는 함수
	const editReviewContent = document.getElementById("editReviewContent");
	const editReviewButton = document.querySelector("#editReviewForm button[type='submit']");
	editReviewContent.addEventListener("input", function() {
		const isReviewContentEmpty = editReviewContent.value.trim() === "";
		editReviewButton.classList.toggle("disabled", isReviewContentEmpty);
	});
	
	// 리뷰를 신고하는 모달창의 신고 사유를 선택했는지 검증하는 함수
	const reasonRadio = document.querySelectorAll("#reportReviewForm input[name='reason']");
	const submitButton = document.querySelector("#reportReviewForm button[type='submit']");

	reasonRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
	
});




/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */




















// 리뷰 데이터 관련 변수
/*
var reviewOffset = 0; // 현재 데이터 시작 위치
var reviewLimit = 10; // 한 번에 가져올 데이터 개수
var reviewCount = 0; // 전체 리뷰 개수

document.addEventListener("DOMContentLoaded", function () {
    // 초기 리뷰 데이터 로드
    loadRecipeReviewContent();

    // "더보기" 버튼 클릭 시 추가 데이터 로드
    document.querySelector('.more_review_btn').addEventListener('click', function (event) {
        event.preventDefault();
        loadRecipeReviewContent();
    });
});
*/

/**
 * 레시피 리뷰 목록 데이터를 fetch로 로드하는 함수
 */
/*
function loadRecipeReviewContent() {
    fetch("http://localhost:8586/recipes/1/reviews", {
		method: "GET"
    })
	.then(response => response.json())
	.then(data => {
		const reviewListElement = document.getElementById("reviewList");
        reviewListElement.innerHTML = data.map(review => `
            <li class="review">
                <img class="review_avatar" src="/images/common/test.png">
                <div class="review_inner">
                    <!-- 리뷰 헤더 -->
                    <div class="review_info">
                        <div class="review_writer">
                            <span>${review.writer}</span>
                            <span>${review.postdate}</span>
                        </div>
                        <div class="review_action">
                            <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editRecipeReviewModal"
                                onclick="openEditRecipeReviewModal();">수정</a>
                            <a href="javascript:void(0);">삭제</a>
                        </div>
                    </div>
                    <!-- 리뷰 별점 -->
                    <div class="review_score">
                        <div class="star">
                            ${'<img src="/images/recipe/star_full.png">'.repeat(review.score)}
                            ${'<img src="/images/recipe/star_empty.png">'.repeat(5 - review.score)}
                        </div>
                        <p>${review.score}</p>
                    </div>
                    <!-- 리뷰 내용 -->
                    <p class="review_content">${review.content}</p>
                    <!-- 리뷰 좋아요 버튼 -->
                    <div class="like_review_btn">
                        <p>이 리뷰가 도움이 되었다면 꾹!</p>
                        <button class="btn_like">
                            <img src="/images/recipe/thumb_up_full.png">
                            <img src="/images/recipe/thumb_up_empty.png">
                            <p>${review.likes}</p>
                        </button>
                    </div>
                </div>
            </li>
        `).join("");

        // 전체 리뷰 개수 설정 (첫 요청 시)
        if (reviewCount === 0) {
            reviewCount = data.totalCount; // 서버에서 전체 개수를 제공한다고 가정
        }

        // offset 증가 (다음 데이터 가져오기 위한 설정)
        reviewOffset += reviewLimit;

        // 불러올 데이터가 없으면 "더보기" 버튼 숨김
        const moreReviewButton = document.querySelector('.more_review_btn');
        if (reviewOffset >= reviewCount) {
            moreReviewButton.style.display = "none";
        } else {
            moreReviewButton.style.display = "block";
        }
    })
    .catch(error => {
        console.error("리뷰 데이터를 불러오는 중 오류 발생:", error);
        document.querySelector('.more_review_btn').style.display = "none";
    });
}
*/




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






