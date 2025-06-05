/**
 * 토큰을 디코딩하는 함수
 */
function parseJwt(token) {
	const base64Url = token.split('.')[1];
	const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
		return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	}).join(''));
	return JSON.parse(jsonPayload);
}



/**
 * 로그인 여부를 확인하는 함수
 */
function isLoggedIn() {
	return !!localStorage.getItem("accessToken");
}



/**
 * 토큰 만료 여부를 확인하는 함수
 */
function isTokenExpired(token) {
	const payload = parseJwt(token);
	const now = Math.floor(Date.now() / 1000);
	return payload.exp < now;
}



/**
 * 토큰을 재발급하는 함수
 */
async function reissueJwt() {
	const token = localStorage.getItem('accessToken');
	
	if (!token) {
		return null;
	}

	if (isTokenExpired(token)) {
		const response = await fetch("/reissue", {
			method: "POST",
			credentials: "include"
		});
		
		const result = await response.json();
		
		if (result.code === 'AUTH_TOKEN_REISSUE_SUCCESS') {
			localStorage.setItem('accessToken', result.data.accessToken);
			return result.data.accessToken;
		}
	}
	else {
		localStorage.removeItem('accessToken');
		alert('세션이 만료되었습니다. 다시 로그인 해주세요.');
		return null;
	}
}











