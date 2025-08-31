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
	searchBox.querySelector('.search_btn')?.addEventListener('click', function () {
		keyword = searchBox.querySelector('.search_word')?.value.trim();
		fetchFridgeList();
	});
	
	// 엔터 검색
	searchBox.querySelector('.search_word')?.addEventListener('keydown', function (event) {
		if (event.isComposing || event.key === 'Process') return;
		if (event.key === 'Enter') {
			event.preventDefault();
			keyword = searchBox.querySelector('.search_word').value.trim();
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
			renderFridgeList(result.data.content);
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
		
		const response = await instance.get(`/users/${id}/add-fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			
			// 렌더링
			renderFridgeList(response.data.data);
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
			renderFridgeList(response.data.data);
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
function renderFridgeList(fridgeList) {
	const container = document.querySelector('#addUserFridgeForm .fridge_list');
	container.innerHTML = '';
	
	fridgeList.forEach(fridge => {
		const li = document.createElement('li');
		
		const box = document.createElement('div');
		box.className = 'fridge';
		
		const img = document.createElement('img');
		img.classList = 'fridge_image';
		img.src = fridge.image;
		
		const info = document.createElement('div');
		const h3 = document.createElement('h3');
		h3.textContent = fridge.name;
		const p = document.createElement('p');
		p.textContent = fridge.category;
		
		info.append(h3, p);
		box.append(img, info);
		
		const btn = document.createElement('img');
		btn.classList = 'fridge_like';
		btn.src = fridge.liked ? '/images/recipe/star.png' : '/images/recipe/star_empty.png';
		// btn.onclick = () => addFridge(fridge); // 필요한 액션 바인딩
		
		li.append(box, btn);
		container.appendChild(li);
	});
}