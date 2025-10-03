/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('resetPasswordForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
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
				// TODO : 토큰 만료 페이지로 이동
				location.href = '/user/passwordTokenExpired.do'
			}
			
		}
		catch (error) {
			console.log(error);
			
		}
		
	});
	
});