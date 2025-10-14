/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	fetchAllWithdrawList();
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin('/');
	}
	
});


/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let totalElements = 0;
let withdrawList = [];

/**
 * 서버에서 모든 사용자의 출금 내역 데이터를 가져오는 함수 (관리자용)
 */
async function fetchAllWithdrawList() {
    try {
        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/admin/withdraw?${params}`);

        page = response.data.data.number;
        totalPages = response.data.data.totalPages;
        totalElements = response.data.data.totalElements;

        withdrawList = response.data.data.content;

        console.log("총 출금 내역:", totalElements);
        console.log(withdrawList);
		renderAllWithdrawList(withdrawList);
		

    } catch (error) {
        console.error("출금 내역 조회 실패:", error);
    }
}



/**
 * 화면에 모든 사용자의 출금 내역 데이터를 출력하는 함수 (관리자용)
 */
function renderAllWithdrawList(withdrawList) {
    const tbody = document.querySelector('.guide_list');
    tbody.innerHTML = '';

    if (!withdrawList || withdrawList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center">출금 내역이 없습니다.</td></tr>';
        return;
    }

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

        tbody.appendChild(tr);
    });
}
