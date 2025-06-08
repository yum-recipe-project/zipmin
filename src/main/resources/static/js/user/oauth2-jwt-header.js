/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	fetch('/oauth2-jwt-header', {
		method: 'POST',
		credentials: 'include'
	})
	.then((response) => {
		const token = response.headers.get('Authorization');
		if (token && token.startsWith('Bearer ')) {
			localStorage.setItem('accessToken', token.substring(7));
			window.location.href = '/';
		}
		else {
			alert('토큰 발급 실패');
		}
	})
	.catch(error => console.log(error));
});