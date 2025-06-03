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
 * 수정 필요 !!!!!!!!!!!!!!!!!!!
 */
async function fetchWithAuth(url, options = {}) {
	const accessToken = localStorage.getItem("accessToken");
	if (!options.headers) options.headers = {};
	if (accessToken) {
		options.headers["Authorization"] = `Bearer ${accessToken}`;
	}
	options.credentials = "include"; // refresh는 쿠키에서 꺼내기 위함

	let res = await fetch(url, options);
	if (res.status === 401) {
		// access token 만료 → 재발급 시도
		const reissueRes = await fetch(`${API_BASE_URL}/reissue`, {
			method: "POST",
			credentials: "include"
		});
		const reissueData = await reissueRes.json();
		if (reissueData.code === "SUCCESS") {
			const newAccessToken = reissueData.data.accessToken;
			localStorage.setItem("accessToken", newAccessToken);

			// 원래 요청 다시 시도
			options.headers["Authorization"] = `Bearer ${newAccessToken}`;
			res = await fetch(url, options);
		} else {
			alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
			localStorage.removeItem("accessToken");
			window.location.href = "/login";
		}
	}
	return res;
}




/* 사용 예시
// 예: 레시피 등록
fetchWithAuth(`${API_BASE_URL}/recipes`, {
	method: "POST",
	headers: {
		"Content-Type": "application/json"
	},
	body: JSON.stringify({
		title: "된장찌개",
		content: "된장 + 두부 + 호박..."
	})
})
.then(res => res.json())
.then(result => {
	if (result.code === "SUCCESS") {
		alert("레시피 등록 완료");
		window.location.href = "/recipes";
	} else {
		alert("등록 실패: " + result.message);
	}
})
.catch(err => console.error("요청 실패:", err));


*/










