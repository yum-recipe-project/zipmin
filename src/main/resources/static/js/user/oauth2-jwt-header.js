/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	const params = new URLSearchParams(window.location.search);
	const name = params.get("name");
	
	fetch(`${API_BASE_URL}/oauth2-jwt-header`, {
		method: "POST",
		credentials: "include"
	})
	.then((response) => {
		console.log(response);
		const token = response.headers.get("Authorization");
		
		if (token && token.startsWith("Bearer ")) {
			localStorage.setItem("accessToken", token);
			localStorage.setItem("name", name);
			window.location.href = "/";
		} else {
			alert("토큰 발급 실패");
		}
	})
	.catch(error => console.log(error));
});