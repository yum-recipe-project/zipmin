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
 * 
 */
document.addEventListener('DOMContentLoaded', async function() {

	fetchUserFridgeList();
});





/**
 * 서버에서 나의 냉장고 목록을 가져오는 함수
 */
async function fetchUserFridgeList() {
	try {
		const token = localStorage.getItem('accessToken');
		const id = parseJwt(token).id;
		
		const response = await instance.get(`users/${id}/fridges`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'USER_FRIDGE_READ_LIST_SUCCESS') {
			// 렌더링
			renderUserFridgeList(response.data.data);
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('.fridge').style.display = 'none';
				document.querySelector('.fridge_list_empty')?.remove();
				const content = document.querySelector('.fridge');
				content.insertAdjacentElement('afterend', renderFridgeListEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.fridge_list_empty')?.remove();
				document.querySelector('.fridge').style.display = '';
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		/***** TODO : 에러 코드 수정하기 *****/
		
		if (code === 'USER_FRIDGE_READ_LIST_FAIL') {
			alert('냉장고 목록 조회에 실패했습니다');
		}
		else {
			console.log('서버 요청 중 오류 발생');
		}
	}
}





/**
 *  
 */
function renderUserFridgeList(userFridgeList) {
	const container = document.querySelector('.fridge_list');
	container.innerHTML = "";
	userFridgeList.forEach(userFridge => {
		const li = document.createElement('li');
		li.className = 'fridge';
		li.dataset.id = userFridge.id;
		
		const divInfo = document.createElement('div');
		divInfo.className = 'fridge_info';
		
		const spanImage = document.createElement('span');
		spanImage.className = 'image';
		spanImage.style.backgroundImage = `url("${userFridge.image}")`;
		
		const divDetail = document.createElement('div');
		divDetail.className = 'detail';
		
		const h4Name = document.createElement('h4');
		h4Name.className = 'name';
		h4Name.textContent = userFridge.name;
		// TODO : 보관 방법 표시
		h4Name.style.setProperty('--dot-color', 'red');
		
		const pCategory = document.createElement('p');
		pCategory.className = 'info';
		const bCat = document.createElement('b');
		bCat.textContent = '카테고리';
		const spanCat = document.createElement('span');
		spanCat.className = 'category';
		spanCat.textContent = userFridge.category;
		pCategory.append(bCat, document.createTextNode('  '), spanCat);
		
		const pExpdate = document.createElement('p');
		pExpdate.className = 'info';
		const bExp = document.createElement('b');
		bExp.textContent = '유통기한';
		const spanExp = document.createElement('span');
		spanExp.className = 'expdate';
		spanExp.textContent = `${formatDate(userFridge.expdate)}`;
		pExpdate.append(bExp, document.createTextNode('  '), spanExp);
		
		const tabWrap = document.createElement('div');
		tabWrap.className = 'tab_wrap';
		
		const btnEdit = document.createElement('button');
		btnEdit.className = 'btn_tab tab_sm';
		btnEdit.type = 'button';
		btnEdit.textContent = '수정';
		// btnEdit.dataset.id = item.id ?? "";
		// TODO : 수정 동작
		
		const btnDelete = document.createElement('button');
		btnDelete.className = 'btn_sort sort_sm';
		btnDelete.type = 'button';
		btnDelete.textContent = '삭제';
		// btnDelete.dataset.id = item.id ?? "";
		// TODO: 삭제 동작
		
		tabWrap.append(btnEdit, btnDelete);
		divDetail.append(h4Name, pCategory, pExpdate);
		divInfo.append(spanImage, divDetail, tabWrap);
		li.appendChild(divInfo);
		container.appendChild(li);
  });
}










/**
 * 냉장고 목록 데이터를 카테고리별로 분류하여 화면에 렌더링하는 함수
 */
function renderFridgeSwiper(list) {
	const container = document.getElementById('ingredient_swiper_container');
	container.innerHTML = '';

	const categories = ['육류', '채소류', '소스류'];
	const categoryIcons = {
		'육류': '/images/fridge/meat.png',
		'채소류': '/images/fridge/vegetables.png',
		'소스류': '/images/fridge/sauce.png'
	};
	const categoryMap = {};

	// 고정 카테고리 요소 생성
	categories.forEach(category => {
		const wrapper = document.createElement('div');
		wrapper.className = 'ingredient_swiper';

		const categoryDiv = document.createElement('div');
		categoryDiv.className = 'category';

		const categoryImg = document.createElement('div');
		categoryImg.className = 'ingredient_img';
		const img = document.createElement('img');
		img.src = categoryIcons[category] || '/images/fridge/default.png';
		categoryImg.appendChild(img);

		const categoryName = document.createElement('span');
		categoryName.textContent = category;

		categoryDiv.append(categoryImg, categoryName);

		const swiper = document.createElement('div');
		swiper.className = 'swiper';

		const swiperWrapper = document.createElement('div');
		swiperWrapper.className = 'swiper-wrapper';
		swiperWrapper.dataset.category = category;

		swiper.appendChild(swiperWrapper);
		wrapper.append(categoryDiv, swiper);
		container.appendChild(wrapper);

		categoryMap[category] = swiperWrapper;
	});

	// 재료 목록 렌더링
	list.forEach(item => {
		const wrapper = categoryMap[item.category];
		if (!wrapper) return;

		const slide = document.createElement('div');
		slide.className = 'swiper-slide';

		const ingredient = document.createElement('div');
		ingredient.className = 'ingredient';
		ingredient.dataset.id = item.id;

		const ingredientImg = document.createElement('div');
		ingredientImg.className = 'ingredient_img';

		const img = document.createElement('img');
		img.src = item.image || '/images/common/test.png';
		ingredientImg.appendChild(img);

		const nameSpan = document.createElement('span');
		nameSpan.textContent = item.name;

		const date = new Date(item.expdate);
		const dateP = document.createElement('p');
		dateP.textContent = `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;

		ingredient.append(ingredientImg, nameSpan, dateP);
		slide.appendChild(ingredient);

		wrapper.appendChild(slide);
	});

	// 스와이퍼 초기화
	document.querySelectorAll('.swiper').forEach(el => {
		new Swiper('.swiper', {
			slidesPerView: 'auto',
			spaceBetween: 12,
			scrollbar: {
				el: el.querySelector('.swiper-scrollbar'),
				draggable: true,
			},
		});
	});

}






/**
 * 냉장고 재료를 삭제하는 함수
 */
async function deleteUserFridge(id) {
	
	try {
		const data = {
			id: id
		};
		
		const response = await instance.delete(`/fridges/${id}`, {data});
		
		if (response.data.code === 'FRIDGE_DELETE_SUCCESS') {
			// 테이블에서 목록 삭제
			const row = document.querySelector(`tr[data-id='${id}']`);
			if (row) {
				row.remove();
			}

			// 스와이퍼에서 삭제
			const swiper = document.querySelector(`.ingredient[data-id='${id}']`);
			if (swiper) {
				const slide = swiper.closest('.swiper-slide');
				if (slide) {
					slide.remove();
				}
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		const message = error?.response?.data?.message;
		
		if (code === 'FRIDGE_DELETE_FAIL') {
			alert(message);
		}
		else if (code === 'FRIDGE_INVALID_INPUT') {
			alert(message);
		}
		else if (code === 'FRIDGE_UNAUTHORIZED') {
			alert(message);
		}
		else if (code === 'FRIDGE_FORBIDDEN') {
			alert(message);
		}
		else if (code === 'FRIDGE_NOT_FOUND') {
			alert(message);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alert(message);
		}
		else {
			console.log('서버 요청 중 오류 발생');
		}
	}
}









/**
 * 서버에서 냉장고 파먹기 목록을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		const response = await instance.get('/fridges/pick', {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_PICK_LIST_SUCCESS') {
			// 렌더링
			renderPickList(response.data.data);
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('.pick_title').style.display = 'none';
				document.querySelector('.pick_list').style.display = 'none';
				document.querySelector('.pick_list_empty')?.remove();
				const content = document.querySelector('.pick_list');
				content.insertAdjacentElement('afterend', renderPickListEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.pick_list_empty')?.remove();
				document.querySelector('.pick_title').style.display = '';
				docyment.querySelector('.pick_list').style.display = '';
			}
		}
	}
	catch (error) {
		
		/***** TODO : 에러코드 추가하기 *****/
	}
	
});





/**
 * 냉장고 재료로 만들 수 있는 레시피 목록을 화면에 렌더링하는 함수
 */
async function renderPickList(pickList) {
	const container = document.querySelector('.pick_list');
	container.innerHTML = '';
	
	if (pickList === null) return;
	
	pickList.forEach((recipe, index) => {
		const li = document.createElement('li');
		
		const recipeDiv = document.createElement('div');
		recipeDiv.className = 'recipe';

		const numberSpan = document.createElement('span');
		numberSpan.textContent = index + 1;

		const titleP = document.createElement('p');
		titleP.textContent = recipe.title;

		recipeDiv.appendChild(numberSpan);
		recipeDiv.appendChild(titleP);

		// 일치율 표시
		if (recipe.rate >= 90) {
			const flagP = document.createElement('p');
			flagP.className = 'flag';
			flagP.textContent = `${recipe.rate}% 일치`;
			recipeDiv.appendChild(flagP);
		}

		li.appendChild(recipeDiv);
		container.appendChild(li);
	});
}





/**
 * 나의 냉장고 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderFridgeListEmpty() {
	const wrapper = document.createElement('div');
	wrapper.className = 'fridge_empty_wrap';
	
    const div = document.createElement('div');
    div.className = 'fridge_list_empty';
	
    const span = document.createElement('span');
    span.textContent = '냉장고에 재료를 추가해보세요';
    div.appendChild(span);
	wrapper.appendChild(div);

    return wrapper;
}





/**
 * 냉장고 파먹기 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderPickListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'pick_list_empty';
	
    const span = document.createElement('span');
    span.textContent = '냉장고 속 재료로 만들 수 있는 요리가 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}





