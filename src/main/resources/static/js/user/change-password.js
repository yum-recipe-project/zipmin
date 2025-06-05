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
		document.querySelector('.old_password_field p').style.display = isOldPasswordEmpty ? 'block' : 'none';
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
		document.querySelector('.check_password_field p').style.display = isCheckPasswordEmpty ? 'block' : 'none';
	});
});



/**
 * 비밀번호를 변경하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	form.addEventListener('submit', function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.checkPassword.value.trim() === '') {
			form.checkPassword.classList.add('danger');
			document.querySelector('.check_password_field p').style.display = 'block';
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
			document.querySelector('.old_password_field p').style.display = 'block';
			form.oldPassword.focus();
			isValid = false;
		}

		if (isValid) {
			
		}
	});
});

