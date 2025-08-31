/**
 * 냉장고 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	// 폼
	const form = document.getElementById('addFridgeForm');
	
	// 이미지 선택 영역 토글
	document.querySelector('#addFridgeForm .select_btn').addEventListener('click', function () {
		const isVisible = document.querySelector('#addFridgeForm .image_list').style.display === 'block';
		document.querySelector('#addFridgeForm .image_list').style.display = isVisible ? 'none' : 'block';
	});

	// 이미지 선택 시 적용
	document.querySelectorAll('#addFridgeForm .image_list button').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			const src = this.querySelector('img').src;
			document.querySelector('#addFridgeForm .select_img').style.backgroundImage = `url("${src}")`;
			document.querySelector('#addFridgeForm .select_img').style.backgroundColor = '#F1F6FD';
			document.querySelector('#addFridgeForm .select_img').style.backgroundSize = '65%';
			document.querySelector('#addFridgeForm .select_img').style.backgroundPosition = 'center';
			document.querySelector('#addFridgeForm .select_img').style.backgroundRepeat = 'no-repeat';
			document.querySelector('#addFridgeForm .select_img').style.borderColor = '#D7DBE6';
			document.querySelector('#addFridgeForm .image_list').style.display = 'none';
			
			form.image.value = src.substring(src.indexOf('/images')); ;
			const isImageEmpty = form.image.value.trim() === '';
			document.querySelector('#addFridgeForm .image_field p.danger').style.display = isImageEmpty ? 'block' : 'none';
		});
	});
	
	// 재료 이름 실시간 검사
	form.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isNameEmpty);
		document.querySelector('#addFridgeForm .name_field p').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 카테고리 실시간 검사
	document.querySelector('#addFridgeForm .category_field select').addEventListener('change', function() {
		form.category.value = this.value;
		const isCategoryEmpty = form.category.value.trim() === '';
		document.querySelector('#addFridgeForm .category_field select').classList.toggle('is-invalid', isCategoryEmpty);
		document.querySelector('#addFridgeForm .category_field p').style.display = isCategoryEmpty ? 'block' : 'none';
	});
});






/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('addFridgeModal');
	modal.addEventListener('hidden.bs.modal', function() {
		
		// 폼 초기화
		const form = document.getElementById('addFridgeForm');
		if (form) form.reset()
		
		// 유효성 표시 제거
		modal.querySelectorAll('.is-invalid').forEach(el => 
			{el.classList.remove('is-invalid')
		});
		modal.querySelectorAll('.danger').forEach(p => {
			p.style.display = 'none'
		});
		
		// 카테고리 초기화
		form.querySelector('input[name="category"]').value = '';
		
		// 이미지 초기화
		form.querySelector('.image_field .select_img').removeAttribute('style');
		form.querySelector('input[name="image"]').value = '';
		document.querySelector('#addFridgeForm .image_list').style.display = 'none';
	});
});





/**
 * 냉장고를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('addFridgeForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검사
		if (form.image.value.trim() === '') {
			document.querySelector('#addFridgeForm .image_field .select_img').classList.add('is-invalid');
			document.querySelector('#addFridgeForm .image_field p.danger').style.display = 'block';
			form.image.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === '') {
			form.name.classList.add('is-invalid');
			document.querySelector('#addFridgeForm .name_field p').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		if (form.category.value.trim() === '') {
			document.querySelector('#addFridgeForm .category_field select').classList.add('is-invalid');
			document.querySelector('#addFridgeForm .category_field p').style.display = 'block';
			form.category.focus();
			isValid = false;
		}
		
		// 냉장고 생성
		if (isValid) {
			try {
				const data = {
					image: form.image.value.trim(),
					name: form.name.value.trim(),
					category: form.category.value.trim()
				}
				
				const response = await instance.post(`/fridges`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'FRIDGE_CREATE_SUCCESS') {
					alertPrimary('냉장고 작성에 성공했습니다.');
					bootstrap.Modal.getInstance(document.getElementById('addFridgeModal'))?.hide();
					bootstrap.Modal.getInstance(document.getElementById('addUserFridgeModal'))?.show();
					fetchAddFridgeList();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'FRIDGE_CREATE_FAIL') {
					alertDanger('냉장고 작성에 실패했습니다.');
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
	});
});












