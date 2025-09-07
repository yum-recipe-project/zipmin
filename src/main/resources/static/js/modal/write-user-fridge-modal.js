/**
 * 전역 변수
 */
let keyword = '';
let mode = 'like';





/**
 * 냉장고 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="fridge"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word').value.trim();
		fetchFridgeList();
	});
	
	// 검색창 빈 경우 초기화
	searchForm.querySelector('.search_word')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			tabItems.forEach(button => button.classList.remove('active'));
			tabItems[0].classList.add('active');
			fetchLikedFridgeList();
		}
	});
	
	// 탭 클릭 이벤트 설정
	const tabItems = document.querySelectorAll('.tab_button');
	tabItems.forEach((item) => {
		item.addEventListener('click', function(event) {
			event.preventDefault();
			closeWriteUserFridgeSheet();
			
			tabItems.forEach(button => button.classList.remove('active'));
			this.classList.add('active');
			mode = 'like';
			document.getElementById('toggleDeleteModeButton').textContent = '관리';
			
			if (this.dataset.tab === 'liked-fridge') {
				fetchLikedFridgeList();
			}
			else if (this.dataset.tab === 'created-fridge') {
				fetchCreatedFridgeList();
			}
		});
	});
	
	// 초기 실행
	tabItems.forEach(button => button.classList.remove('active'));
	tabItems[0].classList.add('active');
	fetchLikedFridgeList();
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 사용자 냉장고 작성 모달
	const modal = document.getElementById('writeUserFridgeModal');
	modal.addEventListener('hidden.bs.modal', function() {
		
		// 검색창 초기화
		keyword = '';
		const searchForm = document.querySelector('.search_form[data-type="fridge"]');
		searchForm.reset();
		
		// 모드 초기화
		mode = 'like';
		document.getElementById('toggleDeleteModeButton').textContent = '관리';
		
		// 탭 초기화
		const tabItems = document.querySelectorAll('.tab_button');
		tabItems.forEach(button => button.classList.remove('active'));
		tabItems[0].classList.add('active');
		fetchLikedFridgeList();
		
		// 시트 초기화
		const sheetForm = document.getElementById('writeUserFridgeForm');
		sheetForm.reset();
		sheetForm.querySelectorAll('.is-invalid').forEach(input => input.classList.remove('is-invalid'));
		sheetForm.querySelectorAll('.danger').forEach(el => (el.style.display = 'none'));
		closeWriteUserFridgeSheet();
	});
});





// *** TODO : 관리자와 본인이 작성한 냉장고만 검색되도록 변경 ***

/**
 * 서버에서 냉장고 목록을 가져오는 함수
 */
