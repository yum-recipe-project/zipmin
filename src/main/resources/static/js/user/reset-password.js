
/**
 * TODO : 이 페이지에 진입하자마자 검사해야 함
 */
document.addEventListener('DOMContentLoaded', async function() {
	
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
		else if (result.code === 'USER_INVALID_TOKEN') {
			location.href = '/user/passwordTokenExpired.do';
		}
		else if (result.code === 'USER_TOKEN_MISSING') {
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
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 비밀번호 초기화 폼
	const form = document.getElementById('resetPasswordForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		// 비밀번호 변경
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
			console.log(result);
			
			if (result.code === 'USER_UPDATE_PASSWORD_SUCCESS') {
				// TODO : 내용 수정
				alert('비밀번호가 변경되었습니다. 로그인하세요');
				location.href = '/user/login.do';
			}
			else if (result.code === 'USER_TOKEN_EXPIRED') {
				location.href = '/user/passwordTokenExpired.do';
			}
			
		}
		catch (error) {
			console.log(error);
			
		}
		
	});
	
});