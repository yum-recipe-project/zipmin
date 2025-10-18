/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 20;
let keyword = '';
let category = '';
let approval = '';
let status = '';
let sortKey = 'id'
let sortOrder = 'desc';
let classList = [];





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
 * 쿠킹클래스 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchClassList();
	});
	document.getElementById('text-srh')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchClassList();
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
			
			classList = [];
			fetchClassList();
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
			fetchClassList();
		});
	});
	
	// 승인 대기 스위치
	document.getElementById('listClassApproval').addEventListener('change', function () {
		if (this.checked) {
			document.getElementById('listClassStatus').checked = false;
		}
		approval = this.checked ? 'PENDING' : '';
		status = this.checked ? 'open' : '';
		page = 0;
		fetchClassList();
	});
	
	// 모집중 스위치
	document.getElementById('listClassStatus').addEventListener('change', function () {
		if (this.checked) {
			document.getElementById('listClassApproval').checked = false;
		}
		approval = this.checked ? 'APPROVED' : '';
		status = this.checked ? 'open' : '';
		page = 0;
		fetchClassList();
	});
	
	fetchClassList();
});





/**
 * 서버에서 쿠킹클래스 목록 데이터를 가져오는 함수
 */
async function fetchClassList(scrollTop = true) {
	
	try {
		const params = new URLSearchParams({
			category: category,
			approval: approval,
			status: status,
			sort: sortKey + '-' + sortOrder,
			keyword : keyword,
			page : page,
			size : size
		}).toString();
		
		const response = await instance.get(`/admin/classes?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_READ_LIST_SUCCESS') {
			// 전역 변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			classList = response.data.data.content;
			
			// 렌더링
			renderClassList(classList);
			renderAdminPagination(fetchClassList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'CLASS_READ_LIST_FAIL') {
			alertDanger('쿠킹클래스 목록 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'AUTH_TOKEN_INVALID') {
			redirectToAdminLogin();
		}
		else if (code === 'CLASS_FORBIDDEN') {
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
 * 쿠킹클래스 목록을 화면에 렌더링하는 함수
 */
function renderClassList(classList) {
	
	const container = document.querySelector('.class_list');
	container.innerHTML = '';
	
	// 쿠킹클래스 목록이 존재하지 않는 경우
	if (classList == null || classList.length === 0) {
		document.querySelector('.table_th').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.fixed-table').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}
	
	// 쿠킹클래스 목록이 존재하는 경우
	document.querySelector('.search_empty')?.remove();
	document.querySelector('.table_th').style.display = '';

	classList.forEach((classs, index) => {
	    const tr = document.createElement('tr');
		tr.dataset.id = classs.id;

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
	
		// 카테고리
		const categoryTd = document.createElement('td');
		const categoryH6 = document.createElement('h6');
		categoryH6.className = 'fw-semibold mb-0';
		categoryH6.textContent = classs.category;
		categoryTd.appendChild(categoryH6);
		
		// 제목
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-0 view';
		titleH6.textContent = classs.title;
		titleH6.dataset.bsToggle = 'modal';
		titleH6.dataset.bsTarget = '#viewClassModal';
		titleH6.dataset.id = classs.id;
		titleTd.appendChild(titleH6);
		
		// 신청자
		const writerTd = document.createElement('td');
		const writerH6 = document.createElement('h6');
		writerH6.className = 'fw-semibold mb-0';
		writerH6.textContent = `${classs.nickname} (${classs.username})`;
		writerTd.appendChild(writerH6);
		
		// 신청일
		const dateTd = document.createElement('td');
		const dateH6 = document.createElement('h6');
		dateH6.className = 'fw-semibold mb-0';
		dateH6.textContent = formatDateDot(classs.postdate);
		dateTd.appendChild(dateH6);
		
		// 기간
		const periodTd = document.createElement('td');
		const periodH6 = document.createElement('h6');
		periodH6.className = 'fw-semibold mb-0';
		periodH6.textContent = `${formatDateDot(classs.eventdate)} ${formatTime(classs.starttime)} - ${formatTime(classs.endtime)}`;
		periodTd.appendChild(periodH6);
		
		// 상태
		const statusTd = renderClassStatus(classs);
		
		// 참여자수
		const totalTd = document.createElement('td');
		totalTd.classList.add('total-td');
		const totalWrap = document.createElement('div');
		totalWrap.className = 'total-cell d-inline-block position-relative';
		const totalCount = document.createElement('h6');
		totalCount.className = 'fw-semibold mb-0';
		totalCount.textContent = classs.applycount;
		
		// 참여자수 조회
		if ((classs.applycount ?? 0) > 0) {
			totalCount.className = 'fw-semibold mb-0 view';
			totalCount.dataset.bsToggle = 'modal';
			totalCount.dataset.bsTarget = '#listClassApplyModal';
			totalCount.dataset.class_id = classs.id;
		}
		totalWrap.appendChild(totalCount);
		totalTd.appendChild(totalWrap);
		
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 기능 권한
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		const canAction =
		    payload.role === 'ROLE_SUPER_ADMIN' ||
		    (payload.role === 'ROLE_ADMIN' && classs.role === 'ROLE_USER') ||
		    (payload.id === classs.user_id);
			
		if (canAction) {
			// 삭제 버튼
			const deleteBtn = document.createElement('button');
			deleteBtn.type = 'button';
			deleteBtn.className = 'btn btn-sm btn-outline-danger';
			deleteBtn.innerHTML = '삭제';
			deleteBtn.onclick = () => deleteClass(classs.id);
			
			btnWrap.appendChild(deleteBtn);
		}
		
		actionTd.appendChild(btnWrap);
		
		tr.append(noTd, categoryTd, titleTd, writerTd, dateTd, periodTd, statusTd, totalTd, actionTd);
		container.appendChild(tr);
	});
}





/**
 * 쿠킹클래스 상태를 화면에 렌더링하는 함수
 */
function renderClassStatus(classs) {
	const td = document.createElement('td');
	td.className = 'text-center';
	
	// 드롭다운
	if (classs.opened) {
		const wrap = document.createElement('div');
		wrap.className = 'dropdown d-inline-block';
		
		const btn = document.createElement('button');
		btn.type = 'button';
		switch (classs.approval) {
			case 1:
				btn.className = 'btn btn-sm dropdown-toggle bg-primary-subtle text-primary';
				btn.textContent = '승인';
				break;
			case 2:
				btn.className = 'btn btn-sm dropdown-toggle bg-warning-subtle text-warning';
				btn.textContent = '대기';
				break;
			case 0:
				btn.className = 'btn btn-sm dropdown-toggle bg-danger-subtle text-danger';
				btn.textContent = '미승인';
				break;
		}
		btn.setAttribute('data-bs-toggle', 'dropdown');
		btn.setAttribute('aria-expanded', 'false');
		
		const menu = document.createElement('ul');
		menu.className = 'dropdown-menu shadow-sm';
		
		// 승인
		const liApprove = document.createElement('li');
		const btnApprove = document.createElement('button');
		btnApprove.type = 'button';
		btnApprove.className = 'dropdown-item';
		btnApprove.textContent = '승인';
		btnApprove.addEventListener('click', () => editClassApproval(classs.id, 'APPROVED'));
		liApprove.appendChild(btnApprove);
		menu.appendChild(liApprove);

		// 반려
		const liReject = document.createElement('li');
		const btnReject = document.createElement('button');
		btnReject.type = 'button';
		btnReject.className = 'dropdown-item';
		btnReject.textContent = '미승인';
		btnReject.addEventListener('click', () => editClassApproval(classs.id, 'REJECTED'));
		liReject.appendChild(btnReject);
		menu.appendChild(liReject);

		// 대기
		const liPending = document.createElement('li');
		const btnPending = document.createElement('button');
		btnPending.type = 'button';
		btnPending.className = 'dropdown-item';
		btnPending.textContent = '대기';
		btnPending.addEventListener('click', () => editClassApproval(classs.id, 'PENDING'));
		liPending.appendChild(btnPending);
		menu.appendChild(liPending);
		
		wrap.append(btn, menu);
		td.appendChild(wrap)
	}
	// 버튼
	else {
		const span = document.createElement('span');
		span.type = 'button';
		span.className = 'badge text-bg-gray';
		switch (classs.approval) {
			case 1:
				span.textContent = '승인';
				break;
			case 2:
				span.textContent = '대기 만료';
				break;
			case 0:
				span.textContent = '미승인';
				break;
		}
		span.disabled = true;
		td.appendChild(span);
	}
	
	return td;
}





/**
 * 클래스의 승인 여부를 수정하는 함수
 */
async function editClassApproval(id, approval) {
	
	try {
		const data = {
			id: id,
			approval: approval
		}
		
		const response = await instance.patch(`/admin/classes/${id}/approval`, data, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_UPDATE_APPROVAL_SUCCESS') {
			alertPrimary('쿠킹클래스의 상태 변경에 성공했습니다.');
			fetchClassList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'CLASS_UPDATE_APPROVAL_FAIL') {
			alertDanger('쿠킹클래스의 상태 변경에 실패했습니다.');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'CLASS_UNAUTHORIZED') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'CLASS_FORBIDDEN') {
			redirectToAdminLogin();
		}
		else if (code === 'CLASS_ALREADY_ENDED') {
			alertDanger('쿠킹클래스가 상태 변경 기간이 종료되었습니다.');
		}
		else if (code === 'CLASS_NOT_FOUND') {
			alertDanger('해당 클래스를 찾을 수 없습니다.');
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
 * 클래스를 삭제하는 함수
 */
async function deleteClass(id) {
	
	try {
		const response = await instance.delete(`/classes/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_DELETE_SUCCESS') {
			alertPrimary('쿠킹클래가 성공적으로 삭제되었습니다.');
			fetchClassList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'CLASS_DELETE_FAIL') {
			alertDanger('클래스 삭제에 실패했습니다');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alealertDangerrt('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alealertDangerrt('입력값이 유효하지 않습니다.');
		}
		else if (code === 'CLASS_UNAUTHORIZED') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'CLASS_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'CLASS_NOT_FOUND') {
			alertDanger('해당 클래스를 찾을 수 없습니다.');
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





































