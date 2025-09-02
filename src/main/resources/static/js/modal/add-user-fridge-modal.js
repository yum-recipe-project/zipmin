/**
 * 전역 변수
 */
let keyword = '';




/**
 * ***** TODO : 모달 초기화
 */






/**
 * 냉장고 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 클릭 검색
	const searchBox = document.querySelector('.search_box[data-type="fridge"]');
	searchBox.addEventListener('click', function(event) {
		event.preventDefault();
		closeFridgeSheet();
	});
	searchBox.querySelector('.search_btn')?.addEventListener('click', function () {
		keyword = searchBox.querySelector('.search_word')?.value.trim();
		closeFridgeSheet();
		fetchFridgeList();
	});
	
	// 엔터 검색
	searchBox.querySelector('.search_word')?.addEventListener('keydown', function (event) {
		if (event.isComposing || event.key === 'Process') return;
		if (event.key === 'Enter') {
			event.preventDefault();
			keyword = searchBox.querySelector('.search_word').value.trim();
			closeFridgeSheet();
			fetchFridgeList();
		}
	});
	
	// 검색창 빈 경우 초기화
	searchBox.querySelector('.search_word')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchLikeFridgeList();
		}
	});
	
	// 탭 클릭 이벤트 설정
	const tabItems = document.querySelectorAll('.tab_button');
	tabItems.forEach((item) => {
		item.addEventListener('click', function(event) {
			event.preventDefault();
			closeFridgeSheet();
			
			tabItems.forEach(button => button.classList.remove('active'));
			this.classList.add('active');
			
			if (this.dataset.tab === 'like-fridge') {
				fetchLikeFridgeList();
			}
			else if (this.dataset.tab === 'add-fridge') {
				fetchAddFridgeList();
			}
		});
	});
	
	// 초기 실행
	tabItems.forEach(button => button.classList.remove('active'));
	tabItems[0].classList.add('active');
	fetchLikeFridgeList();
});





/**
 * 서버에서 냉장고 목록을 가져오는 함수
 */
async function fetchFridgeList() {
	
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
			document.querySelector('#addUserFridgeForm .fridge_total').textContent = `총 ${result.data.totalElements}개`;
			document.querySelector('#addUserFridgeForm .tab_button_wrap').style.display = 'none';
			document.querySelector('#addUserFridgeForm .btn-dark').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const content = document.querySelector('#addUserFridgeForm .fridge_list');
				content.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = '';
			}
		}
		
		// ***** TODO : 에러코드 추가 *****
	}
	catch(error) {
		console.log(error);
	}
}





/**
 * 서버에서 사용자가 등록한 냉장고 목록을 가져오는 함수
 */
