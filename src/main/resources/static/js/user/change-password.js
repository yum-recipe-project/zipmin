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
		console.log(error);
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
	form.oldPassword.addEventListener('input', function() {
		const isOldPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isOldPasswordEmpty);
		document.querySelector('.old_password_field p:nth-of-type(1)').style.display = isOldPasswordEmpty ? 'block' : 'none';
		document.querySelector('.old_password_field p:nth-of-type(2)').style.display = 'none';
	});
	
	// 새 비밀번호 실시간 검사
	form.newPassword.addEventListener('input', function() {
		const isNewPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNewPasswordEmpty);
		document.querySelector('.new_password_field p').style.display = isNewPasswordEmpty ? 'block' : 'none';
	});
	
	// 비밀번호 확인 실시간 검사
	form.checkPassword.addEventListener('input', function() {
		const isCheckPasswordEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isCheckPasswordEmpty);
		document.querySelector('.check_password_field p:nth-of-type(1)').style.display = isCheckPasswordEmpty ? 'block' : 'none';
		document.querySelector('.check_password_field p:nth-of-type(2)').style.display = 'none';
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
		// 현재 비밀번호 일치 여부 확인
		else {
			try {
				const token = localStorage.getItem('accessToken');
				const payload = parseJwt(token);
				
				const data = {
					id: payload.id,
					password: form.oldPassword.value.trim()
				}
				
				try {
					await instance.post('/users/verify-password', data);
				}
				catch (error) {
					form.oldPassword.classList.add('danger');
					document.querySelector('.old_password_field p:nth-of-type(2)').style.display = 'block';
					form.oldPassword.value = '';
					form.newPassword.value = '';
					form.checkPassword.value = '';
					form.oldPassword.focus();
					isValid = false;
				}
			}
			catch(error) {
				console.log(error);
			}
		}
		
		if (form.newPassword.value.trim() !== form.checkPassword.value.trim()) {
			form.newPassword.classList.add('danger');
			form.checkPassword.classList.add('danger');
			document.querySelector('.check_password_field p:nth-of-type(2)').style.display = 'block';
			isValid = false;
		}

		if (isValid) {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				password: form.newPassword.value.trim()
			}
			
			try {
				const response = await instance.put(`/users/${payload.id}`, data);
				
				if (response.data.code === 'USER_PROFILE_UPDATE_SUCCESS') {
					alert('비밀번호가 변경되었습니다.');
					location.href = '/mypage.do';
				}
			}
			catch (error) {
				console.log(error);
			}
		}
	});
});

