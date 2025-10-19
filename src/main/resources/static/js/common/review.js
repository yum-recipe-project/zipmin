/**
 * 전역변수
 */
const reviewSize = 15;
let reviewTotalPages = 0;
let reviewTotalElements = 0;
let reviewPage = 0;
let reviewSort = '';
let reviewList = [];





/**
 * 로그인 여부에 따라 리뷰 작성폼을 다르게 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', async function () {
	
	if (isLoggedIn()) {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		document.getElementById('reviewLoginState').style.display = 'block';
		document.getElementById('reviewLogoutState').style.display = 'none';
		document.getElementById('writeReviewAvatar').src = payload.avatar;
		document.getElementById('writeReviewAvatar').onclick = () => { location.href = `/mypage/profile.do?id=${payload.id}` };
		document.getElementById('writeReviewNickname').innerText = payload.nickname;
		document.getElementById('writeReviewNickname').onclick = () => { location.href = `/mypage/profile.do?id=${payload.id}` };
	}
	else {
		document.getElementById('reviewLoginState').style.display = 'none';
		document.getElementById('reviewLogoutState').style.display = 'block';
	}

});





/**
 * 리뷰 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	const wrap = document.getElementById('reviewWrap');

    // 정렬 버튼
    wrap.querySelectorAll('.review_order .btn_sort_small').forEach(btn => {
        btn.addEventListener('click', function (event) {
            event.preventDefault();
            wrap.querySelector('.review_order .btn_sort_small.active')?.classList.remove('active');
            btn.classList.add('active');

            reviewSort = btn.dataset.sort;
            reviewPage = 0;
            reviewList = [];

            fetchReviewList();
        });
    });

	// 더보기 기능
    wrap.querySelector('.btn_more').addEventListener('click', function () {
        reviewPage = reviewPage + 1;
        fetchReviewList();
    });
	
	fetchReviewList();
});





/**
 * 리뷰 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 리뷰를 작성하는 폼값 검증 및 포커스
	const writeReviewContent = document.getElementById("writeReviewContent");
	const reviewContentBorder = document.querySelector('.review_input');
	const writeReviewButton = document.querySelector('#writeReviewForm button[type="submit"]');
	writeReviewContent.addEventListener('focus', function() {
		reviewContentBorder.classList.add('focus');
	});
	writeReviewContent.addEventListener('blur', function() {
		reviewContentBorder.classList.remove('focus');
	});
	writeReviewContent.addEventListener('input', function() {
		const isReviewCommentEmpty = writeReviewContent.value.trim() === "";
		writeReviewButton.classList.toggle('disable', isReviewCommentEmpty);
		writeReviewButton.disabled = isReviewCommentEmpty;
	});
	
	// 리뷰를 작성하는 폼의 별점 선택
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
	
	// 리뷰를 수정하는 모달창의 폼값을 검증
	const editReviewContent = document.getElementById("editReviewContent");
	const editReviewButton = document.querySelector("#editReviewForm button[type='submit']");
	editReviewContent.addEventListener("input", function() {
		const isReviewContentEmpty = editReviewContent.value.trim() === "";
		editReviewButton.classList.toggle("disabled", isReviewContentEmpty);
	});
	
	// 리뷰를 수정하는 폼의 별점 선택
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
	
	// 리뷰를 신고하는 모달창의 폼값 검증
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
 * 서버에서 리뷰 목록 데이터를 가져오는 함수
 */
