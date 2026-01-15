/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let totalElements = 0;
let withdrawList = [];





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/');
	}
	
});





/**
 * 초기 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    fetchWithdrawList();
});





/**
 * 서버에서 사용자의 출금 목록 데이터를 가져오는 함수
 */
async function fetchWithdrawList(scroll = true) {
	
    try {
        const payload = parseJwt(localStorage.getItem('accessToken'));
		
        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/users/${payload.id}/withdraws?${params}`, {
			headers: getAuthHeaders()
		});

		if (response.data.code === 'WITHDRAW_READ_LIST_SUCCESS') {
			
	        page = response.data.data.number;
	        totalPages = response.data.data.totalPages;
	        totalElements = response.data.data.totalElements;
	        withdrawList = response.data.data.content;
			
	        renderWithdrawList(withdrawList);
	        renderPagination(withdrawList);
	        document.querySelector('.total').innerText = `총 ${totalElements}건`;
			
			if (scroll) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}

    }
	catch (error) {
		const code = error?.response?.data?.code;

		if (code === 'WITHDRAW_READ_LIST_FAIL') {
			console.log(error);
		}
		else if (code === 'WITHDRAW_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'WITHDRAW_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'WITHDRAW_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
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
 * 화면에 출금 내역 목록 데이터를 출력하는 함수
 */
function renderWithdrawList(withdrawList) {
	
    const container = document.querySelector('.withdraw_list');
    container.innerHTML = '';
	
	// 출금 목록이 존재하지 않는 경우
    if (withdrawList == null || withdrawList.length === 0) {
		container.style.display = 'none';
		document.querySelector('.list_empty')?.remove();
	
		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';
	
		const span = document.createElement('span');
		span.textContent = '출금 내역이 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);
	
		return;
    }

	// 출금 목록이 존재하는 경우
	container.style.display = 'block';
	document.querySelector('.list_empty')?.remove();
	
    withdrawList.forEach(withdraw => {
		
        const li = document.createElement('li');

        const dateDiv = document.createElement('div');
        dateDiv.className = 'withdraw_info';
        dateDiv.innerHTML = `
            <div class="request_date">
                <p>${formatDate(withdraw.claimdate)}</p>
            </div>
        `;

        const pointDiv = document.createElement('div');
        pointDiv.className = 'withdraw_info';
        pointDiv.innerHTML = `
            <div class="request_point">
                <p>신청 금액</p>
                <p>${withdraw.point ? withdraw.point.toLocaleString() : 0}원</p>
            </div>
        `;

        const statusDivWrapper = document.createElement('div');
        statusDivWrapper.className = 'withdraw_info';
        let statusDivHtml = '';

        if (withdraw.status === 1) { // 출금 완료
            statusDivHtml = `
                <div class="withdraw_status">
                    <p class="complete">출금 완료</p>
                    <p>${withdraw.settledate ? `입금일 ${formatDate(withdraw.settledate)}` : '입금일 정보 없음'}</p>
                </div>
            `;
        } else { // 출금 대기
            statusDivHtml = `
                <div class="withdraw_status">
                    <p class="pending">출금 대기</p>
                </div>
            `;
        }

        statusDivWrapper.innerHTML = statusDivHtml;

        li.append(dateDiv, pointDiv, statusDivWrapper);
        container.appendChild(li);
    });
}

