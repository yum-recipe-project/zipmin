/**
 * 전역 변수
 */
let totalPages = 0;
let totalElements = 0;
const size = 15;
let page = 0;
let keyword = '';
let category = '';
let sortKey = 'id';
let sortOrder = 'desc';
let fridgeList = [];





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
 * 냉장고 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchAdminFridgeList();
	});
	document.getElementById('text-srh')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchAdminFridgeList();
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
			
			fridgeList = [];
			fetchAdminFridgeList();
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
			fetchAdminFridgeList();
		});
	});

	fetchAdminFridgeList();
});





/**
 * 서버에서 냉장고 목록 데이터를 가져오는 함수
 */
async function fetchAdminFridgeList(scrollTop = true) {
	
	try {
		const params = new URLSearchParams({
			category: category,
			sort: sortKey + '-' + sortOrder,
			keyword: keyword,
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/admin/fridges?${params}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			fridgeList = response.data.data.content;
			
			// 렌더링
			renderAdminFridgeList(fridgeList);
			renderAdminPagination(fetchAdminFridgeList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		// TODO : 수정
		if (code === 'RECIPE_READ_LIST_FAIL') {
			alertDanger('레시피 목록 조회에 실패했습니다.');
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
 * 냉장고 목록을 화면에 랜더링하는 함수
 */
function renderAdminFridgeList(fridgeList) {
	
	const container = document.querySelector('.fridge_list');
	container.innerHTML = '';
	
	// 냉장고 목록이 존재하지 않는 경우
	if (fridgeList == null || fridgeList.length === 0) {
		document.querySelector('.table_th').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.fixed-table').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}
	
	// 냉장고 목록이 존재하는 경우
	document.querySelector('.search_empty')?.remove();
	document.querySelector('.table_th').style.display = '';
	
	fridgeList.forEach((fridge, index) => {
		const tr = document.createElement('tr');
		tr.dataset.id = fridge.id;
		
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
		
		// 이미지
		const imageTd = document.createElement('td');
		imageTd.className = 'd-flex justify-content-center align-items-center';
		const imageSpan = document.createElement('span');
		imageSpan.className = 'fridge_image';
		imageSpan.style.backgroundImage = `url(${fridge.image})`;
		imageTd.appendChild(imageSpan);
		
		// 재료명
		const nameTd = document.createElement('td');
		const nameH6 = document.createElement('h6');
		nameH6.className = 'fw-semibold mb-1 view';
		nameH6.textContent = fridge.name;
		nameTd.append(nameH6);
		
		// 카테고리
		const categoryTd = document.createElement('td');
		const categoryH6 = document.createElement('h6');
		categoryH6.className = 'fw-semibold mb-0';
		categoryH6.textContent = fridge.category;
		categoryTd.appendChild(categoryH6);
		
		// 보관장소
		const zoneTd = document.createElement('td');
		const zoneH6 = document.createElement('h6');
		zoneH6.className = 'fw-semibold mb-0';
		zoneH6.textContent = fridge.zone;
		zoneTd.appendChild(zoneH6);
		
		// 작성자
		const writerTd = document.createElement('td');
		const writerH6 = document.createElement('h6');
		writerH6.className = 'fw-semibold mb-0';
		writerH6.textContent = `${fridge.nickname} (${fridge.username})`;
		writerTd.appendChild(writerH6);
		
		// 기능 버튼 조건
		const payload = getPayload();
		const canAction =
		    payload.role === 'ROLE_SUPER_ADMIN' ||
		    (payload.role === 'ROLE_ADMIN' && fridge.role === 'ROLE_USER') ||
		    (payload.id === fridge.id);
	
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';
		if (canAction) {
			// 수정
		    const editBtn = document.createElement('button');
		    editBtn.type = 'button';
		    editBtn.className = 'btn btn-sm btn-outline-info';
			editBtn.dataset.bsToggle = 'modal';
			editBtn.dataset.bsTarget = '#editFridgeModal';
		    editBtn.innerHTML = '수정';
		    editBtn.onclick = () => {/*
				const form = document.getElementById('editUserForm');
				form.id.value = user.id;
				form.username.value = user.username;
				form.name.value = user.name;
				form.nickname.value = user.nickname;
				form.tel.value = user.tel;
				form.email.value = user.email;
				*/
			};

			// 삭제
		    const deleteBtn = document.createElement('button');
		    deleteBtn.type = 'button';
		    deleteBtn.className = 'btn btn-sm btn-outline-danger';
		    deleteBtn.innerHTML = '삭제';
		    deleteBtn.onclick = () => deleteFridge(fridge.id);
			
		    btnWrap.append(editBtn, deleteBtn);
		}
		actionTd.appendChild(btnWrap);

		tr.append(noTd, imageTd, nameTd, categoryTd, zoneTd, writerTd, actionTd);
		container.appendChild(tr);
	});
}






















