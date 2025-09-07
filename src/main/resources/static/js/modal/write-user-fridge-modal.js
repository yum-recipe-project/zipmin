/**
 * 전역 변수
 */
let keyword = '';





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
			
			if (this.dataset.tab === 'like-fridge') {
				fetchLikedFridgeList();
			}
			else if (this.dataset.tab === 'add-fridge') {
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
 * TODO : 모달 초기화
 */





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
			size: 100,
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
			modal.querySelector('.btn-dark').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
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
			modal.querySelector('.btn-dark').style.display = 'block';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
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
			modal.querySelector('.btn-dark').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				modal.querySelector('.fridge_list').style.display = 'none';
				modal.querySelector('.search_empty')?.remove();
				modal.querySelector('.fridge_list').insertAdjacentElement('afterend', renderSearchEmpty());
			}
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
 * 냉장고 목록을 화면에 렌더링하는 함수
 */
function renderFridgeList(fridgeList, fetchFunction) {
	
	// 컨테이너
	const modal = document.getElementById('writeUserFridgeModal');
	const container = modal.querySelector('.fridge_list');
	container.innerHTML = '';
	
	fridgeList.forEach(fridge => {
		const li = document.createElement('li');
		li.addEventListener('click', function(event) {
			event.preventDefault();
			closeWriteUserFridgeSheet();
			if (fetchFunction == fetchCreatedFridgeList) {
				openWriteUserFridgeSheet(fridge, true);
			}
			else {
				openWriteUserFridgeSheet(fridge, false);
			}
		});
		
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
		
		const btn = document.createElement('img');
		btn.classList = 'fridge_like';
		btn.src = fridge.liked ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			event.stopPropagation();
			closeWriteUserFridgeSheet();
			likeFridge(fridge, fetchFunction);
		});
		
		li.append(div, btn);
		container.appendChild(li);
	});
}





/**
 * 냉장고 시트를 여는 함수
 */
function openWriteUserFridgeSheet(fridge, isShow) {
	
	const form = document.getElementById('writeUserFridgeForm');
	const sheet = document.getElementById('fridgeSheet');
	if (!sheet) return;
	
	form.querySelector('.sheet_title').textContent = fridge.name;
	form.querySelector('.sheet_category').textContent = fridge.category;
	form.fridgeId.value = fridge.id;
	document.getElementById('deleteFridgeButton').style.display = isShow ? 'block' : 'none';
	
	// 폼 초기화
	form.amount.value = '';
	form.expdate.value = '';
	
	// 에러 힌트 초기화
	form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
	form.querySelectorAll('.danger').forEach(el => (el.style.display = 'none'));
	
	// 표시
	sheet.classList.add('is-open');
}





/**
 * 냉장고 시트를 닫는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	// 버튼 클릭시 모달 닫기
	document.getElementById('sheetCloseBtn').addEventListener('click', function(event) {
		event.preventDefault();
		event.stopPropagation();
		closeWriteUserFridgeSheet();
	});
	
	// 모달 닫히면 시트 닫기
	document.getElementById('addUserFridgeModal')?.addEventListener('hidden.bs.modal', closeFridgeSheet);
	document.getElementById('addFridgeModal')?.addEventListener('hidden.bs.modal', closeFridgeSheet);
	
});

function closeWriteUserFridgeSheet() {
	const sheet = document.getElementById('fridgeSheet');
	if (!sheet) return;
	
	sheet.classList.remove('is-open');
	sheet.setAttribute('aria-hidden', 'true');
}





/**
 * TODO : 실시간 검증 + 폼값 초기화
 */





/**
 * 사용자 냉장고를 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
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
 * 
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







