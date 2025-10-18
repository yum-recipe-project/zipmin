/**
 * 전역 변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 10;
let keyword = '';
let category = '';
let sortKey = 'id';
let sortOrder = 'desc';
let withdrawList = [];





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin('/');
	}
	
});




/**
 * 출금 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	fetchAdminWithdrawList();
	
});





/**
 * 서버에서 모든 사용자의 출금 내역 데이터를 가져오는 함수 (관리자용)
 */
async function fetchAdminWithdrawList(scrollTop = true) {
    try {
        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/admin/withdraw?${params}`);

		if (response.data.code === 'WITHDRAW_READ_LIST_SUCCESS') {
	        // 전역변수 설정
	        totalPages = response.data.data.totalPages;
			page = response.data.data.number;
	        withdrawList = response.data.data.content;
	        totalElements = response.data.data.totalElements;
	
			// 렌더링
			renderAdminWithdrawList(withdrawList);
			renderAdminPagination(fetchAdminWithdrawList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
    }
	catch (error) {
		const code = error?.response?.data?.code;
		
        console.error(error);
    }
}





/**
 * 출금 내역 목록을 화면에 렌더링하는 함수
 */
function renderAdminWithdrawList(withdrawList) {
	
    const container = document.querySelector('.guide_list');
    container.innerHTML = '';

	// 출금 목록이 존재하지 않는 경우
	if (withdrawList == null || withdrawList.length === 0) {
		document.querySelector('.table_th').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.fixed-table').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}

	// 출금 목록이 존재하는 경우
	document.querySelector('.search_empty')?.remove();
	document.querySelector('.table_th').style.display = '';

    withdrawList.forEach((withdraw, index) => {
        const tr = document.createElement('tr');

        // No
        const tdNo = document.createElement('td');
        tdNo.textContent = index + 1 + page * size; // 페이징 적용
        tr.appendChild(tdNo);

        // 출금 요청자 (username / userId)
        const tdUser = document.createElement('td');
        tdUser.textContent = `${withdraw.username} (id: ${withdraw.user_id})`;
        tr.appendChild(tdUser);

        // 출금 요청일
        const tdRequestDate = document.createElement('td');
        tdRequestDate.textContent = formatDate(withdraw.request_date);
        tr.appendChild(tdRequestDate);

        // 요청 금액
        const tdRequestPoint = document.createElement('td');
        tdRequestPoint.textContent = `${withdraw.request_point?.toLocaleString() || 0}원`;
        tr.appendChild(tdRequestPoint);

        // 승인 여부
        const tdStatus = document.createElement('td');
        if (withdraw.status === 1) {
            tdStatus.innerHTML = `<span class="badge bg-success">출금 완료</span>`;
        } else {
            tdStatus.innerHTML = `<span class="badge bg-warning text-dark">출금 대기</span>`;
        }
        tr.appendChild(tdStatus);

        // 기타 (비워두거나 필요 시 버튼 등 추가)
        const tdEtc = document.createElement('td');
        tdEtc.textContent = '-';
        tr.appendChild(tdEtc);

        // 관리 (예: 승인/취소 버튼 등)
//        const tdManage = document.createElement('td');
//        tdManage.innerHTML = `<button class="btn btn-sm btn-primary">상세</button>`;
//        tr.appendChild(tdManage);

        container.appendChild(tr);
    });
}
