/**
 * 이메일 검증을 확인하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const isEmailChecked = sessionStorage.getItem('emailChecked') === 'true';
	if (!isEmailChecked) {
		location.href = '/user/findAccount.do?mode=password';
		return;
	}
});





/**
 * 사용자의 비밀번호를 찾는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 비밀번호 찾기 폼
	const emailWrap = document.getElementById('emailWrap');
	const submitButton = emailWrap.querySelector('.btn_primary_wide');
	
	submitButton.addEventListener('click', async function(event) {
		event.preventDefault();
		
		try {
			const data = {
				username: sessionStorage.getItem('username-email').split('-')[0],
				email: sessionStorage.getItem('username-email').split('-')[1]
			}
			
			const response = await fetch('/users/find-password', {
				method: 'POST',
				headers: getAuthHeaders(),
				body: JSON.stringify(data)
			});
			
			const result = await response.json();
			
			console.log(result);
			
			if (result.code === 'USER_READ_PASSWORD_SUCCESS') {
				alert('이메일을 전송하였습니다. 임시 비밀번호로 로그인하여 비밀번호를 변경하세요.');
			}
			
			// *** TODO : 에러코드 ***
			
		}
		catch(error) {
			console.log(error);
		}
	});
	
});