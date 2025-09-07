/**
 * 사용자 냉장고 수정 폼값을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {

	// 사용자 냉장고 수정 폼
	const form = document.getElementById('editUserFridgeForm');
	
	// 양
	form.amount.addEventListener('input', function() {
		const isAmountEmpty = this.value.trim() === '';
		form.amount.classList.toggle('is-invalid', isAmountEmpty);
		form.querySelector('.amount_field p').style.display = isAmountEmpty ? 'block' : 'none';
	});
	
	// 유통기간
	form.expdate.addEventListener('input', function() {
		const isExpdateEmpty = this.value.trim() === '';
		form.expdate.classList.toggle('is-invalid', isExpdateEmpty);
		form.querySelector('.expdate_field p').style.display = isExpdateEmpty ? 'block' : 'none';
	});
	
});