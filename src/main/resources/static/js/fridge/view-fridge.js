/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	if (!isLoggedIn()) {
		redirectToLogin('/');
		return;
	}

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		console.log(error);
	}
});



/**
 * 
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	const token = localStorage.getItem('accessToken');
	const headers = {
		'Content-Type': 'application/json'
	};
	
	if (isLoggedIn()) {
		headers['Authorization'] = `Bearer ${token}`;
	}
	
	try {
		const response = await instance.get('/fridges', { headers: headers });
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			createIngredientList(response.data.data);
			createIngredientSwiper(response.data.data);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		const message = error?.response?.data?.message;
		
		if (code === 'FRIDGE_READ_LIST_FAIL') {
			alert(message);
		}
		else {
			console.log('서버 요청 중 오류 발생');
		}
	}
	
});



/**
 * 
 */
function createIngredientList(list) {
	const tbody = document.querySelector('.ingredient_body');
	tbody.innerHTML = '';

	list.forEach(item => {
		const tr = document.createElement('tr');
		tr.dataset.id = item.id;

		const nameTd = document.createElement('td');
		nameTd.textContent = item.name;

		const amountTd = document.createElement('td');
		amountTd.textContent = item.amount + item.unit;
		
		const dateTd = document.createElement('td');
		const date = new Date(item.expdate);
		dateTd.textContent = `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;

		const categoryTd = document.createElement('td');
		categoryTd.textContent = item.category;

		const deleteTd = document.createElement('td');
		const deleteBtn = document.createElement('button');
		deleteBtn.className = 'delete_btn';
		deleteTd.addEventListener('click', function(event) {
			event.preventDefault();
			deleteFridge(item.id);
		});

		const deleteImg = document.createElement('img');
		deleteImg.src = '/images/fridge/close.png';

		deleteBtn.appendChild(deleteImg);
		deleteTd.appendChild(deleteBtn);

		tr.append(nameTd, amountTd, dateTd, categoryTd, deleteTd);
		tbody.appendChild(tr);
	});
	
}



/**
 * 냉장고 목록 데이터를 카테고리별로 분류하여 화면에 렌더링하는 함수
 */
function createIngredientSwiper(list) {
	
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
 * 냉장고 재료를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('addIngredientForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.image.value.trim() === '') {
			document.querySelector('.image_field .select_img').classList.add('danger');
			document.querySelector('.image_field p.danger').style.display = 'block';
			form.image.focus();
			isValid = false;
		}
		
		if (form.category.value.trim() === '') {
			document.querySelector('.category_field select').classList.add('danger');
			document.querySelector('.category_field p').style.display = 'block';
			form.amount.focus();
			isValid = false;
		}
		
		if (form.amount.value.trim() === '') {
			form.amount.classList.add('danger');
			document.querySelector('.amount_field p').style.display = 'block';
			form.amount.focus();
			isValid = false;
		}
		
		if (form.expdate.value.trim() === '') {
			form.expdate.classList.add('danger');
			document.querySelector('.expdate_field p').style.display = 'block';
			form.expdate.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === '') {
			form.name.classList.add('danger');
			document.querySelector('.name_field p').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		if (isValid) {
			if (!isLoggedIn()) {
				redirectToLogin();
			}
			
			try {
				const token = localStorage.getItem('accessToken');
				const headers = {
					'Content-Type': 'application/json'
				};
				if (isLoggedIn()) {
					headers['Authorization'] = `Bearer ${token}`;
				}
				
				const rawAmount = form.amount.value;
				const parsed = rawAmount.match(/^(\d+)(.*)$/);
				
				const data = {
					image: form.image.value,
					name: form.name.value,
					amount: parsed ? parsed[1].trim() : '',
					unit: parsed ? parsed[2].trim() : '',
					expdate: form.expdate.value,
					category: form.category.value
				}
				
				console.log(data);
				
				const response = await instance.post(`/fridges`, data, {
					headers: headers
				});
				
				if (response.data.code === 'FRIDGE_CREATE_SUCCESS') {
					// 모달 초기화 및 닫기
					form.reset();
					bootstrap.Modal.getInstance(document.getElementById('addIngredientModal')).hide();
					
					// 재랜더링
					const list = await instance.get('/fridges');
					if (list.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
						createIngredientList(list.data.data);
						createIngredientSwiper(list.data.data);
					}
					else {
						location.reload();
					}
				}
				
			}
			catch (error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;

				if (code === 'FRIDGE_CREATE_FAIL') {
					alert(message);
				}
				else if (code === 'FRIDGE_INVALID_INPUT') {
					alert(message);
				}
				else if (code === 'USER_NOT_FOUND') {
					alert(message);
				}
				else if (code === 'FRIDGE_UNAUTHORIZED') {
					alert(message);
				}
				else if (code === 'FRIDGE_FORBIDDEN') {
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
	});
});





/**
 * 냉장고 재료를 삭제하는 함수
 */
async function deleteFridge(id) {
	
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
				content.insertAdjacentElement('afterend', renderListEmpty());
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
 * 냉장고 파먹기 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'pick_list_empty';
	
    const span = document.createElement('span');
    span.textContent = '냉장고 속 재료로 만들 수 있는 요리가 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}





