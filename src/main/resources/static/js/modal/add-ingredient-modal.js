/**
 * 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const form = document.getElementById('addIngredientForm');

	// 이미지 선택 영역 토글
	document.querySelector('.select_btn').addEventListener('click', function () {
		const isVisible = document.querySelector('.image_list').style.display === 'block';
		document.querySelector('.image_list').style.display = isVisible ? 'none' : 'block';
	});
	
	// 이미지 선택 시 적용
	document.querySelectorAll('.image_list button').forEach((btn) => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			const src = this.querySelector('img').src;
			document.querySelector('.select_img').style.backgroundImage = `url("${src}")`;
			document.querySelector('.select_img').style.backgroundColor = '#f1f6fd';
			document.querySelector('.select_img').style.backgroundSize = '80%';
			document.querySelector('.select_img').style.backgroundPosition = 'center';
			document.querySelector('.select_img').style.backgroundRepeat = 'no-repeat';
			document.querySelector('.select_img').style.border = '#ddd';
			document.querySelector('.image_list').style.display = 'none';
			
			form.image.value = src.substring(src.indexOf('/images')); ;
			const isImageEmpty = form.image.value.trim() === '';
			document.querySelector('.image_field p.danger').style.display = isImageEmpty ? 'block' : 'none';
		});
	});
	
	// 재료 이름 실시간 검사
	form.name.addEventListener('blur', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNameEmpty);
		document.querySelector('.name_field p').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 소비기한 실시간 검사
	form.expdate.addEventListener('blur', function() {
		const isExpdateEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isExpdateEmpty);
		document.querySelector('.expdate_field p').style.display = isExpdateEmpty ? 'block' : 'none';
	});
	
	// 용량 실시간 검사
	form.amount.addEventListener('blur', function() {
		const isAmountEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isAmountEmpty);
		document.querySelector('.amount_field p').style.display = isAmountEmpty ? 'block' : 'none';
	});
	
	// 카테고리 실시간 검사
	document.querySelector('.category_field select').addEventListener('change', function() {
		form.category.value = this.value;
		const isCategoryEmpty = form.category.value.trim() === '';
		document.querySelector('.category_field select').classList.toggle('danger', isCategoryEmpty);
		document.querySelector('.category_field p').style.display = isCategoryEmpty ? 'block' : 'none';
	});
});
