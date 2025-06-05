/**
 * 비밀번호 입력 폼을 실시간으로 검증하는 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
	
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
	form.addEventListener('submit', function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.password.value.trim() === '') {
			form.password.classList.add('danger');
			document.querySelector('.password_field p').style.display = 'block';
			form.password.focus();
			isValid = false;
		}
		
		if (isValid) {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				id: payload.id,
				password: form.password.value.trim()
			}
			
			fetch('/users/verify-password', {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(data)
			})
			.then(response => response.json())
			.then(result => {
				console.log(result);
				if (result.code === 'USER_PASSWORD_VERIFY_SUCCESS') {
					const path = sessionStorage.getItem("path") || "/";
					sessionStorage.setItem('passwordVerified', 'true');
					sessionStorage.removeItem('path');
					location.href = path;
				}
				else if (result.code === 'USER_INVALID_PARAM') {
					alert("입력값이 올바르지 않습니다.");
				}
				else if (result.code === 'USER_PASSWORD_NOT_MATCH') {
					document.querySelector('.alert').style.display = 'block';
					form.password.value = '';
					form.password.focus();
				}
				else {
					alert("문제가 발생했습니다. 다시 시도하세요.");
				}
			});
		}
	});
});