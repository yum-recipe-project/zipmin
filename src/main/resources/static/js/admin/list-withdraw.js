/**
 * 전역변수
 */
let page = 0;
const size = 15;
let totalPages = 0;
let totalElements = 0;
let keyword = '';
let category = '';
let state = '';
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
 * 출금 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchAdminWithdrawList();
	});
	document.getElementById('text-srh')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchAdminWithdrawList();
		}
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab a').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab a.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			keyword = '';
			document.getElementById('text-srh').value = '';
			sortKey = 'id';
			sortOrder = 'desc';
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			document.querySelector(`.sort_btn[data-key="${sortKey}"]`).classList.add(sortOrder);
			
			withdrawList = [];
			fetchAdminWithdrawList();
		});
	});
	
	// 정렬 버튼
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
			this.classList.add(sortOrder);
			page = 0;
			fetchAdminWithdrawList();
		});
	});
	
	// 승인 상태 스위치
	document.getElementById('listWithdrawState').addEventListener('change', function() {
		state = this.checked ? 'open' : '';
		page = 0;
		fetchAdminWithdrawList();
	});
	
	fetchAdminWithdrawList();
});





/**
 * 서버에서 모든 사용자의 출금 내역 데이터를 가져오는 함수
 */
async function fetchAdminWithdrawList(scroll = true) {
	
    try {
        const params = new URLSearchParams({
			keyword: keyword,
			state: state,
			sort: sortKey + '-' + sortOrder,
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/admin/withdraws?${params}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'WITHDRAW_READ_LIST_SUCCESS') {
			
	        totalPages = response.data.data.totalPages;
			page = response.data.data.number;
	        withdrawList = response.data.data.content;
	        totalElements = response.data.data.totalElements;
	
			renderAdminWithdrawList(withdrawList);
			renderAdminPagination(fetchAdminWithdrawList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			if (scroll) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
    }
	catch (error) {
		const code = error?.response?.data?.code;
		
		// TODO : 에러코드
		
    }
	
}





/**
 * 출금 내역 목록을 화면에 렌더링하는 함수
 */
function renderAdminWithdrawList(withdrawList) {
	
    const container = document.querySelector('.withdraw_list');
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
		tr.dataset.id = withdraw.id;

        // 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		const offset = page * size + index;
		if (sortKey === 'id' && sortOrder === 'asc') {
			noH6.textContent = offset + 1;
		}
		else {
			noH6.textContent = totalElements - offset;
		}
		noTd.appendChild(noH6);

        // 아이디
		const usernameTd = document.createElement('td');
		const usernameH6 = document.createElement('h6');
		usernameH6.className = 'fw-semibold mb-0';
		usernameH6.textContent = withdraw.username;
		usernameTd.appendChild(usernameH6);
		
		// 이름
		const nameTd = document.createElement('td');
		const nameH6 = document.createElement('h6');
		nameH6.className = 'fw-semibold mb-0';
		nameH6.textContent = withdraw.name;
		nameTd.appendChild(nameH6);
		
		// 출금 금액
		const pointTd = document.createElement('td');
		const pointH6 = document.createElement('h6');
		pointH6.className = 'fw-semibold mb-0';
		pointH6.textContent = `${withdraw.point}원`;
		pointTd.appendChild(pointH6);

		// 계좌번호
		const accountnumTd = document.createElement('td');
		const accountnumH6 = document.createElement('h6');
		accountnumH6.className = 'fw-semibold mb-0';
		accountnumH6.textContent = withdraw.accountnum;
		accountnumTd.appendChild(accountnumH6);
		
        // 요청일
		const claimdateTd = document.createElement('td');
		const claimdateH6 = document.createElement('h6');
		claimdateH6.className = 'fw-semibold mb-0';
		claimdateH6.textContent = formatDateDot(withdraw.claimdate);
		claimdateTd.appendChild(claimdateH6);
		
		// 출금일
		const settledateTd = document.createElement('td');
		const settledateH6 = document.createElement('h6');
		settledateH6.className = 'fw-semibold mb-0';
		settledateH6.textContent = (withdraw.status === 1) ? formatDateDot(withdraw.settledate) : '-';
		settledateTd.appendChild(settledateH6);		
		
        // 승인 여부
		const stateTd = renderWithdrawState(withdraw);

		// 기능
		const payload = parseJwt(localStorage.getItem('accessToken'));
		const isAction = payload.role === 'ROLE_SUPER_ADMIN' || (payload.role === 'ROLE_ADMIN' && withdraw.role === 'ROLE_USER') || payload.id === withdraw.user_id;
		
		const actionTd = document.createElement('td');
		const btnDiv = document.createElement('div');
		btnDiv.className = 'd-flex justify-content-end gap-2';
		
		if (isAction) {
			const deleteBtn = document.createElement('button');
			deleteBtn.type = 'button';
			deleteBtn.className = 'btn btn-sm btn-outline-danger';
			deleteBtn.innerHTML = '삭제';
			deleteBtn.onclick = () => deleteWithdraw(withdraw.id);
			btnDiv.appendChild(deleteBtn);
		}
		actionTd.appendChild(btnDiv);
		
		tr.append(noTd, usernameTd, nameTd, pointTd, accountnumTd, claimdateTd, settledateTd, stateTd, actionTd);
        container.appendChild(tr);
    });
}





/**
 * 출금 상태를 화면에 렌더링하는 함수
 */
function renderWithdrawState(withdraw) {
	const stateTd = document.createElement('td');
	stateTd.className = 'text-center';
	
	if (withdraw.status === 0) {
		const span = document.createElement('span');
		span.type = 'button';
		span.className = 'badge text-bg-gray';
		span.textContent = '미승인';
		span.disabled = true;
		stateTd.appendChild(span);
	}
	else if (withdraw.status === 1) {
		const span = document.createElement('span');
		span.type = 'button';
		span.className = 'badge text-bg-gray';
		span.textContent = '승인';
		span.disabled = true;
		stateTd.appendChild(span);
	}
	else if (withdraw.status === 2) {
		const div = document.createElement('div');
		div.className = 'dropdown d-inline-block';
		
		// 버튼
		const btn = document.createElement('button');
		btn.type = 'button';
		btn.className = 'btn btn-sm dropdown-toggle bg-warning-subtle text-warning';
		btn.textContent = '대기';
		btn.setAttribute('data-bs-toggle', 'dropdown');
		btn.setAttribute('aria-expanded', 'false');
		
		// 메뉴
		const menu = document.createElement('ul');
		menu.className = 'dropdown-menu shadow-sm';
		
		const approveLi = document.createElement('li');
		const approveBtn = document.createElement('button');
		approveBtn.type = 'button';
		approveBtn.className = 'dropdown-item';
		approveBtn.textContent = '승인';
		approveBtn.addEventListener('click', () => editWithdrawState(withdraw.id, 1));
		approveLi.appendChild(approveBtn);
		menu.appendChild(approveLi);

		const rejectLi = document.createElement('li');
		const rejectBtn = document.createElement('button');
		rejectBtn.type = 'button';
		rejectBtn.className = 'dropdown-item';
		rejectBtn.textContent = '미승인';
		rejectBtn.addEventListener('click', () => editWithdrawState(withdraw.id, 0));
		rejectLi.appendChild(rejectBtn);
		menu.appendChild(rejectLi);
		
		div.append(btn, menu);
		stateTd.appendChild(div);
	}
	
	return stateTd;
}





/**
 * 출금의 승인 여부를 수정하는 함수
 */
async function editWithdrawState(id, state) {
	
	if (!confirm('이 동작은 되돌릴 수 없습니다. 정말로 수정하시겠습니까?')) {
		return;
	}
	
	try {
		const data = {
			id: id,
			status: state
		}
		
		const response = await instance.patch(`/withdraws/${id}`, data, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'WITHDRAW_UPDATE_SUCCESS') {
			alertPrimary('출금 상태 변경에 성공했습니다');
			fetchAdminWithdrawList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'WITHDRAW_UPDATE_FAIL') {
			alertDanger('출금 상태 변경에 실패했습니다');
		}
		else if (code === 'WITHDRAW_INVALID_INPUT') {
			alertDanger('출금 상태 변경에 실패했습니다');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('출금 상태 변경에 실패했습니다');
		}
		else if (code === 'WITHDRAW_UNAUTHORIZED_ACCESS') {
			alertDanger('접근 권한이 없습니다');
		}
		else if (code === 'WITHDRAW_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다');
		}
		else if (code === 'WITHDRAW_NOT_FOUND') {
			alertDanger('출금 상태 변경에 실패했습니다');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('출금 상태 변경에 실패했습니다');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다');
		}
		else {
			alertDanger('서버 내부에서 오류가 발생했습니다');
		}
	}
}









/**
 * 출금을 삭제하는 함수
 */
async function deleteWithdraw(id) {
	
	
	
	
}







