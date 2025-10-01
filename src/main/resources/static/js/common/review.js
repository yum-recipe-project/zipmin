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
 */
function renderReviewList(reviewList) {
	
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
        avatar.src = '/images/common/test.png';
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
				document.getElementById('editReviewStar').value = review.score;
				
				// 수정 시 별점 초기화
			    const editReviewStarGroup = document.querySelectorAll('#editReviewStarGroup .star');
			    editReviewStarGroup.forEach(star => {
			        const value = Number(star.getAttribute('data-value'));
			        star.src = value <= review.score
			            ? '/images/common/star_full.png'
			            : '/images/common/star_outline.png';
			    });
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






/**
 * 리뷰를 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('writeReviewForm').addEventListener('submit', async function (event) {
        event.preventDefault();

        try {
            const recipeId = new URLSearchParams(window.location.search).get('id');

			const data = {
			    score: Number(document.getElementById('writeReviewStar').value || 0), 
			    content: document.getElementById('writeReviewContent').value.trim(),  
			    recipe_id: Number(recipeId)                                           
			};


            const response = await instance.post('/reviews', data, {
                headers: getAuthHeaders()
            });

            if (response.data.code === 'REVIEW_CREATE_SUCCESS') {
                alertPrimary('리뷰 작성에 성공했습니다.');

                document.getElementById("writeReviewContent").value = '';
                const submitBtn = document.querySelector("#writeReviewForm button[type='submit']");
                submitBtn.disabled = true;
                submitBtn.classList.add('disable');

                page = 0;
                reviewList = [];
                fetchReviewList();
            }
        } catch (error) {
            const code = error?.response?.data?.code;

            if (code === 'REVIEW_CREATE_FAIL') {
                alertDanger('리뷰 작성에 실패했습니다.');
            } else if (code === 'REVIEW_INVALID_INPUT') {
                alertDanger('입력값이 유효하지 않습니다.');
            } else if (code === 'USER_INVALID_INPUT') {
                alertDanger('입력값이 유효하지 않습니다.');
            } else if (code === 'REVIEW_UNAUTHORIZED') {
                alert('로그인되지 않은 사용자입니다.');
            } else if (code === 'REVIEW_FORBIDDEN') {
                alert('접근 권한이 없습니다.');
            } else if (code === 'REVIEW_NOT_FOUND') {
                alert('해당 리뷰를 찾을 수 없습니다.');
            } else if (code === 'USER_NOT_FOUND') {
                alert('해당 사용자를 찾을 수 없습니다.');
            } else if (code === 'INTERNAL_SERVER_ERROR') {
                alert('서버 내부에서 오류가 발생했습니다.');
            } else {
                console.log(error);
            }
        }
    });
});




/**
 * 리뷰를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const editForm = document.getElementById('editReviewForm');
	editForm.addEventListener('submit', async function(event) {
		event.preventDefault();

		const id = document.getElementById('editReviewId').value;  // 리뷰 ID (hidden input)
		const content = document.getElementById('editReviewContent').value.trim(); // 수정할 리뷰 내용
		const score = Number(document.getElementById('editReviewStar').value || 0); // 수정할 별점 (hidden input)

		try {
			const data = {
				id: id,
				content: content,
				score: score
			};
			
			const response = await instance.patch(`/reviews/${id}`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'REVIEW_UPDATE_SUCCESS') {
				alertPrimary('리뷰가 성공적으로 수정되었습니다.');
				
				// 리뷰 내용 업데이트
				if (document.querySelector(`.review[data-id='${id}'] .review_content`)) {
					document.querySelector(`.review[data-id='${id}'] .review_content`).textContent = response.data.data.content;
				}

				// 리뷰 별점 업데이트 (⭐ 아이콘 변경)
				const starContainer = document.querySelector(`.review[data-id='${id}'] .star`);
				if (starContainer) {
				    const starImgs = starContainer.querySelectorAll('img');
				    starImgs.forEach((starImg, index) => {
				        starImg.src = index < response.data.data.score
				            ? "/images/recipe/star_full.png"
				            : "/images/recipe/star_empty.png";
				    });
				}
				
				// 별점 개수 업데이트
				const scoreP = document.querySelector(`.review[data-id='${id}'] p`);
				if (scoreP) scoreP.textContent = response.data.data.score;
				
				
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'REVIEW_UPDATE_FAIL') {
				alertDanger('리뷰 수정에 실패했습니다.');
			}	
			else if (code === 'REVIEW_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'REVIEW_UNAUTHORIZED_ACCESS') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'REVIEW_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'REVIEW_NOT_FOUND') {
				alert('해당 리뷰를 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alert('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
		
		bootstrap.Modal.getInstance(document.getElementById('editReviewModal'))?.hide();
	});
});

















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