async function fetchReviewList() {
	
	const wrap = document.getElementById('reviewWrap');
	
    try {
        const id = new URLSearchParams(window.location.search).get('id');

        const params = new URLSearchParams({
            recodenum: id,
            sort: reviewSort,
            page: reviewPage,
            size: reviewSize
        }).toString();
		
        const response = await fetch(`/reviews?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
        });
		
        const result = await response.json();
		
        if (result.code === 'REVIEW_READ_LIST_SUCCESS') {
            // 전역 변수 설정
            reviewTotalPages = result.data.totalPages;
            reviewTotalElements = result.data.totalElements;
            reviewList = [...reviewList, ...result.data.content];

			// 렌더링
            renderReviewList(reviewList);
            document.querySelector('.review_count span:last-of-type').innerText = reviewTotalElements;
			if (document.querySelector('#viewRecipeReviewCommentWrap .review_count')) {
				document.querySelector('#viewRecipeReviewCommentWrap .review_count').innerText = reviewTotalElements;
			}
            wrap.querySelector('.btn_more').style.display = reviewPage >= reviewTotalPages - 1 ? 'none' : 'block';
        }
        else if (result.code === 'REVIEW_READ_LIST_FAIL') {
            alertDanger('리뷰 목록 조회에 실패했습니다.');
        }
		else if (result.code === 'INTERVAL_SERVER_ERROR') {
			alert('서버 내부에서 오류가 발생했습니다.');
		}
		else if (result.code === 'LIKE_COUNT_FAIL') {
		    alertDanger('리뷰 좋아요 수 조회에 실패했습니다.');
		}
		else if (result.code === 'REPORT_COUNT_FAIL') {
		    alertDanger('리뷰 신고 수 조회에 실패했습니다.');
		}
		else if (result.code === 'LIKE_EXIST_FAIL') {
		    alertDanger('리뷰 좋아요 여부 조회에 실패했습니다.');
		}
		else if (result.code === 'REVIEW_INVALID_INPUT') {
		    alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_INVALID_INPUT') {
		    alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
		    alert('해당 사용자를 찾을 수 없습니다.');
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
		    alert('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
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
	
	const wrap = document.getElementById('reviewWrap');
    const container = wrap.querySelector('.review_list');
    container.innerHTML = ''; 

	// 리뷰 목록이 존재하지 않는 경우
	if (reviewList == null || reviewList.length === 0) {
		container.style.display = 'block';
		wrap.querySelector('.list_empty')?.remove();
		
		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '리뷰가 없습니다';
		wrapper.appendChild(span);
		wrap.querySelector('.review_write').insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 리뷰 목록이 존재하는 경우
	container.style.display = 'block';
	wrap.querySelector('.list_empty')?.remove();
	
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
        const avatarImg = document.createElement('img');
        avatarImg.className = 'review_avatar';
        avatarImg.src = review.avatar;
		avatarImg.onclick = () => { location.href = `/mypage/profile.do?id=${review.user_id}`; }
        const nameSpan = document.createElement('span');
		nameSpan.onclick = () => { location.href = `/mypage/profile.do?id=${review.user_id}`; }
        nameSpan.textContent = review.nickname;
		
        const dateSpan = document.createElement('span');
        dateSpan.textContent = formatDate(review.postdate);
        writerDiv.append(avatarImg, nameSpan, dateSpan);

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

		// 신고 버튼 (본인 리뷰인 경우 숨김)
       if (!(payload?.id === review.user_id)) {
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
       }

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

		// 좋아요 버튼 동작
		likeBtn.addEventListener('click', async function() {
			
			if (!isLoggedIn()) {
				redirectToLogin();
				return;
			}
			
			// 좋아요 취소
			if (isLiked) {
				try {
					const data = {
						tablename: 'review',
						recodenum: id,
					}
					
					const response = await instance.delete(`/reviews/${id}/likes`, {
						data: data,
						headers: getAuthHeaders()
					});
					
					if (response.data.code === 'REVIEW_UNLIKE_SUCCESS') {
						alertPrimary('리뷰 좋아요 취소에 성공했습니다.');
						isLiked = false;
						img.src = '/images/common/thumb_up_empty.png';
						likeCountP.textContent = Number(likeCountP.textContent) - 1;
					}
				}
				catch (error) {
					const code = error?.response?.data?.code;
					
					if (code === 'REVIEW_UNLIKE_FAIL') {
						alertDanger('댓글 좋아요 취소에 실패했습니다.');
					}
					else if (code === 'LIKE_DELETE_FAIL') {
						alertDanger('좋아요 삭제에 실패했습니다');
					}
					else if (code === 'REVIEW_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'USER_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'LIKE_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'REVIEW_UNAUTHORIZED_ACCESS') {
						alertDanger('로그인되지 않은 사용자입니다.');
					}
					else if (code === 'LIKE_FORBIDDEN') {
						alertDanger('접근 권한이 없습니다.');
					}
					else if (code === 'LIKE_NOT_FOUND') {
						alertDanger('해당 좋아요를 찾을 수 없습니다');
					}
					else if (code === 'REVIEW_NOT_FOUND') {
						alertDanger('해당 댓글을 찾을 수 없습니다.');
					}
					else if (code === 'INTERNAL_SERVER_ERROR') {
						alertDanger('서버 내부에서 오류가 발생했습니다.');
					}
					else {
						console.log(error);
					}
				}
			}
			
			// 좋아요
			else {
				try {
					const data = {
						tablename: 'review',
						recodenum: id
					}
					
					const response = await instance.post(`/reviews/${id}/likes`, data, {
						headers: getAuthHeaders()
					});
					
					if (response.data.code === 'REVIEW_LIKE_SUCCESS') {
						alertPrimary('리뷰 좋아요에 성공했습니다.');
						isLiked = true;
						img.src = '/images/common/thumb_up_full.png';
						likeCountP.textContent = Number(likeCountP.textContent) + 1;
					}
				}
				catch (error) {
					const code = error?.response?.data?.code;
					
					if (code === 'REVIEW_LIKE_FAIL') {
						alertDanger('댓글 좋아요에 실패했습니다.');
					}
					else if (code === 'LIKE_CREATE_FAIL') {
						alertDanger('좋아요 작성에 실패했습니다.');
					}
					else if (code === 'REVIEW_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'USER_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'LIKE_INVALID_INPUT') {
						alertDanger('입력값이 유효하지 않습니다.');
					}
					else if (code === 'REVIEW_UNAUTHORIZED_ACCESS') {
						alertDanger('로그인되지 않은 사용자입니다.');
					}
					else if (code === 'LIKE_FORBIDDEN') {
						alertDanger('접근 권한이 없습니다.');
					}
					else if (code === 'REVIEW_NOT_FOUND') {
						alertDanger('해당 댓글을 찾을 수 없습니다.');
					}
					else if (code === 'USER_NOT_FOUND') {
						alertDanger('해당 사용자를 찾을 수 없습니다.');
					}
					else if (code === 'LIKE_DUPLICATE') {
						alertDanger('이미 좋아요한 댓글입니다.');
					}
					else if (code === 'INTERNAL_SERVER_ERROR') {
						alertDanger('서버 내부에서 오류가 발생했습니다.');
					}
					else {
						console.log(error);
					}
				}
			}
		});

	   return likeBtn;
}






/**
 * 리뷰를 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	const form = document.getElementById('writeReviewForm');
    form.addEventListener('submit', async function (event) {
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

                document.getElementById('writeReviewContent').value = '';
                const submitBtn = form.querySelector('button[type="submit"]');
                submitBtn.disabled = true;
                submitBtn.classList.add('disable');

                reviewPage = 0;
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

		const id = document.getElementById('editReviewId').value;
		const content = document.getElementById('editReviewContent').value.trim();
		const score = Number(document.getElementById('editReviewStar').value || 0);

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

				// 리뷰 별점 업데이트 (아이콘 변경)
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
				
				// reviewList의 해당 리뷰 데이터 업데이트
				const updatedIndex = reviewList.findIndex(r => r.id == id);
				if (updatedIndex !== -1) {
				    reviewList[updatedIndex].content = response.data.data.content;
				    reviewList[updatedIndex].score = response.data.data.score;
				}

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





/**
 * 리뷰를 삭제하는 함수
 */
async function deleteReview(id) {

    if (confirm('작성하신 리뷰를 삭제하시겠습니까?')) {
        try {
            const response = await instance.delete(`/reviews/${id}`, {
                headers: getAuthHeaders()
            });

            if (response.data.code === 'REVIEW_DELETE_SUCCESS') {
                alertPrimary('리뷰를 성공적으로 삭제했습니다.');

                document.querySelector(`.review[data-id='${id}']`)?.remove();
				reviewTotalElements--;
				document.querySelector('.review_count span:last-of-type').innerText = reviewTotalElements;
				if (document.querySelector('#viewRecipeReviewCommentWrap .review_count')) {
					document.querySelector('#viewRecipeReviewCommentWrap .review_count').innerText = reviewTotalElements;
			}
                reviewList = reviewList.filter(review => review.id !== id);
            }

        } catch (error) {
            const code = error?.response?.data?.code;

            if (code === 'REVIEW_DELETE_FAIL') {
                alertDanger('리뷰 삭제에 실패했습니다.');
            }
            else if (code === 'REVIEW_INVALID_INPUT') {
                alertDanger('입력값이 유효하지 않습니다.');
            }
            else if (code === 'USER_INVALID_INPUT') {
                alertDanger('입력값이 유효하지 않습니다.');
            }
            else if (code === 'REVIEW_UNAUTHORIZED_ACCESS') {
                alertDanger('로그인되지 않은 사용자입니다.');
            }
            else if (code === 'REVIEW_FORBIDDEN') {
                alertDanger('접근 권한이 없습니다.');
            }
            else if (code === 'REVIEW_NOT_FOUND') {
                alertDanger('해당 리뷰를 찾을 수 없습니다.');
            }
            else if (code === 'USER_NOT_FOUND') {
                alertDanger('해당 사용자를 찾을 수 없습니다.');
            }
            else if (code === 'INTERNAL_SERVER_ERROR') {
                alertDanger('서버 내부에서 오류가 발생했습니다.');
            }
            else {
                console.log(error);
            }
        }
    }

}





/**
 * 리뷰를 신고하는 함수 
 */
document.addEventListener('DOMContentLoaded', function() {
	const reportForm = document.getElementById('reportReviewForm');
	
	reportForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}
		
		const reviewId = document.getElementById('reportReviewId').value;
		const reason = document.querySelector('input[name="reason"]:checked')?.value;
		
		console.log("reason:"+ reason);
		
		try {
			const data = {
				tablename: 'review',
				recodenum: reviewId,
				reason: reason
			};
			
			const response = await instance.post(`/reviews/${reviewId}/reports`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'REVIEW_REPORT_SUCCESS') {
				alertPrimary('신고 처리되었습니다.');
			}
			
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'REVIEW_REPORT_FAIL') {
				alertDanger('리뷰 신고에 실패했습니다.');
			}
			else if (code === 'REPORT_CREATE_FAIL') {
				alertDanger('신고 작성에 실패했습니다.');
			}
			else if (code === 'REVIEW_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'REPORT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'REVIEW_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'REPORT_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'REVIEW_NOT_FOUND') {
				alertDanger('해당 리뷰를 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'REPORT_DUPLICATE') {
				alertDanger('이미 신고한 리뷰입니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
		
		bootstrap.Modal.getInstance(document.getElementById('reportReviewModal'))?.hide();
	});
});