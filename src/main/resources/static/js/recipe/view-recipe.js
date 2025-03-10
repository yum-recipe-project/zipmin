/**
 * 레시피 상세 페이지에 데이터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	// 데이터 패치
	fetch("http://localhost:8586/recipes/1", {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		// 제목
		const titleElement = document.getElementById("title");
		titleElement.innerText = data.title;
		// 단계
		const levelElement = document.getElementById("level");
		levelElement.innerText = data.level;
		// 조리 시간
		const timeElement = document.getElementById("time");
		timeElement.innerText = data.time;
		// 맵기 정도
		const spicyElement = document.getElementById("spicy");
		spicyElement.innerText = data.spicy;
		// 소개
		const introduceElement = document.getElementById("introduce");
		introduceElement.innerText = data.introduce;
		// 팁
		const tipElement = document.getElementById("tip");
		tipElement.innerText = data.tip;
		// 닉네임
		const nicknameElement = document.querySelectorAll(".nickname[data-id]");
		nicknameElement.forEach(nickname => { nickname.innerText = data.member.nickname; });
		// 조리 양
		const servingInput = document.getElementById("servingInput");
		servingInput.value = data.serving;
		// 재료
		const ingredientElement = document.getElementById("ingredient");
		ingredientElement.innerHTML = data.ingredient_list.map(ingredient => `
		    <tr>
		        <td>${ingredient.name}</td>
		        <td>${ingredient.amount}${ingredient.unit}</td>
		        <td>${ingredient.note || ""}</td>
		    </tr>
		`).join("");
		// 조리 순서
		const stepElement = document.getElementById("step");
		stepElement.innerHTML = data.step_list.map((step, index) => `
			<li>
				<div class="description">
					<h5>STEP${ index + 1 }</h5>
					<p><span class="hidden">${ index + 1 }.&nbsp;</span>${ step.description }</p>
				</div>
				${step.image ? `<div class="image"><img src="${step.image}"></div>` : `<div class="image"><img src="/images/common/test.png"></div>`}
			</li>
		`).join("");
		// 구독자 수
		const followerElement = document.getElementById("follower");
		followerElement.innerText = data.follower_count;
		// 구독 버튼
		const followButton = document.getElementById("followButton");
		followButton.classList.toggle("btn_outline", data.isFollow);
		followButton.classList.toggle("btn_dark", !data.isFollow);
		followButton.innerText = (data.isFollow === false ? "구독" : "구독 중");
		// 리뷰 수
		const reviewCountElement = document.querySelectorAll(".review_count[data-id]");
		reviewCountElement.forEach(reviewcount => { reviewcount.innerText = data.review_count; });
		// 댓글 수
		const commentCountElement = document.querySelectorAll(".comment_count[data-id]");
		commentCountElement.forEach(commentcount => { commentcount.innerText = data.comment_count; });
		// 장보기메모 (모달창)
		const memoElement = document.getElementById("memo");
		memoElement.innerHTML = data.ingredient_list.map((ingredient, index) => `
		    <tr>
		        <td>${ingredient.name}</td>
		        <td>${ingredient.amount}${ingredient.unit}</td>
		        <td>
		            <input type="checkbox" id="addMemo_${index}" name="ingredient" checked>
		            <label for="addMemo_${index}"></label>
		        </td>
		    </tr>
		`).join("");
	})
	.catch(error => console.log(error));
});



/**
 * 리뷰/댓글 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const tabItems = document.querySelectorAll('.tab_button button');
    const contentItems = document.querySelectorAll('.tab_content');

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

    // 선택된 탭 활성화
    tabItems.forEach(button => button.classList.remove('active'));
    contentItems.forEach(content => content.style.display = 'none');

    tabItems[0].classList.add('active');
    contentItems[0].style.display = 'block';
});



// 리뷰 데이터 관련 변수
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

/**
 * 레시피 리뷰 목록 데이터를 fetch로 로드하는 함수
 */
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




/**
 * 레시피 댓글 목록 데이터를 로드하는 함수
 */
function loadRecipeCommentContent() {
    $.ajax({
        url: '/recipe/viewRecipe/comment.do',
        type: 'GET',
        data: {
	        offset: commentOffset,
	        limit: commentLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#recipeCommentContent').append(response);

            // 서버에서 총 데이터의 개수를 가져와서 설정
            commentCount = parseInt($('#commentCount').val());
	        commentOffset += commentLimit;

            // 불러올 데이터가 없으면 더보기 버튼 숨김
            if (commentOffset >= commentCount) {
                $('.more_comment_btn').hide();
            } else {
                $('.more_comment_btn').show();
            }
        },
        error: function () {
            $('.more_comment_btn').hide();
        }
    });
}





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