async function fetchAddFridgeList() {

	try {
		const token = localStorage.getItem('accessToken');
		const id = parseJwt(token).id;
		
		const response = await instance.get(`/users/${id}/created-fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			
			// 렌더링
			renderFridgeList(response.data.data, fetchAddFridgeList);
			document.querySelector('#addUserFridgeForm .fridge_total').textContent = `총 ${response.data.data.length}개`;
			document.querySelector('#addUserFridgeForm .tab_button_wrap').style.display = 'flex';
			document.querySelector('#addUserFridgeForm .btn-dark').style.display = 'block';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const content = document.querySelector('#addUserFridgeForm .fridge_list');
				content.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = '';
			}
		}
		
		// ***** TODO : 에러코드 추가 *****
	}
	catch(error) {
		console.log(error);
	}

}





/**
 * 서버에서 사용자가 즐겨찾기한 냉장고 목록을 가져오는 함수
 */
async function fetchLikeFridgeList() {
	
	try {
		const token = localStorage.getItem('accessToken');
		const id = parseJwt(token).id;
		
		const response = await instance.get(`/users/${id}/liked-fridges`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			
			// 렌더링
			renderFridgeList(response.data.data, fetchLikeFridgeList);
			document.querySelector('#addUserFridgeForm .fridge_total').textContent = `총 ${response.data.data.length}개`;
			document.querySelector('#addUserFridgeForm .tab_button_wrap').style.display = 'flex';
			document.querySelector('#addUserFridgeForm .btn-dark').style.display = 'none';
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const content = document.querySelector('#addUserFridgeForm .fridge_list');
				content.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('#addUserFridgeForm .fridge_list').style.display = '';
			}
		}
		
		// ***** TODO : 에러코드 추가 *****
	}
	catch(error) {
		console.log(error);
	}
}






/**
 * 냉장고 목록을 화면에 렌더링하는 함수
 */
function renderFridgeList(fridgeList, fetchFunction) {
	const container = document.querySelector('#addUserFridgeForm .fridge_list');
	container.innerHTML = '';
	
	fridgeList.forEach(fridge => {
		const li = document.createElement('li');
		li.addEventListener('click', function(event) {
			event.preventDefault();
			closeFridgeSheet();
			if (fetchFunction == fetchAddFridgeList) {
				openFridgeSheet(fridge, true);
			}
			else {
				openFridgeSheet(fridge, false);
			}
		});
		
		const box = document.createElement('div');
		box.className = 'fridge';
		
		const img = document.createElement('span');
		img.classList = 'fridge_image';
		img.style.backgroundImage = `url("${fridge.image}")`;
		
		const info = document.createElement('div');
		const h3 = document.createElement('h3');
		h3.textContent = fridge.name;
		const p = document.createElement('p');
		p.textContent = fridge.category;
		
		info.append(h3, p);
		box.append(img, info);
		
		const btn = document.createElement('img');
		btn.classList = 'fridge_like';
		btn.src = fridge.liked ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			event.stopPropagation();
			closeFridgeSheet();
			createFridgeLike(fridge, fetchFunction);
		});
		
		li.append(box, btn);
		container.appendChild(li);
	});
}





/**
 * 냉장고 시트를 여는 함수
 */
function openFridgeSheet(fridge, isShow) {
	const sheet = document.getElementById('fridgeSheet');
	if (!sheet) return;
	
	sheet.querySelector('.sheet_title').textContent = fridge.name;
	sheet.querySelector('.sheet_category').textContent = fridge.category;
	document.getElementById('deleteFridgeBtn').style.display = isShow ? 'block' : 'none';
	
	// 폼 초기화
	document.getElementById('sheetAmount').value = '';
	document.getElementById('sheetExpdate').value = '';
	document.getElementById('sheetExpdate').value = '';
	
	// 에러 힌트 초기화
	sheet.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
	sheet.querySelectorAll('.danger').forEach(el => (el.style.display = 'none'));
	
	// 표시
	sheet.classList.add('is-open');
	sheet.setAttribute('aria-hidden', 'false');
}





/**
 * 냉장고 시트를 닫는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	// 버튼 클릭시 모달 닫기
	document.getElementById('sheetCloseBtn').addEventListener('click', function(event) {
		event.preventDefault();
		event.stopPropagation();
		closeFridgeSheet();
	});
	
	// 모달 닫히면 시트 닫기
	document.getElementById('addUserFridgeModal')?.addEventListener('hidden.bs.modal', closeFridgeSheet);
	document.getElementById('addFridgeModal')?.addEventListener('hidden.bs.modal', closeFridgeSheet);
	
});

function closeFridgeSheet() {
	const sheet = document.getElementById('fridgeSheet');
	if (!sheet) return;
	
	sheet.classList.remove('is-open');
	sheet.setAttribute('aria-hidden', 'true');
}





/**
 * 
 */
async function createFridgeLike(fridge, fetchFunction) {
	
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
				alertPrimary('냉장고 좋아요 취소에 성공했습니다.');
				fetchFunction();
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
			
			/**** TODO : 에러코드 추가하기 ****/
			
			if (code === 'FRIDGE_UNLIKE_FAIL') {
				alertDanger('냉장고 좋아요 취소에 실패했습니다.');
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
				alertPrimary('냉장고 좋아요에 성공했습니다.');
				fetchFunction();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
						
			/**** TODO : 에러코드 추가하기 ****/
			
			if (code === 'FRIDGE_LIKE_FAIL') {
				alertDanger('냉장고 좋아요에 실패했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
	
}







