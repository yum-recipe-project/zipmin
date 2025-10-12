document.addEventListener("DOMContentLoaded", function() {
    fetchWithdrawList();
});

/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let withdrawList = [];
let totalElements = 0;


/**
 * 서버에서 출금 내역 목록 데이터를 가져오는 함수
 */
async function fetchWithdrawList() {
    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;
        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/users/${id}/withdraw?${params}`);

        page = response.data.data.number;
        totalPages = response.data.data.totalPages;
        totalElements = response.data.data.totalElements;

        withdrawList = response.data.data.content;
        renderWithdrawList(withdrawList);
        renderPagination();

        document.querySelector('.withdraw_util  .total').innerText = `총 ${response.data.data.totalElements}건`;

    } catch (error) {
        console.log(error);
    }
}


/**
 * 화면에 출금 내역 목록 데이터를 출력하는 함수
 */
function renderWithdrawList(withdrawList) {
    const container = document.querySelector('.withdraw_list');
    container.innerHTML = '';

    if (!withdrawList || withdrawList.length === 0) {
        container.innerHTML = '<p class="empty">아직 출금 내역이 없습니다.</p>';
        return;
    }

    withdrawList.forEach(withdraw => {
        const li = document.createElement('li');

        const dateDiv = document.createElement('div');
        dateDiv.className = 'withdraw_info';
        dateDiv.innerHTML = `
            <div class="request_date">
                <p>${formatDate(withdraw.request_date)}</p>
            </div>
        `;

        const pointDiv = document.createElement('div');
        pointDiv.className = 'withdraw_info';
        pointDiv.innerHTML = `
            <div class="request_point">
                <p>신청 금액</p>
                <p>${withdraw.request_point ? withdraw.request_point.toLocaleString() : 0}원</p>
            </div>
        `;

        const statusDivWrapper = document.createElement('div');
        statusDivWrapper.className = 'withdraw_info';
        let statusDivHtml = '';

        if (withdraw.status === 1) { // 출금 완료
            statusDivHtml = `
                <div class="withdraw_status">
                    <p class="complete">출금 완료</p>
                    <p>${withdraw.complete_date ? `입금일 ${formatDate(withdraw.complete_date)}` : '입금일 정보 없음'}</p>
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

