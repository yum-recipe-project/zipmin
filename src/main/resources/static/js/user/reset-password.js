/**
 * 비밀번호 초기화 토큰을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 토큰 검증
	try {
		const token = new URLSearchParams(window.location.search).get('key');
		
		const response = await fetch(`/users/check-token`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
		});
		
		const result = await response.json();
		
		if (result.code === 'USER_VALID_TOKEN') {
			return;
		}
		else if (result.code === 'USER_TOKEN_MISSING') {
			location.href = '/user/passwordTokenExpired.do';
		}
		else if (result.code === 'USER_TOKEN_INVALID') {
			location.href = '/user/passwordTokenExpired.do';
		}
		else if (result.code === 'USER_TOKEN_EXPIRED') {
			location.href = '/user/passwordTokenExpired.do';
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alert('서버 내부에서 오류가 발생했습니다.');
			location.href = '/';
		}
	}
	catch(error) {
		console.log(error);
	}
	
});





/**
 * 폼값을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 비밀번호 초기화 폼
	const form = document.getElementById('resetPasswordForm');
	
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
 * 비밀번호를 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 비밀번호 초기화 폼
	const form = document.getElementById('resetPasswordForm');
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
		
		if (form.newPassword.value.trim() !== form.checkPassword.value.trim()) {
			form.newPassword.classList.add('danger');
			form.checkPassword.classList.add('danger');
			document.querySelector('.check_password_field p:nth-of-type(2)').style.display = 'block';
			isValid = false;
		}
		
		// 비밀번호 변경
		if (isValid) {
			try {
				const token = new URLSearchParams(window.location.search).get('key');
				
				const data = {
					password: form.newPassword.value.trim()
				}
				
				const response = await fetch(`/users/password`, {
					method: 'PATCH',
					headers: {
						'Content-Type': 'application/json',
						'Authorization': `Bearer ${token}`
					},
					body: JSON.stringify(data)
				});
				
				const result = await response.json();
				
				if (result.code === 'USER_UPDATE_PASSWORD_SUCCESS') {
					alert('비밀번호가 성공적으로 변경되었습니다.');
					location.href = '/user/login.do';
				}
				else if (result.code === 'USER_TOKEN_MISSING') {
					location.href = '/user/passwordTokenExpired.do';
				}
				else if (result.code === 'USER_TOKEN_INVALID') {
					location.href = '/user/passwordTokenExpired.do';
				}
				else if (result.code === 'USER_TOKEN_EXPIRED') {
					location.href = '/user/passwordTokenExpired.do';
				}
				else if (result.code === 'USER_UPDATE_FAIL') {
					alertDanger('비밀번호 변경에 실패했습니다.');
				}
				else if (result.code === 'USER_TOKEN_UPDATE_FAIL') {
					alertDanger('비밀번호 변경에 실패했습니다.');
				}
				else if (result.code === 'INTERNAL_SERVER_ERROR') {
					alert('서버 내부에서 오류가 발생했습니다.');
					location.href = '/';
				}
			}
			catch (error) {
				console.log(error);
			}
		}
	});
	
});