async function fetchFridgeList() {
	
	// 냉장고 작성 모달
	const modal = document.getElementById('writeUserFridgeModal');
	
	// 냉장고 목록 조회
	try {
		const params = new URLSearchParams({
			keyword: keyword,
			size: 1000,
			page: 0
		}).toString();
		
		const response = await fetch(`/fridges?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});

		const result = await response.json();
		
		if (result.code === 'FRIDGE_READ_LIST_SUCCESS') {
			// 렌더링
			renderFridgeList(result.data.content, fetchFridgeList);
			modal.querySelector('.fridge_total').textContent = `총 ${result.data.totalElements}개`;
			modal.querySelector('.tab_button_wrap').style.display = 'none';
			modal.querySelector('.btn_wrap').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').style.display = '';
			}
		}
		else if (result.code === 'FRDIGE_READ_LIST_FAIL') {
			alertDanter('냉장고 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
	}
	catch(error) {
		console.log(error);
	}
}





/**
 * 서버에서 좋아요한 냉장고 목록을 가져오는 함수
 */
async function fetchLikedFridgeList() {
	
	// 냉장고 작성 모달
	const modal = document.getElementById('writeUserFridgeModal');
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const response = await instance.get(`/users/${id}/liked-fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			// 렌더링
			renderFridgeList(response.data.data, fetchLikedFridgeList);
			modal.querySelector('.fridge_total').textContent = `총 ${response.data.data.length}개`;
			modal.querySelector('.tab_button_wrap').style.display = 'flex';
			modal.querySelector('.btn_wrap').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').style.display = '';
			}
		}
	}
	catch(error) {
		const code = error?.response?.data?.code;
				
		if (code === 'FRDIGE_READ_LIST_FAIL') {
			alertDanger('작성한 냉장고 목록 조회에 실패했습니다.');
		}
		if (code === 'LIKE_READ_LIST_FAIL') {
			alertDanger('좋아요 목록 조회에 실패했습니다.');
		}
		else if (code === 'FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'LIKE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'FRIDGE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
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





/**
 * 서버에서 사용자가 작성한 냉장고 목록을 가져오는 함수
 */
async function fetchCreatedFridgeList() {
	
	// 냉장고 작성 모달
	const modal = document.getElementById('writeUserFridgeModal');

	// 작성한 냉장고 목록 조회
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const response = await instance.get(`/users/${id}/created-fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			// 렌더링
			renderFridgeList(response.data.data, fetchCreatedFridgeList);
			modal.querySelector('.fridge_total').textContent = `총 ${response.data.data.length}개`;
			modal.querySelector('.tab_button_wrap').style.display = 'flex';
			modal.querySelector('.btn_wrap').style.display = 'flex';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').style.display = '';
			}
		}
	}
	catch(error) {
		const code = error?.response?.data?.code;
		
		if (code === 'FRDIGE_READ_LIST_FAIL') {
			alertDanger('작성한 냉장고 목록 조회에 실패했습니다.');
		}
		else if (code === 'FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'FRIDGE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
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





/**
 * 냉장고 목록을 화면에 렌더링하는 함수
 */
function renderFridgeList(fridgeList, fetchFunction) {
	
	const modal = document.getElementById('writeUserFridgeModal');
	const container = modal.querySelector('.fridge_list');
	container.innerHTML = '';
	
	fridgeList.forEach(fridge => {
		const li = document.createElement('li');
		li.addEventListener('click', function(event) {
			event.preventDefault();
			closeWriteUserFridgeSheet();
			openWriteUserFridgeSheet(fridge);
		});
		
		// 냉장고 정보
		const div = document.createElement('div');
		div.className = 'fridge';
		
		const img = document.createElement('span');
		img.classList = 'fridge_image';
		img.style.backgroundImage = `url("${fridge.image}")`;
		
		const info = document.createElement('div');
		const h3 = document.createElement('h3');
		h3.textContent = fridge.name;
		const p = document.createElement('p');
		p.textContent = fridge.category;
		
		info.append(h3, p);
		div.append(img, info);
		
		// 즐겨찾기 버튼
		const likeBtn = document.createElement('img');
		likeBtn.classList = 'fridge_like';
		likeBtn.src = fridge.liked ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
		likeBtn.addEventListener('click', function (event) {
			event.preventDefault();
			event.stopPropagation();
			closeWriteUserFridgeSheet();
			likeFridge(fridge, fetchFunction);
		});
		
		// 삭제 버튼
		const deleteBtn = document.createElement('button');
		deleteBtn.classList = 'fridge_delete';
		deleteBtn.textContent = '삭제';
		deleteBtn.addEventListener('click', function (event) {
			event.preventDefault();
			event.stopPropagation();
			closeWriteUserFridgeSheet();
			deleteFridge(fridge.id);
		});
		
		// 현재 모드에 맞게 버튼 표시
		if (mode === 'delete') {
			likeBtn.style.display = 'none';
			deleteBtn.style.display = 'inline-block';
		}
		else {
			likeBtn.style.display = 'inline-block';
			deleteBtn.style.display = 'none';
		}
		
		li.append(div, likeBtn, deleteBtn);
		container.appendChild(li);
	});
}





/**
 * 냉장고 목록 삭제 모드를 토글하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const toggleDeleteModeButton = document.getElementById('toggleDeleteModeButton');
	
	toggleDeleteModeButton.addEventListener('click', function () {
		mode = (mode === 'delete') ? 'like' : 'delete';
		toggleDeleteModeButton.textContent = (mode === 'delete') ? '완료' : '관리';
		fetchCreatedFridgeList();
	});
});





/**
 * 사용자 냉장고 작성 시트를 여는 함수
 */
function openWriteUserFridgeSheet(fridge) {
	
	const form = document.getElementById('writeUserFridgeForm');
	const sheet = document.getElementById('fridgeSheet');
	
	form.querySelector('.sheet_title').textContent = fridge.name;
	form.querySelector('.sheet_category').textContent = fridge.category;
	form.fridgeId.value = fridge.id;
	
	sheet.classList.add('is-open');
}





/**
 * 사용자 냉장고 작성 시트를 닫는 함수
 */
function closeWriteUserFridgeSheet() {
	
	const form = document.getElementById('writeUserFridgeForm');
	const sheet = document.getElementById('fridgeSheet');
	
	form.reset();
	form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
	form.querySelectorAll('.danger').forEach(el => (el.style.display = 'none'));
	
	sheet.classList.remove('is-open');
}





/**
 * 사용자 냉장고 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 사용자 냉장고 작성 폼
	const form = document.getElementById('writeUserFridgeForm');
	
	// 양 실시간 검사
	form.amount.addEventListener('blur', function() {
		const isAmountEmpty = form.amount.value.trim() === '';
		const isAmountNotMatch = !form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
		this.classList.toggle('is-invalid', isAmountEmpty || isAmountNotMatch);
		document.getElementById('sheetAmountHint1').style.display = isAmountEmpty ? 'block' : 'none';
		document.getElementById('sheetAmountHint2').style.display = !isAmountEmpty && isAmountNotMatch ? 'block' : 'none';
	});
	
	// 유효기간 실시간 검사
	form.expdate.addEventListener('input', function() {
		const isExpdateEmpty = form.expdate.value.trim() === '';
		this.classList.toggle('is-invalid', isExpdateEmpty);
		form.querySelector('.expdate_field p.danger').style.display = isExpdateEmpty ? 'block' : 'none';
	});
	
	// 냉장고 시트 닫기 동작
	document.getElementById('sheetCloseButton').addEventListener('click', function(event) {
		event.preventDefault();
		event.stopPropagation();
		closeWriteUserFridgeSheet();
	});
});





/**
 * 사용자 냉장고를 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 사용자 냉장고 작성 폼
	const form = document.getElementById('writeUserFridgeForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검사
		if (form.expdate.value.trim() === '') {
			form.expdate.classList.add('is-invalid');
			form.querySelector('.expdate_field p').style.display = 'block';
			form.expdate.focus();
			isValid = false;
		}
		
		const match = form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
		if (form.amount.value.trim() === '') {
			form.amount.classList.add('is-invalid');
			document.getElementById('sheetAmountHint1').style.display = 'block';
			form.amount.focus();
			isValid = false;
		}
		else if (!match) {
			form.amount.classList.add('is-invalid');
			document.getElementById('sheetAmountHint2').style.display = 'block';
			form.amount.focus();
			isValid = false;
		}
		
		// 사용자 냉장고 작성
		if (isValid) { 
			try {
				const id = parseJwt(localStorage.getItem('accessToken')).id;
				
				const data = {
				    amount: match[1],
				    unit: match[2],
				    expdate: form.expdate.value.trim(),
					fridge_id: form.fridgeId.value
				}
				
				const response = await instance.post(`/users/${id}/fridges`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'USER_FRIDGE_CREATE_SUCCESS') {
					bootstrap.Modal.getInstance(document.getElementById('writeUserFridgeModal'))?.hide();
					fetchUserFridgeList();
				}
			}
			catch(error) {
				const code = error?.response?.data?.code;
				
				if (code === 'USER_FRIDGE_CREATE_FAIL') {
					alertDanger('사용자 냉장고 작성에 실패했습니다.');
				}
				else if (code === 'USER_FRIDGE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_FRIDGE_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'USER_FRIDGE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
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
		
	});
	
});





/**
 * 냉장고를 삭제하는 함수
 */
async function deleteFridge(id) {
	
	// 냉장고 삭제
	try {
		const response = await instance.delete(`/fridges/${id}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'FRIDGE_DELETE_SUCCESS') {
			fetchCreatedFridgeList();
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
						
		if (code === 'FRIDGE_DELETE_FAIL') {
			alertDanger('냉장고 삭제에 실패했습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
	
}






/**
 * 냉장고에 좋아요를 표시하는 함수
 */
async function likeFridge(fridge, fetchFunction) {
	
	// 좋아요 취소
	if (fridge.liked) {
		try {
			const data = {
				tablename: 'fridge',
				recodenum: fridge.id,
			}

			const response = await instance.delete(`/fridges/${fridge.id}/likes`, {
				data: data,
				headers: getAuthHeaders()
			});

			if (response.data.code === 'FRIDGE_UNLIKE_SUCCESS') {
				fetchFunction();
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
						
			if (code === 'FRIDGE_UNLIKE_FAIL') {
				alertDanger('냉장고 좋아요 취소에 실패했습니다.');
			}
			else if (code === 'LIKE_DELETE_FAIL') {
				alertDanger('냉장고 좋아요 취소에 실패했습니다.');
			}
			else if (code === 'FRIDGE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'LIKE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'FRIDGE_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'LIKE_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'FRIDGE_NOT_FOUND') {
				alertDanger('해당 냉장고를 찾을 수 없습니다.');
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
	// 좋아요
	else {
		try {
			const data = {
				tablename: 'fridge',
				recodenum: fridge.id,
			}
			
			const response = await instance.post(`/fridges/${fridge.id}/likes`, data, {
				headers: getAuthHeaders()
			});
	
			if (response.data.code === 'FRIDGE_LIKE_SUCCESS') {
				fetchFunction();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
						
			if (code === 'FRIDGE_LIKE_FAIL') {
				alertDanger('냉장고 좋아요에 실패했습니다.');
			}
			else if (code === 'LIKE_CREATE_FAIL') {
				alertDanger('냉장고 좋아요에 실패했습니다.');
			}
			else if (code === 'FRIDGE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'LIKE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'FRIDGE_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'FRIDGE_NOT_FOUND') {
				alertDanger('해당 냉장고를 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'LIKE_DUPLICATE') {
				alertDanger('이미 등록된 내용입니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
	
}