/**
 * 팝업으로 소셜 로그인 후 토큰을 요청하고 부모 창에 저장 및 리다이렉트 처리하는 함수
 */
document.addEventListener('DOMContentLoaded', async function () {
	
	try {
		// 서버에 토큰 요청
		const response = await fetch('/oauth2-jwt-header', {
			method: 'POST',
			credentials: 'include'
		});
		
		// Authorization 헤더에서 Access Token 추출
		const token = response.headers.get('Authorization');
		
		if (token && token.startsWith('Bearer ')) {
			// Access Token 저장
			localStorage.setItem('accessToken', token.substring(7));

			// 팝업 여부에 따라 리다이렉트 처리
			if (window.opener) {
				window.opener.location.href = '/';
				window.close();
			}
			else {
				window.location.href = '/';
			}
		}
		else {
			console.log('토큰 발급에 실패했습니다.');
			window.close();
		}
	}
	catch (error) {
		console.log(error);
		window.close();
	}

});