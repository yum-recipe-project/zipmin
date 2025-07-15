/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		redirectToLogin();
	}

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin();
	}
});



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper', {
        direction: 'horizontal',
		
		slidesPerView: 'auto',
        spaceBetween: 12,
		
        scrollbar: {
            el: '.swiper-scrollbar',
            draggable: true, // 스크롤바 드래그 가능
        },
    });
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
		
		console.log(response);
		
		if (response.data.code === 'FRIDGE_READ_LIST_SUCCESS') {
			createIngredientList(response.data.data);
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
 * 냉장고 재료를 삭제하는 함수
 */
async function deleteFridge(id) {
	
	try {
		const data = {
			id: id
		};
		
		const response = await instance.delete(`/fridges/${id}`, {data});
		
		if (response.data.code === 'FRIDGE_DELETE_SUCCESS') {
			const fridgeEl = document.querySelector(`tr[data-id="${id}"]`);
			if (fridgeEl) {
				fridgeEl.remove();
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

