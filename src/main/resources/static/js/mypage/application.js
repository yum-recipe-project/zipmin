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
 * 전역 변수 선언
 */
let sort = -1;
let totalPages = 0;
let page = 0;
let size = 10;
let applyList = [];




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
			
			totalPages = response.data.data.totalPages;
			page = response.data.data.number;
			applyList = response.data.data.content;
			
			renderApplyList(applyList);
			renderPagination();
			
			document.querySelector('.apply_util .total').innerText = `총 ${response.data.data.totalElements}개`;
		}
	}
	catch (error) {
		console.log(error);
	}
	
}




/**
 * 쿠킹클래스의 신청 목록을 화면에 렌더링하는 함수
 */
function renderApplyList(applyList) {
	const container = document.querySelector('.apply_list');
	container.innerHTML = '';

	applyList.forEach(apply => {
		const li = document.createElement('li');

		const table = document.createElement('table');

		const colgroup = document.createElement('colgroup');
		
		const col1 = document.createElement('col');
		col1.setAttribute('width', '100px');
		
		const col2 = document.createElement('col');
		col2.setAttribute('width', '*');
		
		colgroup.appendChild(col1);
		colgroup.appendChild(col2);

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





/**
 * 페이지네이션을 화면에 렌더링하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchGuideList();
			}
		});
	});
}













