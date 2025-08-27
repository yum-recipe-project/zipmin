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
			status = '';
		}
		approval = this.checked ? 'PENDING' : '';
		page = 0;
		fetchClassList();
	});
	
	// 모집중 스위치
	document.getElementById('listClassStatus').addEventListener('change', function () {
		if (this.checked) {
			document.getElementById('listClassApproval').checked = false;
			approval = '';
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
		
		const response = await fetch(`/classes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'CLASS_READ_LIST_SUCCESS') {
			
			// 전역 변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			classList = result.data.content;
			
			// 렌더링
			renderClassList(classList);
			renderAdminPagination(fetchClassList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.table_th').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const table = document.querySelector('.fixed-table');
				table.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('.table_th').style.display = '';
			}
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
		
		/***** 에러 코드 추가 *****/
	}
	catch (error) {
		console.log(error);
	}
}





/**
 * 쿠킹클래스 목록을 화면에 렌더링하는 함수
 */
function renderClassList(classList) {
	const container = document.querySelector('.class_list');
	container.innerHTML = '';

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
		// ***** 세부 내용 더 추가하기 *****
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
		// ***** 더 제대로 일단 이거 버튼으로 승인 등등 할 수 있어야 함 *****
		const statusTd = renderClassStatus(classs);
		
		// 참여자수
		const totalTd = document.createElement('td');
		totalTd.classList.add('total-td');
		const totalWrap = document.createElement('div');
		totalWrap.className = 'total-cell d-inline-block position-relative';
		const totalCount = document.createElement('h6');
		totalCount.className = 'fw-semibold mb-0';
		totalCount.textContent = classs.applycount;
		
		// 참여자 보기
		if ((classs.applycount ?? 0) > 0) {
			totalCount.className = 'fw-semibold mb-0 view';
			totalCount.dataset.bsToggle = 'modal';
			totalCount.dataset.bsTarget = '#listClassApplyModal';
			totalCount.dataset.class_id = classs.id;
			totalCount.addEventListener('click', (event) => {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					return;
				}
			});
		}
		totalWrap.appendChild(totalCount);
		totalTd.appendChild(totalWrap);
		
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 기능 버튼 조건
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
 * 
 */
function renderClassStatus(classs) {
	const td = document.createElement('td');
	td.className = 'text-center';
	
	// 드롭다운을 만드는 함수
	function makeDropdown(approval) {
		const wrap = document.createElement('div');
		wrap.className = 'dropdown d-inline-block';
		
		const btn = document.createElement('button');
		btn.type = 'button';
		switch (approval) {
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
		btnApprove.addEventListener('click', () => onApprove(classs.id));
		liApprove.appendChild(btnApprove);
		menu.appendChild(liApprove);

		// 반려
		const liReject = document.createElement('li');
		const btnReject = document.createElement('button');
		btnReject.type = 'button';
		btnReject.className = 'dropdown-item';
		btnReject.textContent = '미승인';
		btnReject.addEventListener('click', () => onApprove(classs.id));
		liReject.appendChild(btnReject);
		menu.appendChild(liReject);

		// 대기
		const liPending = document.createElement('li');
		const btnPending = document.createElement('button');
		btnPending.type = 'button';
		btnPending.className = 'dropdown-item';
		btnPending.textContent = '대기';
		btnPending.addEventListener('click', () => onApprove(classs.id));
		liPending.appendChild(btnPending);
		menu.appendChild(liPending);
		
		wrap.appendChild(btn);
		wrap.appendChild(menu);
		return wrap;
	}

	// 단일 버튼(확정 상태용)
	function makeButton() {
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
		return span;
	}
	
	// 분기 렌더링
	if (classs.opened) {
		td.appendChild(makeDropdown(classs.approval));
	}
	else {
		td.appendChild(makeButton());
	}
	
	return td;
}

// onApprove/onReject/onClose/onDelete는 기존에 정의된 핸들러 사용
 function onApprove(id) { 
	
  }
 function onReject(id)  { }
 function onClose(id)   {  }
 function onDelete(id)  {  }







/**
 * 클래스를 삭제하는 함수
 */
async function deleteClass(id) {
	
	try {
		
	}
	catch (error) {
		const code = error?.response?.data?.code;
	}
	
}





































