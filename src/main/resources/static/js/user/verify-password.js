/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/');
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

			try {
				const data = {
					password: form.password.value.trim()
				}
				
				const response = await instance.post(`/users/${payload.id}/check-password`, data);
				
				if (response.data.code === 'USER_CORRECT_PASSWORD') {
					const path = sessionStorage.getItem('path') || '/';
					sessionStorage.setItem('passwordVerified', 'true');
					sessionStorage.removeItem('path');
					location.href = path;
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;
				
				if (code === 'USER_INCORRECT_PASSWORD') {
					document.querySelector('.alert').style.display = 'block';
					form.password.value = '';
					form.password.focus();
				}
				else if (code === 'USER_INVALID_INPUT') {
					alert(message);
				}
				else if (code === 'USER_UNAUTHORIZED_ACCESS') {
					alert(message);
				}
				else if (code === 'USER_FORBIDDEN') {
					alert(message);
				}
				else if (code === 'USER_NOT_FOUND') {
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