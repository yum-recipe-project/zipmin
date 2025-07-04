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
 * 비밀번호 입력 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
	
	form.password.addEventListener('input', function() {
		const isPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isPasswordEmpty);
		document.querySelector('.password_field p').style.display = isPasswordEmpty ? 'block' : 'none';
	});
});



/**
 * 비밀번호를 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검증
		if (form.password.value.trim() === '') {
			form.password.classList.add('danger');
			document.querySelector('.password_field p').style.display = 'block';
			form.password.focus();
			isValid = false;
		}
		
		// 비밀번호 검증
		if (isValid) {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				id: payload.id,
				password: form.password.value.trim()
			}
			
			try {
				const response = await instance.post('/users/verify-password', data);
				
				if (response.data.code === 'USER_PASSWORD_VERIFY_SUCCESS') {
					const path = sessionStorage.getItem('path') || '/';
					sessionStorage.setItem('passwordVerified', 'true');
					sessionStorage.removeItem('path');
					location.href = path;
				}
			}
			catch (error) {
				if (error.response?.data?.code === 'USER_PASSWORD_NOT_MATCH') {
					document.querySelector('.alert').style.display = 'block';
					form.password.value = '';
					form.password.focus();
				}
				else if (error.response?.data?.code === 'USER_INVALID_PARAM') {
					alert('입력값이 올바르지 않습니다.');
				}
				else {
					alert('문제가 발생했습니다. 다시 시도하세요.');
				}
			}
		}
	});
});