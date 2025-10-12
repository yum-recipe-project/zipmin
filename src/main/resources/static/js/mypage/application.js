/**
 * 전역 변수
 */
let sort = -1;
let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let applyList = [];





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
document.addEventListener('DOMContentLoaded', function() {
	
	// 정렬 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			applyList = [];
			
			fetchApplyList();
		});
	});
	
	fetchApplyList();
});





/**
 * 서버에서 쿠킹클래스의 신청 목록 데이터를 가져오는 함수
 */
async function fetchApplyList() {
	
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
			document.querySelector('.apply_util .total').innerText = `총 ${totalElements}개`;
		}
	}
	catch (error) {
		
		// TODO : 에러코드
		console.log(error);
	}
	
}




/**
 * 쿠킹클래스의 신청 목록을 화면에 렌더링하는 함수
 */
function renderApplyList(applyList) {
	
	const container = document.querySelector('.apply_list');
	container.innerHTML = '';

	// 쿠킹클래스의 신청 목록이 존재하지 않는 경우	
	if (applyList == null || applyList.length === 0) {
		container.style.display = 'none';
		document.querySelector('.list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '쿠킹클래스 신청서가 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 쿠킹클래스의 신청 목록이 존재하는 경우
	applyList.forEach(apply => {
		container.style.display = 'block';
		document.querySelector('.list_empty')?.remove();
		
		const li = document.createElement('li');

		const table = document.createElement('table');
		const colgroup = document.createElement('colgroup');
		const col1 = document.createElement('col');
		col1.setAttribute('width', '100px');
		const col2 = document.createElement('col');
		col2.setAttribute('width', '*');
		colgroup.append(col1, col2);

		const tbody = document.createElement('tbody');

		const rows = [
			{ label: '이름', value: apply.name },
			{ label: '상태', value: apply.selected === 1 ? '선정' : '대기' },
			{ label: '신청동기', value: apply.reason },
			{ label: '질문', value: apply.question }
		];

		rows.forEach(row => {
			const tr = document.createElement('tr');

			const th = document.createElement('th');
			th.scope = 'col';
			th.textContent = row.label;

			const td = document.createElement('td');
			td.textContent = row.value;

			tr.append(th, td);
			tbody.appendChild(tr);
		});

		table.append(colgroup, tbody);
		li.appendChild(table);
		
		// 선정/대기 버튼 영역
		const selectBox = document.createElement('div');
		selectBox.className = 'select_btn';

		// 버튼 생성
		const selectBtn = document.createElement('button');
		selectBtn.textContent = '선정';

		const pendingBtn = document.createElement('button');
		pendingBtn.textContent = '대기';

		// 초기 상태 active 적용
		if (apply.selected === 1) {
			selectBtn.classList.add('active');
		} else {
			pendingBtn.classList.add('active');
		}

		// 선정 버튼 클릭 이벤트
		selectBtn.onclick = async function () {
			try {
				const id = new URLSearchParams(window.location.search).get('id');
				const token = localStorage.getItem('accessToken');

				const data = {
					id: apply.id,
					selected: 1,
					class_id: id
				};

				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				};

				await instance.patch(`/classes/${id}/applies/${apply.id}`, data, { headers });

				selectBtn.classList.add('active');
				pendingBtn.classList.remove('active');
			} catch (error) {
				console.log(error);
			}
		};

		// 대기 버튼 클릭 이벤트
		pendingBtn.onclick = async function () {
			try {
				const id = new URLSearchParams(window.location.search).get('id');
				const token = localStorage.getItem('accessToken');

				const data = {
					id: apply.id,
					selected: 0,
					class_id: id
				};

				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				};

				await instance.patch(`/classes/${id}/applies/${apply.id}`, data, { headers });

				pendingBtn.classList.add('active');
				selectBtn.classList.remove('active');
			} catch (error) {
				console.log(error);
			}
		};

		selectBox.append(selectBtn, pendingBtn);
		li.appendChild(selectBox);


		// 버튼 영역
		const btnBox = document.createElement('div');
		btnBox.className = 'attend_btn';

		// 버튼 생성
		const attendBtn = document.createElement('button');
		attendBtn.textContent = '출석';

		const absentBtn = document.createElement('button');
		absentBtn.textContent = '결석';
		
		if (apply.attend === 1) {
			attendBtn.classList.add('active');
		}
		else {
			absentBtn.classList.add('active');
		}

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
	});
}














