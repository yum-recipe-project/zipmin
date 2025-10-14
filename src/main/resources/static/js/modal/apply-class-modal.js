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
	
		try {
			const data = {
				reason: form.reason.value.trim(),
				question: form.question.value.trim(),
				class_id: form.id.value
			};
			
			const response = await instance.post(`/classes/${form.id.value}/applies`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'CLASS_APPLY_CREATE_SUCCESS') {
				alert('쿠킹클래스 신청이 완료되었습니다.');
				form.reason.value = '';
				form.question.value = '';
				bootstrap.Modal.getInstance(document.getElementById('applyClassModal'))?.hide();
				fetchClass();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			form.reason.value = '';
			form.question.value = '';
			bootstrap.Modal.getInstance(document.getElementById('applyClassModal'))?.hide();
			
			if (code === 'CLASS_APPLY_CREATE_FAIL') {
				alertDanger('쿠킹클래스 신청에 실패했습니다.');
			}	
			else if (code === 'CLASS_APPLY_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'CLASS_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'CLASS_ALREADY_ENDED') {
				alertDanger('이미 종료된 쿠킹클래스입니다.')
			}
			else if (code === 'CLASS_APPLY_UNABLE') {
				alertDanger('최근 60일 내 결석이 3회를 초과하여 클래스 신청이 제한되었습니다.');
			}
			else if (code === 'CLASS_NOT_FOUND') {
				alertDanger('해당 클래스를 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'CLASS_APPLY_DUPLICATE') {
				alertDanger('이미 신청한 쿠킹클래스입니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	});
});