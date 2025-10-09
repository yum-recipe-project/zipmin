/**
 * 쿠킹클래스를 신청하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const applyClassReasonInput = document.getElementById('applyClassReasonInput');
	const applyClassButton = document.getElementById('applyClassButton');
	
	applyClassReasonInput.addEventListener('input', function() {
		const isClassReasonEmpty = applyClassReasonInput.value.trim() === "";
		applyClassButton.disabled = isClassReasonEmpty;
		applyClassButton.classList.toggle('btn-disable', isClassReasonEmpty);
	});
	
});





/**
 * 쿠킹클래스를 지원하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('applyClassForm');

	form.addEventListener('submit', async function(event) {
		event.preventDefault();
	
		if (!isLoggedIn()) {
			redirectToLogin();
		}
		
		try {
			const token = localStorage.getItem('accessToken');

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const data = {
				reason: form.reason.value.trim(),
				question: form.question.value.trim(),
				class_id: form.id.value
			};
			
			console.log(data);
			
			const response = await instance.post(`/classes/${form.id.value}/applies`, data, {
				headers: headers
			});
			
			if (response.data.code === 'CLASS_APPLY_CREATE_SUCCESS') {
				alert('쿠킹클래스 신청이 완료되었습니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal')).hide();
				fetchClass();
			}

		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'CLASS_APPLY_CREATE_FAIL') {
				alert('쿠킹클래스 신청에 실패했습니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal')).hide();
			}	
			else if (code === 'CLASS_APPLY_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'CLASS_UNAUTHORIZED_ACCESS') {
				alert('로그인되지 않은 사용자입니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal')).hide();
			}
			else if (code === 'CLASS_APPLY_DUPLICATE') {
				alert('이미 신청한 클래스입니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal')).hide();
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부 오류가 발생했습니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal')).hide();
			}
			else {
				console.log(error);
			}
		}
		
	});

});