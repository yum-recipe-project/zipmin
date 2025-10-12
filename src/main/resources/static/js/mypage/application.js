/**
 * 전역 변수
 */
let sort = -1;
let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let applyList = [];
let isOpened = false;
let isEvented = false;





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
 * 정렬 버튼 클릭시 연결된 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	const wrap = document.getElementById('applyWrap');
	
	// 정렬 버튼
	wrap.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			wrap.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			applyList = [];
			
			fetchApplyList();
		});
	});
	
	await fetchClass();
	await fetchApplyList();
});





/**
 * 서버에서 쿠킹클래스 상세 정보를 가져오는 함수
 */
async function fetchClass() {
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await instance.get(`/classes/${id}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'CLASS_READ_SUCCESS') {
			// 전역변수 설정
			isOpened = response.data.data.opened;
			isEvented = response.data.data.evented;
			
			// 렌더링
			const wrap = document.getElementById('applyWrap');
			wrap.querySelector('.apply_title').innerText = `${response.data.data.title} 신청서`;
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'CLASS_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'CLASS_TARGET_READ_LIST_FAIL') {
			console.log(error);
		}
		else if (code === 'CLASS_SCHEDULE_READ_LIST_FAIL') {
			console.log(error);
		}
		else if (code === 'CLASS_TUTOR_READ_LIST_FAIL') {
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
 * 서버에서 쿠킹클래스의 신청 목록 데이터를 가져오는 함수
 */
async function fetchApplyList() {

	const wrap = document.getElementById('applyWrap');
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const params = new URLSearchParams({
			sort: Number(sort),
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/classes/${id}/applies?${params}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'CLASS_APPLY_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			applyList = response.data.data.content;
			
			// 렌더링
			renderApplyList(applyList);
			renderPagination(fetchApplyList);
			wrap.querySelector('.apply_util .total').innerText = `총 ${totalElements}개`;
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		// TODO : 에러코드
		console.log(error);
	}
	
}





/**
 * 쿠킹클래스의 신청 목록을 화면에 렌더링하는 함수
 */
function renderApplyList(applyList) {
	
	const wrap = document.getElementById('applyWrap');
	const container = wrap.querySelector('.apply_list');
	container.innerHTML = '';

	// 쿠킹클래스의 신청 목록이 존재하지 않는 경우	
	if (applyList == null || applyList.length === 0) {
		container.style.display = 'none';
		wrap.querySelector('.list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '쿠킹클래스 신청서가 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 쿠킹클래스의 신청 목록이 존재하는 경우
	container.style.display = 'table';
	wrap.querySelector('.list_empty')?.remove();
	
	const thead = document.createElement('thead');
	thead.innerHTML = `
		<tr>
			<th width="10%">재료</th>
			<th width="60%">용량</th>
			<th width="15%">수정</th>
			<th width="15%">선택</th>
		</tr>
	`;
	container.appendChild(thead);
		
	const tbody = document.createElement('tbody');
	applyList.forEach((apply, index) => {
		const tr = document.createElement('tr');
		// tr.dataset.id = apply.id;
		
		// 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		// TODO : 수정
		// noH6.textContent = offset + 1;
		const offset = page * size + index;
		noH6.textContent = totalElements - offset;
		noTd.appendChild(noH6);
		
		// 신청자
		const writerTd = document.createElement('td');
		const writerH6 = document.createElement('h6');
		// writerH6.textContent = `${classs.nickname} (${classs.username})`;
		writerH6.textContent = apply.name;
		writerTd.appendChild(writerH6);

		// 승인
		const selectedTd = renderApplySelected(apply);
		
		// 출셕
		const attendTd = renderApplyAttend(apply);

		tr.append(noTd, writerTd, selectedTd, attendTd);
		tbody.appendChild(tr);
		container.appendChild(tbody);
	});
}





/**
 * 클래스 신청서의 선택 여부를 화면에 랜더링하는 함수
 */
function renderApplySelected(apply) {
	
	const td = document.createElement('td');

	// 드롭다운
	if (isOpened) {
		const wrap = document.createElement('div');
		wrap.className = 'dropdown';
		
		const btn = document.createElement('button');
		btn.type = 'button';
		switch (apply.selected) {
			case 1:
				btn.className = 'btn_toggle primary';
				btn.textContent = '승인';
				break;
			case 2:
				btn.className = 'btn_toggle primary';
				btn.textContent = '대기';
				break;
			case 0:
				btn.className = 'btn_toggle primary';
				btn.textContent = '미승인';
				break;
		}
		btn.setAttribute('data-bs-toggle', 'dropdown');
		btn.setAttribute('aria-expanded', 'false');
		
		const menu = document.createElement('ul');
		menu.className = 'dropdown-menu';
		
		// 승인
		const liApprove = document.createElement('li');
		const btnApprove = document.createElement('button');
		btnApprove.type = 'button';
		btnApprove.className = 'dropdown-item';
		btnApprove.textContent = '승인';
		btnApprove.addEventListener('click', () => editApplySelected(apply.id, 'APPROVED'));
		liApprove.appendChild(btnApprove);
		menu.appendChild(liApprove);

		// 반려
		const liReject = document.createElement('li');
		const btnReject = document.createElement('button');
		btnReject.type = 'button';
		btnReject.className = 'dropdown-item';
		btnReject.textContent = '미승인';
		btnReject.addEventListener('click', () => editApplySelected(apply.id, 'REJECTED'));
		liReject.appendChild(btnReject);
		menu.appendChild(liReject);

		// 대기
		const liPending = document.createElement('li');
		const btnPending = document.createElement('button');
		btnPending.type = 'button';
		btnPending.className = 'dropdown-item';
		btnPending.textContent = '대기';
		btnPending.addEventListener('click', () => editApplySelected(apply.id, 'PENDING'));
		liPending.appendChild(btnPending);
		menu.appendChild(liPending);
		
		wrap.append(btn, menu);
		td.appendChild(wrap)
	}
	// 버튼
	else {
		const span = document.createElement('span');
		span.type = 'button';
		span.className = 'btn_gray_small';
		switch (apply.selected) {
			case 1:
				span.textContent = '선정';
				break;
			case 2:
				span.textContent = '대기 만료';
				break;
			case 0:
				span.textContent = '미선정';
				break;
		}
		span.disabled = true;
		td.appendChild(span);
	}

	return td;
}


/**
 *
 */
// TODO : 전면 수정 필요
function renderApplyAttend(apply) {
	
	const td = document.createElement('td');
	td.className = 'text-center';

	// 드롭다운
	if (isEvented) {
		const wrap = document.createElement('div');
		wrap.className = 'dropdown d-inline-block';
		
		const btn = document.createElement('button');
		btn.type = 'button';
		switch (apply.selected) {
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
		btnApprove.addEventListener('click', () => editApplySelected(apply.id, 'APPROVED'));
		liApprove.appendChild(btnApprove);
		menu.appendChild(liApprove);

		// 반려
		const liReject = document.createElement('li');
		const btnReject = document.createElement('button');
		btnReject.type = 'button';
		btnReject.className = 'dropdown-item';
		btnReject.textContent = '미승인';
		btnReject.addEventListener('click', () => editApplySelected(apply.id, 'REJECTED'));
		liReject.appendChild(btnReject);
		menu.appendChild(liReject);

		// 대기
		const liPending = document.createElement('li');
		const btnPending = document.createElement('button');
		btnPending.type = 'button';
		btnPending.className = 'dropdown-item';
		btnPending.textContent = '대기';
		btnPending.addEventListener('click', () => editApplySelected(apply.id, 'PENDING'));
		liPending.appendChild(btnPending);
		menu.appendChild(liPending);
		
		wrap.append(btn, menu);
		td.appendChild(wrap)
	}
	// 버튼
	else {
		const span = document.createElement('span');
		span.type = 'button';
		span.className = 'btn_gray_small';
		switch (apply.selected) {
			case 1:
				span.textContent = '출석';
				break;
			case 2:
				span.textContent = '대기';
				break;
			case 0:
				span.textContent = '결석';
				break;
		}
		span.disabled = true;
		td.appendChild(span);
	}

	return td;
}





/**
 * 클래스 신청서의 출석 여부를 화면에 렌더링하는 함수
 */






async function editApplySelected(applyId, selected) {
	
	try {
		const classId = new URLSearchParams(window.location.search).get('id');
		
		const data = {
			id: applyId,
			selected: selected,
			class_id: classId
		};
		
		const response = await instance.patch(`/classes/${classId}/applies/${applyId}`, data, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		// selectBtn.classList.add('active');
		// pendingBtn.classList.remove('active');
		
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		console.log(error);
	}
}











/*

attendBtn.onclick = async function () {
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		const token = localStorage.getItem('accessToken');

		const data = {
			id: apply.id,
			attend: 1,
			class_id: id
		};
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		};
		
		await instance.patch(`/classes/${id}/applies/${apply.id}`, data, { headers });

		attendBtn.classList.add('active');
		absentBtn.classList.remove('active');
	} catch (error) {
		console.log(error);
	}
};

absentBtn.onclick = async function () {
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		const token = localStorage.getItem('accessToken');

		const data = {
			id: apply.id,
			attend: 0,
			class_id: id
		};

		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		};

		await instance.patch(`/classes/${id}/applies/${apply.id}`, data, { headers });
		
		absentBtn.classList.add('active');
		attendBtn.classList.remove('active');
	} catch (error) {
		console.log(error);
	}
};

btnBox.append(attendBtn, absentBtn);
li.appendChild(btnBox);

container.appendChild(li);
*/


