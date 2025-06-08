/**
 * 폼 입력 검증 함수
 */
document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
	
	// 아이디 실시간 검사
	form.username.addEventListener('input', function() {
		if (form.username.value.trim() === '') {
			form.username.classList.add('danger');
			document.querySelector('.username_field p').style.display = 'block';
		}
		else {
			form.username.classList.remove('danger');
			document.querySelector('.username_field p').style.display = 'none';
		}
	});
	
	// 비밀번호 실시간 검사
	form.password.addEventListener('input', function() {
		if (form.password.value.trim() === '') {
			form.password.classList.add('danger');
			document.querySelector('.password_field p').style.display = 'block';
		}
		else {
			form.password.classList.remove('danger');
			document.querySelector('.password_field p').style.display = 'none';
		}
	});
});



/**
 * 일반 로그인을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	
	// 저장한 아이디 표시
	const savedUsername = localStorage.getItem('savedUsername');
	if (savedUsername) {
		const parsed = JSON.parse(savedUsername);
		const now = Date.now();
		
		if (now < parsed.exp) {
			form.username.value = parsed.username;
			document.getElementById('save-id').checked = true;
		}
		else {
			localStorage.removeItem('savedUsername');
		}
	}
	
	// 로그인 폼 이벤트 발생
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		// 폼 제출 시 최종 검사
		if (form.password.value.trim() === '') {
			form.password.classList.add('danger');
			document.querySelector('.password_field p').style.display = 'block';
			form.password.focus();
			isValid = false;
		}
		
		if (form.username.value.trim() === '') {
			form.username.classList.add('danger');
			document.querySelector('.username_field p').style.display = 'block';
			form.username.focus();
			isValid = false;
		}
		
		// 로그인
		if (isValid) {
			const data = {
				username : form.username.value.trim(),
				password : form.password.value.trim()
			};
			
			try {
				const response = await fetch('/login', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify(data)
				});
				
				const result = await response.json();
				
				if (result.code === 'USER_LOGIN_SUCCESS') {
					localStorage.setItem('accessToken', result.data.accessToken);
					// 아이디 저장
					if (document.getElementById('save-id').checked) {
						localStorage.setItem('savedUsername', JSON.stringify({
							username: form.username.value.trim(),
							exp: Date.now() + 24 * 60 * 60 * 1000
						}));
					}
					else {
						localStorage.removeItem('savedUsername');
					}
					window.location.href = '/';
				}
				else if (result.code === 'AUTH_UNAUTHORIZED') {
					form.username.value = '';
					form.password.value = '';
					form.username.focus();
					document.querySelector('.alert').style.display = 'flex';
				}
			}
			catch (error) {
				console.log(error);
			}
		}
	})
});






