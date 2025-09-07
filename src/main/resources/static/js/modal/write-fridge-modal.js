/**
 * 냉장고 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 냉장고 작성 폼
	const form = document.getElementById('writeFridgeForm');
	
	// 이미지 선택 영역 토글
	form.querySelector('.select_btn').addEventListener('click', function () {
		const isVisible = form.querySelector('.image_list').style.display === 'block';
		form.querySelector('.image_list').style.display = isVisible ? 'none' : 'block';
	});

	// 이미지 선택 시 적용
	form.querySelectorAll('.image_list button').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			const src = this.querySelector('img').src;
			form.querySelector('.select_img').style.backgroundImage = `url("${src}")`;
			form.querySelector('.select_img').style.backgroundColor = '#F1F6FD';
			form.querySelector('.select_img').style.backgroundSize = '65%';
			form.querySelector('.select_img').style.backgroundPosition = 'center';
			form.querySelector('.select_img').style.backgroundRepeat = 'no-repeat';
			form.querySelector('.select_img').style.borderColor = '#D7DBE6';
			form.querySelector('.image_list').style.display = 'none';
			
			form.image.value = src.substring(src.indexOf('/images')); ;
			const isImageEmpty = form.image.value.trim() === '';
			form.querySelector('.image_field p.danger').style.display = isImageEmpty ? 'block' : 'none';
		});
	});
	
	// 재료 이름 실시간 검사
	form.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isNameEmpty);
		form.querySelector('.name_field p').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 카테고리 실시간 검사
	form.querySelector('.category_field select').addEventListener('change', function() {
		form.category.value = this.value;
		const isCategoryEmpty = form.category.value.trim() === '';
		form.querySelector('.category_field select').classList.toggle('is-invalid', isCategoryEmpty);
		form.querySelector('.category_field p').style.display = isCategoryEmpty ? 'block' : 'none';
	});
	
	// 보관 장소 실시간 검사
	form.querySelectorAll('input[name="zoneRadio"]').forEach(radio => {
		radio.addEventListener('change', function() {
			form.zone.value = this.value;
			const isZoneEmpty = form.zone.value.trim() === '';
			form.querySelector('.zone_field p').style.display = isZoneEmpty ? 'block' : 'none';
		});
	});
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 냉장고 작성 모달
	const modal = document.getElementById('writeFridgeModal');
	modal.addEventListener('hidden.bs.modal', function() {
		// 폼 초기화
		const form = document.getElementById('writeFridgeForm');
		if (form) form.reset()
		
		// 유효성 표시 제거
		form.querySelectorAll('.is-invalid').forEach(input => 
			{input.classList.remove('is-invalid')
		});
		form.querySelectorAll('.danger').forEach(p => {
			p.style.display = 'none'
		});

		// 이미지 초기화
		form.querySelector('.image_field .select_img').removeAttribute('style');
		form.querySelector('.image_list').style.display = 'none';
	});
	
});





/**
 * 냉장고를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 냉장고 작성 폼
	const form = document.getElementById('writeFridgeForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검사
		if (form.zone.value.trim() === '') {
			form.querySelector('.zone_field p').style.display = 'block';
			form.zone.focus();
			isValid = false;
		}

		if (form.category.value.trim() === '') {
			form.querySelector('.category_field select').classList.add('is-invalid');
			form.querySelector('.category_field p').style.display = 'block';
			form.category.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === '') {
			form.name.classList.add('is-invalid');
			form.querySelector('.name_field p').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		if (form.image.value.trim() === '') {
			form.querySelector('.image_field .select_img').classList.add('is-invalid');
			form.querySelector('.image_field p.danger').style.display = 'block';
			form.image.focus();
			isValid = false;
		}
		
		// 냉장고 생성
		if (isValid) {
			try {
				const data = {
					image: form.image.value.trim(),
					name: form.name.value.trim(),
					category: form.category.value.trim(),
					zone: form.zone.value.trim()
				}
				
				const response = await instance.post(`/fridges`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'FRIDGE_CREATE_SUCCESS') {
					alertPrimary('냉장고 작성에 성공했습니다.');
					bootstrap.Modal.getInstance(document.getElementById('writeFridgeModal'))?.hide();
					bootstrap.Modal.getInstance(document.getElementById('writeUserFridgeModal'))?.show();
					
					// *** TODO : 탭 2 활성화 ***
					
					fetchCreatedFridgeList();
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