/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 20;
let keyword = '';
let category = '';
let sortKey = 'id'
let sortOrder = 'desc';
let chompList = [];





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
 * 쩝쩝박사 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchChompList();
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
			
			chompList = [];
			renderAddChompButton(category);
			fetchChompList();
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
			fetchChompList();
		});
	});
	
	fetchChompList();
});





/**
 * 서버에서 쩝쩝박사 목록 데이터를 가져오는 함수
 */
async function fetchChompList(scrollTop = true) {
	
	try {
		const params = new URLSearchParams({
			category: category,
			sort: sortKey + '-' + sortOrder,
			keyword : keyword,
			page : page,
			size : size
		}).toString();
		
		const response = await instance.get(`/admin/chomp?${params}`, {
			headers: getAuthHeaders()
		});

		if (response.data.code === 'CHOMP_READ_LIST_SUCCESS') {
			
			// 전역 변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			chompList = response.data.data.content;
			
			// 렌더링
			renderChompList(chompList);
			renderAdminPagination(fetchChompList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 검색 결과 없음 표시
			if (response.data.data.totalPages === 0) {
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
	}
	catch (error) {
		const code = error?.response?.data?.code;
				
		if (code === 'CHOMP_READ_LIST_FAIL') {
			alertDanger('쩝쩝박사 목록 조회에 실패했습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'AUTH_TOKEN_INVALID') {
			redirectToAdminLogin();
		}
		else if (code === 'CHOMP_FORBIDDEN') {
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
 * 쩝쩝박사 추가 버튼을 화면에 렌더링하는 함수
 */
function renderAddChompButton(category) {
	
	const container = document.querySelector('.bar');
	container.querySelector('.btn-info[data-bs-toggle="modal"]')?.remove();
	
	if (category === '') {
		return;
	}
	
	const createBtn = document.createElement('button');
	createBtn.type = 'button';
	createBtn.className = 'btn btn-info m-1';
	createBtn.dataset.bsToggle = 'modal';
	
	let modalId = '';
	let label = '';
	switch (category) {
	    case 'vote':
	        modalId = '#writeVoteModal';
	        label = '투표 생성하기';
	        break;
	    case 'megazine':
	        modalId = '#writeMegazineModal';
	        label = '매거진 생성하기';
	        break;
	    case 'event':
	        modalId = '#writeEventModal';
	        label = '이벤트 생성하기';
	        break;
	    default:
	        break;
	}

	if (modalId && label) {
	    createBtn.dataset.bsTarget = modalId;

	    const icon = document.createElement('i');
	    icon.className = 'ti ti-plus fs-4';
	    const text = document.createTextNode(label);

	    createBtn.append(icon, text);
	}
	document.querySelector('.bar').appendChild(createBtn);
	
};





/**
 * 쩝쩝박사 목록을 화면에 렌더링하는 함수
 */
function renderChompList(chompList) {
    const container = document.querySelector('.chomp_list');
    container.innerHTML = '';

    chompList.forEach((chomp, index) => {
        const tr = document.createElement('tr');
		tr.dataset.id = chomp.id;

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
		
		// 게시판명
		const categoryTd = document.createElement('td');
		const categoryH6 = document.createElement('h6');
		categoryH6.className = 'fw-semibold mb-0';
		switch(chomp.category) {
			case 'vote' :
				categoryH6.textContent = '투표';
				break;
			case 'megazine' :
				categoryH6.textContent = '매거진';
				break;
			case 'event' :
				categoryH6.textContent = '이벤트';
				break;
		}
		categoryTd.appendChild(categoryH6);

        // 제목
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-0 view';
		titleH6.textContent = chomp.title;
		switch (chomp.category) {
			case 'vote':
				titleH6.dataset.bsToggle = 'modal';
				titleH6.dataset.bsTarget = '#viewVoteModal';
				titleH6.dataset.id = chomp.id;
				break;
			case 'megazine':
				titleH6.dataset.bsToggle = 'modal';
				titleH6.dataset.bsTarget = '#viewMegazineModal';
				titleH6.dataset.id = chomp.id;
				break;
			case 'event':
				titleH6.dataset.bsToggle = 'modal';
				titleH6.dataset.bsTarget = '#viewEventModal';
				titleH6.dataset.id = chomp.id;
				break;
		}
		titleTd.appendChild(titleH6);

        // 기간
		const periodTd = document.createElement('td');
		const periodH6 = document.createElement('h6');
		periodH6.className = 'fw-semibold mb-0';
		switch(chomp.category) {
			case 'vote' :
				periodH6.textContent = `${formatDateDot(chomp.opendate)} - ${formatDateDot(chomp.closedate)}`;
				break;
			case 'megazine' :
				periodH6.textContent = `${formatDateDot(chomp.closedate)}`;
				break;
			case 'event' :
				periodH6.textContent = `${formatDateDot(chomp.opendate)} - ${formatDateDot(chomp.closedate)}`;
				break;
		}
		periodTd.appendChild(periodH6);
		
		// 상태
		const statusTd = document.createElement('td');
		const statusSpan = document.createElement('span');
		switch (chomp.category) {
			case 'vote' :
				statusSpan.className = `badge ${chomp.opened ? 'bg-primary-subtle text-primary' : 'bg-danger-subtle text-danger'} d-inline-flex align-items-center gap-1`;
				statusSpan.innerHTML = chomp.opened ? '투표 진행중' : '투표 종료';
				break;
			case 'event' :
				statusSpan.className = `badge ${chomp.opened ? 'bg-primary-subtle text-primary' : 'bg-danger-subtle text-danger'} d-inline-flex align-items-center gap-1`;
				statusSpan.innerHTML = chomp.opened ? '행사 진행중' : '행사 종료';
				break;
		}
		statusTd.appendChild(statusSpan);

        // 댓글수
		const commentTd = document.createElement('td');
		const commentH6 = document.createElement('h6');
		commentH6.className = 'fw-semibold mb-0';
		commentH6.textContent = chomp.commentcount;
		commentTd.appendChild(commentH6);
		
		// 참여자수
		const totalTd = document.createElement('td');
		const totalH6 = document.createElement('h6');
		totalH6.className = 'fw-semibold mb-0';
		if (chomp.category === 'vote') {
			totalH6.textContent = chomp.recordcount;
		}
		totalTd.appendChild(totalH6);
		
        // 기능
		const actionTd = document.createElement('td');
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		const canAction =
			payload.role === 'ROLE_SUPER_ADMIN' ||
			(payload.role === 'ROLE_ADMIN' && chomp.role === 'ROLE_USER') ||
			(payload.id === chomp.user_id);
		
		if (canAction) {
			const btnWrap = document.createElement('div');
			btnWrap.className = 'd-flex justify-content-end gap-2';
			
			// 수정
			const editBtn = document.createElement('button');
			editBtn.type = 'button';
			editBtn.className = 'btn btn-sm btn-outline-info';
			editBtn.innerHTML = '수정';
			switch (chomp.category) {
				case 'vote' :
					editBtn.dataset.bsToggle = 'modal';
					editBtn.dataset.bsTarget = '#editVoteModal';
					editBtn.dataset.id = chomp.id;
					editBtn.addEventListener('click', function(event) {
						event.preventDefault();
						document.getElementById('editVoteId').value = chomp.id;
						document.getElementById('editVoteTitleInput').value = chomp.title;
						document.getElementById('editVoteOpendateInput').value = chomp.opendate.split("T")[0];
						document.getElementById('editVoteClosedateInput').value = chomp.closedate.split("T")[0];
						
						const choiceList = document.getElementById('editVoteChoiceList');
						const addChoiceBtn = document.getElementById('addEditVoteChoiceBtn');
						let inputs = choiceList.querySelectorAll('input[name="choice"]');
						
						const needed = (chomp.choice_list || []).length;
						while (inputs.length < needed) {
							addChoiceBtn && addChoiceBtn.click();
							inputs = choiceList.querySelectorAll('input[name="choice"]');
						}
						
						chomp.choice_list.forEach((choice, index) => {
						    if (inputs[index]) inputs[index].value = choice.choice || '';
							inputs[index].dataset.id = choice.id;
						});
					})
					break;
				case 'megazine' :
					editBtn.dataset.bsToggle = 'modal';
					editBtn.dataset.bsTarget = '#editMegazineModal';
					editBtn.addEventListener('click', function(event) {
						event.preventDefault();
						if (!isLoggedIn()) {
							redirectToLogin();
							return;
						}
						document.getElementById('editMegazineId').value = chomp.id;
						document.getElementById('editMegazineTitleInput').value = chomp.title;
						document.getElementById('editMegazineContentInput').value = chomp.content;
					});
					break;
				case 'event' :
					editBtn.dataset.bsToggle = 'modal';
					editBtn.dataset.bsTarget = '#editEventModal';
					editBtn.addEventListener('click', function(event) {
						event.preventDefault();
						if (!isLoggedIn()) {
							redirectToLogin();
							return;
						}
						document.getElementById('editEventId').value = chomp.id;
						document.getElementById('editEventTitleInput').value = chomp.title;
						document.getElementById('editEventOpendateInput').value = chomp.opendate.split("T")[0];
						document.getElementById('editEventClosedateInput').value = chomp.closedate.split("T")[0];
						document.getElementById('editEventContentInput').value = chomp.content;
					});
					break;
			}
			editBtn.addEventListener('click', (event) => {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					return;
				}
			});
			
			// 삭제 버튼
			const deleteBtn = document.createElement('button');
			deleteBtn.type = 'button';
			deleteBtn.className = 'btn btn-sm btn-outline-danger';
			deleteBtn.innerHTML = '삭제';
			switch (chomp.category) {
				case 'vote' :
					deleteBtn.onclick = () => deleteVote(chomp.id);
					break;
				case 'megazine' :
					deleteBtn.onclick = () => deleteMegazine(chomp.id);
					break;
				case 'event' :
					deleteBtn.onclick = () => deleteEvent(chomp.id);
					break;
			}
			
			btnWrap.append(editBtn, deleteBtn);
			actionTd.appendChild(btnWrap);
		}
		
		tr.append(noTd, categoryTd, titleTd, periodTd, statusTd, commentTd, totalTd, actionTd);
        container.appendChild(tr);
    });
}





/**
 * 매거진을 삭제하는 함수
 */
async function deleteMegazine(id) {
	
	try {
		const response = await instance.delete(`/megazines/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'MEGAZINE_DELETE_SUCCESS') {
			alertPrimary('매거진이 성공적으로 삭제되었습니다.');
			fetchChompList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'MEGAZINE_DELETE_FAIL') {
			alertDanger('매거진 삭제에 실패했습니다');
		}
		else if (code === 'MEGAZINE_INVALID_INPUT') {
			alealertDangerrt('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alealertDangerrt('입력값이 유효하지 않습니다.');
		}
		else if (code === 'MEGAZINE_UNAUTHORIZED') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'MEGAZINE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'MEGAZINE_NOT_FOUND') {
			alertDanger('해당 매거진을 찾을 수 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}

}





/**
 * 투표를 삭제하는 함수
 */
async function deleteVote(id) {
	
	try {
		const response = await instance.delete(`/votes/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'VOTE_DELETE_SUCCESS') {
			alertPrimary('투표가 성공적으로 삭제되었습니다.');
			fetchChompList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'VOTE_DELETE_FAIL') {
			alertDanger('투표 삭제에 실패했습니다');
		}
		else if (code === 'VOTE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'VOTE_UNAUTHORIZED') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'VOTE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'VOTE_NOT_FOUND') {
			alertDanger('해당 투표를 찾을 수 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}

}





/**
 * 이벤트를 삭제하는 함수
 */
async function deleteEvent(id) {
	
	try {
		const response = await instance.delete(`/events/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'EVENT_DELETE_SUCCESS') {
			alertPrimary('이벤트가 성공적으로 삭제되었습니다.');
			fetchChompList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'EVENT_DELETE_FAIL') {
			alertDanger('이벤트 삭제에 실패했습니다');
		}
		else if (code === 'EVENT_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'EVENT_UNAUTHORIZED') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'EVENT_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'EVENT_NOT_FOUND') {
			alertDanger('해당 이벤트를 찾을 수 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}

}
