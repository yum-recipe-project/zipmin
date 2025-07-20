/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin();
	}

});



/**
 * 비밀번호 검증을 확인하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const isPasswordVerified = sessionStorage.getItem('passwordVerified') === 'true';
	if (!isPasswordVerified) {
		sessionStorage.setItem('path', window.location.pathname);
		location.href = '/user/verifyPassword.do';
	}
});



/**
 * 폼값을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	
	// 현재 비밀번호 실시간 검사
	form.oldPassword.addEventListener('blur', function() {
		const isOldPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isOldPasswordEmpty);
		document.querySelector('.old_password_field p:nth-of-type(1)').style.display = isOldPasswordEmpty ? 'block' : 'none';
		document.querySelector('.old_password_field p:nth-of-type(2)').style.display = 'none';
	});
	
	// 새 비밀번호 실시간 검사
	form.newPassword.addEventListener('blur', function() {
		const isNewPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNewPasswordEmpty);
		document.querySelector('.new_password_field p').style.display = isNewPasswordEmpty ? 'block' : 'none';
	});
	
	// 비밀번호 확인 실시간 검사
	form.checkPassword.addEventListener('blur', function() {
		const isCheckPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isCheckPasswordEmpty);
		document.querySelector('.check_password_field p:nth-of-type(1)').style.display = isCheckPasswordEmpty ? 'block' : 'none';
		if (document.querySelector('.check_password_field p:nth-of-type(2)').style.display === 'block') {
			form.newPassword.classList.remove('danger');
			document.querySelector('.check_password_field p:nth-of-type(2)').style.display = 'none';
		} 
	});
});



/**
 * 비밀번호를 변경하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.checkPassword.value.trim() === '') {
			form.checkPassword.classList.add('danger');
			document.querySelector('.check_password_field p:nth-of-type(1)').style.display = 'block';
			form.checkPassword.focus();
			isValid = false;
		}
		
		if (form.newPassword.value.trim() === '') {
			form.newPassword.classList.add('danger');
			document.querySelector('.new_password_field p').style.display = 'block';
			form.newPassword.focus();
			isValid = false;
		}
		
		if (form.oldPassword.value.trim() === '') {
			form.oldPassword.classList.add('danger');
			document.querySelector('.old_password_field p:nth-of-type(1)').style.display = 'block';
			form.oldPassword.focus();
			isValid = false;
		}
		else {
			// 현재 비밀번호 일치 여부 확인
			try {
				const token = localStorage.getItem('accessToken');
				const payload = parseJwt(token);
				
				const data = {
					password: form.oldPassword.value.trim()
				}
				
				await instance.post(`/users/${payload.id}/check-password`, data);
			}
			catch(error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;
				
				if (code === 'USER_INCORRECT_PASSWORD') {
					form.oldPassword.classList.add('danger');
					document.querySelector('.old_password_field p:nth-of-type(2)').style.display = 'block';
					form.oldPassword.value = '';
					form.newPassword.value = '';
					form.checkPassword.value = '';
					form.oldPassword.focus();
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
				isValid = false;
			}
		}
		
		if (form.newPassword.value.trim() !== form.checkPassword.value.trim()) {
			form.newPassword.classList.add('danger');
			form.checkPassword.classList.add('danger');
			document.querySelector('.check_password_field p:nth-of-type(2)').style.display = 'block';
			isValid = false;
		}

		// 비밀번호 수정
		if (isValid) {
			try {
				const token = localStorage.getItem('accessToken');
				const payload = parseJwt(token);
				
				const data = {
					password: form.newPassword.value.trim()
				}
				
				const response = await instance.patch(`/users/${payload.id}`, data);
				
				if (response.data.code === 'USER_UPDATE_SUCCESS') {
					alert('비밀번호가 변경되었습니다.');
					location.href = '/mypage.do';
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;
				
				if (code === 'USER_UPDATE_FAIL') {
					alert(message);
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

