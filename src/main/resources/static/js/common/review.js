document.addEventListener('DOMContentLoaded', function() {
	fetchReviewList();
});




/**
 * 로그인 여부에 따라 리뷰 작성폼을 다르게 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', async function () {
	
	if (isLoggedIn()) {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		document.getElementById('login_state').style.display = 'block';
		document.getElementById('logout_state').style.display = 'none';
		document.getElementById('writeReviewNickname').innerText = payload.nickname;
	}
	else {
		document.getElementById('login_state').style.display = 'none';
		document.getElementById('logout_state').style.display = 'block';
	}

});



/**
 * 전역변수
 */
let reviewTotalPages = 0;
let reviewTotalElements = 0;
let reviewPage = 0;
const reviewSize = 15;
let reviewTablename = '';
let reviewSort = '';
let reviewList = [];

/**
 * 서버에서 리뷰 목록 데이터를 가져오는 함수
 */
async function fetchReviewList() {
    try {
        const id = new URLSearchParams(window.location.search).get('id');

        const params = new URLSearchParams({
            recipeId: id,            // 리뷰는 recipeId 기준
            sort: reviewSort,
            page: reviewPage,
            size: reviewSize
        }).toString();

        const response = await fetch(`/reviews?${params}`, {
            method: 'GET',
            headers: getAuthHeaders()  // 인증 헤더 포함
        });

        const result = await response.json();

        if (result.code === 'REVIEW_READ_LIST_SUCCESS') {

            // 전역 변수 설정
            reviewTotalPages = result.data.totalPages;
            reviewTotalElements = result.data.totalElements;
            reviewList = [...reviewList, ...result.data.content];

            // 렌더링 (renderReviewList는 나중에 구현)
            renderReviewList(reviewList);

            // 리뷰 총 개수 표시
            document.querySelector('.review_count span:last-of-type').innerText = reviewTotalElements;

            // 더보기 버튼 제어
            document.querySelector('.more_review_btn .btn_more').style.display = reviewPage >= reviewTotalPages - 1 ? 'none' : 'block';

            // 리뷰 목록이 없을 때 처리
            if (reviewTotalPages === 0) {
                document.querySelector('.review_list').style.display = 'none';
                document.querySelector('.list_empty')?.remove();
                const content = document.querySelector('.review_write');
                content.insertAdjacentElement('afterend', renderListEmpty());
            } else {
                document.querySelector('.list_empty')?.remove();
                document.querySelector('.review_list').style.display = '';
            }
        }
        else if (result.code === 'REVIEW_READ_LIST_FAIL') {
            alertDanger('리뷰 목록 조회에 실패했습니다.');
        }
        else {
            console.log('알 수 없는 에러:', result);
        }
    }
    catch (error) {
        console.log(error);
    }
}






/**
 * 리뷰 목록을 화면에 렌더링하는 함수
 * @param {Array} reviewList - 서버에서 받아온 리뷰 데이터 배열
 */
function renderReviewList(reviewList) {
	
	console.log(reviewList);
	
    const container = document.getElementById('reviewList');
    container.innerHTML = ''; 

    reviewList.forEach(review => {
        const reviewList = document.createElement('li');
        reviewList.className = 'review';
        reviewList.dataset.id = review.id;
		
        // 리뷰 내부 wrapper
        const innerDiv = document.createElement('div');
        innerDiv.className = 'review_inner';

        // 리뷰 헤더
        const infoDiv = document.createElement('div');
        infoDiv.className = 'review_info';

        // 작성자 정보
        const writerDiv = document.createElement('div');
        writerDiv.className = 'review_writer';
        const avatar = document.createElement('img');
        avatar.className = 'review_avatar';
        avatar.src = '/images/common/test.png'; // 임시, 실제 유저 프로필 이미지로 교체 가능
        const nameSpan = document.createElement('span');
        nameSpan.textContent = review.nickname;
		
        const dateSpan = document.createElement('span');
        dateSpan.textContent = formatDate(review.postdate);
        writerDiv.append(avatar, nameSpan, dateSpan);

        // 기능 버튼 (신고 / 수정 / 삭제)
        const actionDiv = document.createElement('div');
        actionDiv.className = 'review_action';

        const payload = isLoggedIn() ? getPayload() : null;
		
        const canAction =
            payload?.role === 'ROLE_SUPER_ADMIN' ||
            (payload?.role === 'ROLE_ADMIN' && review.role === 'ROLE_USER') ||
            (payload?.id === review.user_id);
			

        // 수정 / 삭제 버튼
        if (canAction) {
            const editLink = document.createElement('a');
            editLink.href = 'javascript:void(0);';
            editLink.dataset.bsToggle = 'modal';
            editLink.dataset.bsTarget = '#editReviewModal';
            editLink.textContent = '수정';
            editLink.addEventListener('click', function() {
                document.getElementById('editReviewContent').value = review.content;
                document.getElementById('editReviewId').value = review.id;
            });

            const deleteLink = document.createElement('a');
            deleteLink.href = 'javascript:void(0);';
            deleteLink.textContent = '삭제';
            deleteLink.addEventListener('click', function() {
                deleteReview(review.id);
            });

            actionDiv.append(editLink, deleteLink);
        }

        // 신고 버튼
        const reportLink = document.createElement('a');
        reportLink.href = 'javascript:void(0);';
        reportLink.textContent = '신고';
        reportLink.dataset.bsToggle = 'modal';
        reportLink.dataset.bsTarget = '#reportReviewModal';
        reportLink.addEventListener('click', function() {
            if (!isLoggedIn()) {
                redirectToLogin();
                return;
            }
            document.getElementById('reportReviewId').value = review.id;
        });
        actionDiv.append(reportLink);

        infoDiv.append(writerDiv, actionDiv);

        // 별점
        const scoreDiv = document.createElement('div');
        scoreDiv.className = 'review_score';
        const starDiv = document.createElement('div');
        starDiv.className = 'star';

        for (let i = 1; i <= 5; i++) {
            const starImg = document.createElement('img');
            starImg.src = i <= review.score ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
            starDiv.appendChild(starImg);
        }
        const scoreP = document.createElement('p');
        scoreP.textContent = review.score;
        scoreDiv.append(starDiv, scoreP);

        // 리뷰 내용
        const contentP = document.createElement('p');
        contentP.className = 'review_content';
        contentP.textContent = review.content;
		
        // 좋아요 버튼
		const likeDiv = document.createElement('div');
		likeDiv.className = 'like_review_btn';
		
		const likeText = document.createElement('p');
		likeText.textContent = '이 리뷰가 도움이 되었다면 꾹!';
		
		const likeBtn = renderReviewLikeButton(review.id, review.likecount, review.liked);
		
		likeDiv.append(likeText, likeBtn);
		innerDiv.append(infoDiv, scoreDiv, contentP, likeDiv);

		reviewList.appendChild(innerDiv);
        container.appendChild(reviewList);
    });
}



/**
 * 리뷰 좋아요 버튼을 생성하는 함수
 */
function renderReviewLikeButton(id, likecount, isLiked) {
	   const likeBtn = document.createElement('button');
	   likeBtn.className = 'btn_like';
	   
	   const img = document.createElement('img');
	   img.src = isLiked ? '/images/recipe/thumb_up_full.png' : '/images/recipe/thumb_up_empty.png';

	   const likeCountP = document.createElement('p');
	   likeCountP.textContent = likecount;

	   likeBtn.append(img, likeCountP);

	   // 클릭 이벤트
	   likeBtn.addEventListener('click', function() {
			console.log("클릭");			
	   });

	   return likeBtn;
}






















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






