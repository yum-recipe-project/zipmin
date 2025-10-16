/**
 * 전역 변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let keyword = '';
let reviewList = [];

let sortKey = 'id';    
let sortOrder = 'desc';





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin();
	}
	
});







/**
 * 리뷰 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('.search').addEventListener('submit', function(e) {
	    e.preventDefault();
	    keyword = document.getElementById('text-srh').value.trim();
	    page = 0;        
	    reviewList = [];
	    fetchAdminReviewList();
	});
	
	// 정렬
	document.querySelectorAll('.sort_btn').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			const key = btn.dataset.key;

			if (sortKey === key) {
				sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
			}
			else {
				sortKey = key;
				sortOrder = 'desc';
			}

			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			btn.classList.add(sortOrder);

			page = 0;
			fetchAdminReviewList();
		});
	});
	
	
	
	fetchAdminReviewList();
});





/**
 * 서버에서 전체 리뷰 목록 데이터를 가져오는 함수 (관리자용)
 */
async function fetchAdminReviewList() {
	
    try {
        const params = new URLSearchParams({
			keyword: keyword,
			sort: sortKey + '-' + sortOrder,
            page: page,
            size: size
        }).toString();
		
        const response = await instance.get(`/admin/reviews?${params}`, {
            headers: getAuthHeaders() 
        });
		
		console.log(response);
		
		if (response.data.code === 'REVIEW_READ_LIST_SUCCESS') {
			
			// 전역 변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			reviewList = response.data.data.content;
			
			// 렌더링
			renderAdminReviewList(reviewList);
			renderAdminPagination(fetchAdminReviewList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
    }
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'REVIEW_READ_LIST_FAIL') {
			alertDanger('사용자 목록 조회에 실패했습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'AUTH_TOKEN_INVALID') {
			redirectToAdminLogin();
		}
		else if (code === 'USER_FORBIDDEN') {
			redirectToAdminLogin();
		}
		else if (code === 'USER_NOT_FOUND') {
			redirectToAdminLogin();
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
    }
	
}



/**
 * 관리자 리뷰 목록을 화면에 렌더링하는 함수
 */
function renderAdminReviewList(reviewList) {
    const tableBody = document.querySelector('.review_list');
    tableBody.innerHTML = ''; 

    if (!reviewList || reviewList.length === 0) {
        const tr = document.createElement('tr');
        const td = document.createElement('td');
        td.colSpan = 8;
        td.className = 'text-center py-4';
        td.textContent = '등록된 리뷰가 없습니다.';
        tr.appendChild(td);
        tableBody.appendChild(tr);
        return;
    }

    const token = localStorage.getItem('accessToken');
    const payload = parseJwt(token);

    reviewList.forEach((review, index) => {
        const tr = document.createElement('tr');
        tr.dataset.id = review.id;

        // 번호
        const noTd = document.createElement('td');
        const noH6 = document.createElement('h6');
        noH6.className = 'fw-semibold mb-0';
        noH6.textContent = index + 1 + page * size;
        noTd.appendChild(noH6);

        // 별점
        const scoreTd = document.createElement('td');
        const scoreH6 = document.createElement('h6');
        scoreH6.className = 'fw-semibold mb-0';
        scoreH6.textContent = review.score ?? 0;
        scoreTd.appendChild(scoreH6);

        // 내용
        const contentTd = document.createElement('td');
        const contentH6 = document.createElement('h6');
        contentH6.className = 'fw-semibold mb-0 text-truncate';
        contentH6.title = review.content || '-';
        contentH6.textContent = review.content || '-';
        contentTd.appendChild(contentH6);

        // 작성자
        const writerTd = document.createElement('td');
        const writerH6 = document.createElement('h6');
        writerH6.className = 'fw-semibold mb-0';
        writerH6.textContent = review.nickname || review.username || '-';
        writerTd.appendChild(writerH6);

        // 작성일
        const dateTd = document.createElement('td');
        const dateH6 = document.createElement('h6');
        dateH6.className = 'fw-semibold mb-0';
        dateH6.textContent = formatDate(review.postdate);
        dateTd.appendChild(dateH6);

        // 좋아요
        const likeTd = document.createElement('td');
        const likeH6 = document.createElement('h6');
        likeH6.className = 'fw-semibold mb-0';
        likeH6.textContent = review.likecount ?? 0;
        likeTd.appendChild(likeH6);

        // 신고수
        const reportTd = document.createElement('td');
        const reportH6 = document.createElement('h6');
        reportH6.className = 'fw-semibold mb-0';
        reportH6.textContent = review.reportcount ?? 0;
        reportTd.appendChild(reportH6);

        // 기능 버튼
        const actionTd = document.createElement('td');
        actionTd.className = 'text-end';
        const btnWrap = document.createElement('div');
        btnWrap.className = 'd-flex justify-content-end gap-2';

        const canAction =
            payload.role === 'ROLE_SUPER_ADMIN' ||
            (payload.role === 'ROLE_ADMIN' && review.role === 'ROLE_USER') ||
            (payload.id === review.user_id);

        if (canAction) {
            // 수정 버튼
            const editBtn = document.createElement('button');
            editBtn.type = 'button';
            editBtn.className = 'btn btn-sm btn-outline-info';
            editBtn.dataset.bsToggle = 'modal';
			editBtn.textContent = '수정';
            editBtn.dataset.bsTarget = '#editReviewModal';
			editBtn.addEventListener('click', () => {
				// 리뷰 ID 설정
			    document.getElementById('editReviewId').value = review.id;
			    document.getElementById('editReviewContent').value = review.content;
			
			    // 별점 설정 (별점 이미지와 hidden input 초기화)
			    const starInput = document.getElementById('editReviewStar'); // hidden input
			    starInput.value = review.score ?? 0; // 점수가 없으면 0
			
			    const editReviewStarGroup = document.querySelectorAll('#editReviewStarGroup .star');
			    editReviewStarGroup.forEach(star => {
			        const value = Number(star.getAttribute('data-value'));
			        star.src = value <= (review.score ?? 0)
			            ? '/images/common/star_full.png'
			            : '/images/common/star_outline.png';
			    });
			});
			
            // 삭제 버튼
            const deleteBtn = document.createElement('button');
            deleteBtn.type = 'button';
            deleteBtn.className = 'btn btn-sm btn-outline-danger';
            deleteBtn.textContent = '삭제';
            deleteBtn.addEventListener('click', () => deleteReview(review.id));

            btnWrap.append(editBtn, deleteBtn);
        }
        actionTd.appendChild(btnWrap);

        tr.append(noTd, scoreTd, contentTd, writerTd, dateTd, likeTd, reportTd, actionTd);
        tableBody.appendChild(tr);
    });
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
				await fetchAdminReviewList();
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
				await fetchAdminReviewList();

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





// 말줄임 클릭 시 펼침
document.addEventListener('click', e => {
	if (e.target.classList.contains('text-truncate')) {
		e.target.classList.toggle('expanded');
	}
});
