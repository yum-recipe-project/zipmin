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
	const form = document.getElementById('sendEmailForm');
	
	form.addEventListener('submit', async function(event) {
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
			
			if (result.code === 'USER_READ_PASSWORD_SUCCESS') {
				alert('이메일로 비밀번호 변경 링크가 전송되었습니다.');
			}
			else if (result.code === 'USER_SEND_EMAIL_FAIL') {
				alertDanger('이메일 전송에 실패했습니다.');
			}
			else if (result.code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (result.code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (result.code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
		}
		catch(error) {
			console.log(error);
		}
	});
});