
document.addEventListener('DOMContentLoaded', function() {
	document.querySelector('.btn_more').addEventListener('click', function() {
		page = page + 1;
		fetchReviewList();
	});
	fetchReviewList();
});

let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let reviewList = [];

/**
 * 서버에서 리뷰 목록 데이터를 가져오는 함수
 */
async function fetchReviewList() {
	
	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const params = new URLSearchParams({
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/reviews?${params}`);
		const result = response.data; 
		
		if (result.code === 'USER_READ_LIST_SUCCESS') {
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			reviewList = [...reviewList, ...response.data.data.content];

            renderReviewList(reviewList);
            document.querySelector('.myreview_count span').innerText = result.data.totalElements + '개';
			
			const btnMore = document.querySelector('.btn_more');
			btnMore.style.display = (reviewList.length < totalElements) ? 'block' : 'none';

        } 
		else if (result.code === 'REVIEW_READ_LIST_FAIL') {
            alertDanger('리뷰 목록 조회에 실패했습니다.');
        }
		else if (result.code === 'USER_INVALID_INPUT') {
            alertDanger('입력값이 유효하지 않습니다.');
        }
        else if (result.code === 'USER_UNAUTHORIZED_ACCESS') {
            alertDanger('로그인되지 않은 사용자입니다.');
        }
        else if (result.code === 'USER_FORBIDDEN') {
            alertDanger('접근 권한이 없습니다.');
        }
		else if (code === 'INTERNAL_SERVER_ERROR') {
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
 * @param {Array} reviewList - 서버에서 받은 리뷰 목록
 */
function renderReviewList(reviews) {

    const container = document.querySelector('.myreview_list');
	container.innerHTML = '';
	
	// 작성한 리뷰 목록이 존재하지 않는 경우
	if (reviews == null || reviews.length === 0) {
		container.style.display = 'none';
		document.querySelector('.list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '작성한 리뷰가 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	
	// 작성한 리뷰 목록이 존재하는 경우
    reviews.forEach(review => {
        const li = document.createElement('li');

        // 리뷰 제목
        const titleDiv = document.createElement('div');
        titleDiv.className = 'myreview_title';
        const a = document.createElement('a');
        a.href = `/recipe/viewRecipe.do?id=${review.recipe_id}`;
        a.textContent = review.title;
        titleDiv.appendChild(a);

        // 리뷰 내용
        const reviewDiv = document.createElement('div');
        reviewDiv.className = 'myreview';
		reviewDiv.dataset.id = review.id; 

        // 작성자 정보
        const avatar = document.createElement('img');
        avatar.className = 'review_avatar';
        avatar.src = review.image || '/images/common/test.png';

        const innerDiv = document.createElement('div');
        innerDiv.className = 'review_inner';

        const infoDiv = document.createElement('div');
        infoDiv.className = 'review_info';

        const writerDiv = document.createElement('div');
        writerDiv.className = 'review_writer';
        const nameSpan = document.createElement('span');
        nameSpan.textContent = review.nickname;
        const dateSpan = document.createElement('span');
        dateSpan.textContent = formatDate(review.postdate);

        writerDiv.appendChild(nameSpan);
        writerDiv.appendChild(dateSpan);

        const actionDiv = document.createElement('div');
        actionDiv.className = 'review_action';
		
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
        deleteLink.addEventListener('click', async () => {
            await deleteReview(review.id);
        });

        actionDiv.append(editLink, deleteLink);

        infoDiv.appendChild(writerDiv);
        infoDiv.appendChild(actionDiv);

        // 별점
        const scoreDiv = document.createElement('div');
        scoreDiv.className = 'review_score';
        const starDiv = document.createElement('div');
        starDiv.className = 'star';
        for (let i = 0; i < 5; i++) {
            const starImg = document.createElement('img');
            starImg.src = i < review.score ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
            starDiv.appendChild(starImg);
        }
        const scoreP = document.createElement('p');
        scoreP.textContent = review.score;

        scoreDiv.appendChild(starDiv);
        scoreDiv.appendChild(scoreP);

        // 리뷰 내용
        const contentP = document.createElement('p');
        contentP.className = 'review_content';
        contentP.textContent = review.content;

        innerDiv.appendChild(infoDiv);
        innerDiv.appendChild(scoreDiv);
        innerDiv.appendChild(contentP);

        reviewDiv.appendChild(avatar);
        reviewDiv.appendChild(innerDiv);

        li.appendChild(titleDiv);
        li.appendChild(reviewDiv);

        container.appendChild(li);
    });
}





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
				const reviewContentEl = document.querySelector(`.myreview[data-id='${id}'] .review_content`);
				if (reviewContentEl) {
				    reviewContentEl.textContent = response.data.data.content;
				}


				// 리뷰 별점 업데이트 (⭐ 아이콘 변경)
				const starContainer = document.querySelector(`.myreview[data-id='${id}'] .star`);
				if (starContainer) {
				    const starImgs = starContainer.querySelectorAll('img');
				    starImgs.forEach((starImg, index) => {
				        starImg.src = index < response.data.data.score
				            ? "/images/recipe/star_full.png"
				            : "/images/recipe/star_empty.png";
				    });
				}
				
				// 별점 개수 업데이트
				const scoreP = document.querySelector(`.myreview[data-id='${id}'] p`);
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
			   reviewList = reviewList.filter(review => review.id !== id);
			   renderReviewList(reviewList);
			   
			   totalElements--;
			   document.querySelector('.myreview_count span').textContent = totalElements + '개';
			   
		       const btnMore = document.querySelector('.btn_more');
		       if (reviewList.length < size || reviewList.length >= totalElements) {
		           btnMore.style.display = 'none';
		       } else {
		           btnMore.style.display = 'block';
		       }
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
 * 리뷰 폼 값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
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
	
});



