/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	fetchAdminReviewList();
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin();
	}
	
});



let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let ReviewList = [];

/**
 * 서버에서 전체 리뷰 목록 데이터를 가져오는 함수 (관리자용)
 */
async function fetchAdminReviewList() {
    try {
        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        // ✅ 관리자용 전체 리뷰 목록 API 호출
        const response = await instance.get(`/admin/reviews?${params}`, {
            headers: getAuthHeaders() // 관리자 토큰 인증 필요 시
        });

		if (response.data.code === 'REVIEW_READ_LIST_SUCCESS') {
			
//		      const reviewPage = response.data.data;
			  // 전역 변수 설정
			  totalPages = response.data.data.totalPages;
			  totalElements = response.data.data.totalElements;
			  page = response.data.data.number;
			  ReviewList = response.data.data.content;
			
			  
		      renderAdminReviewList(ReviewList);
			  renderAdminPagination(fetchAdminReviewList);
			  document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
		}
    } catch (error) {
        console.error("전체 리뷰 목록 불러오기 실패:", error);
    }
}





/**
 * 관리자 리뷰 목록을 화면에 렌더링하는 함수
 */
function renderAdminReviewList(reviewList) {
    const tableBody = document.querySelector('.review_list');
    tableBody.innerHTML = ''; // 기존 목록 초기화

    if (!reviewList || reviewList.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="8" class="text-center py-4">등록된 리뷰가 없습니다.</td>
            </tr>`;
        return;
    }

    reviewList.forEach((review, index) => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${index + 1 + page * size}</td>
            <td class="recipe_title text-truncate" title="${review.title || '-'}">
                ${review.title || '-'}
            </td>
            <td class="review_content text-truncate" title="${review.content || '-'}">
                ${review.content || '-'}
            </td>
            <td>${review.nickname || review.username || '-'}</td>
            <td>${formatDate(review.postdate)}</td>
            <td>${review.likecount ?? 0}</td>
            <td>${review.reportcount ?? 0}</td>
            <td class="text-end">
                <button class="btn btn-sm btn-outline-danger" onclick="deleteReview(${review.id})">삭제</button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}
