/**
 * 회원가입 정보를 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const userStr = sessionStorage.getItem("user");
	
	if (userStr) {
		const user = JSON.parse(userStr);
		
		document.getElementById("username").innerText = user.username;
		document.getElementById("name").innerText = user.name;
		document.getElementById("nickname").innerText = user.nickname;
		document.getElementById("tel").innerText = user.tel;
		document.getElementById("email").innerText = user.email;
		sessionStorage.removeItem("user");
	}
	else {
		alert("잘못된 접근입니다");
		window.location.href = "/";
	}
});