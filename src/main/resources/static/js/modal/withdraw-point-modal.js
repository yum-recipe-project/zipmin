/**
 * 포인트를 계산하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {

	const ownedPoint = document.getElementById('ownedPoint');
	const remainPoint = document.getElementById('remainPoint');
	const pointInput = document.getElementById('pointInput');
	const submitButton = document.querySelector('#withdrawPointForm button[type="submit"]');
	
	const userPoint = Number(document.querySelector('.support_point .point span:last-child').textContent.replace(/[^0-9]/g, ''));

	ownedPoint.innerText = userOwned.toLocaleString();

	pointInput.value = 1000;

	function validatePoint() {
		const inputPoint = parseInt(pointInput.value);
		
		if (isNaN(inputPoint) || inputPoint < 1000 || inputPoint > userPoint) {
			remainPoint.innerText = '-';
			submitButton.classList.add('disabled');
			submitButton.disabled = true;
			return;
		}
		
		remainPoint.innerText = (userPoint - inputPoint).toLocaleString();
		submitButton.classList.remove('disabled');
		submitButton.disabled = false;
	}

	pointInput.addEventListener('input', validatePoint);
	validatePoint();
});





/**
 * 출금을 요청하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('withdrawPointForm');
	
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const point = document.getElementById('pointInput');
		if (point.value.trim() === '') {
			// TODO : 입력값 처리
			point.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const userId = parseJwt(localStorage.getItem('accessToken')).id;
				
				const data = {
					point: parseInt(point.value)
				}
				
				const response = await instance.post(`/users/${userId}/withdraws`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'WITHDRAW_CREATE_SUCCESS') {
					alertPrimary('출금 요청에 성공했습니다');
					bootstrap.Modal.getInstance(document.getElementById('withdrawPointModal'))?.hide();
					fetchFundSum();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				if (code === 'WITHDRAW_CREATE_FAIL') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else if (code === 'WITHDRAW_INVALID_INPUT') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else if (code === 'WITHDRAW_UNAUTHORIZED_ACCESS') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else if (code === 'USER_NOT_FOUND') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('출금 요청에 실패했습니다');
				}
				else {
					alertDanger('출금 요청에 실패했습니다');
				}
			}
		}
	});
});
