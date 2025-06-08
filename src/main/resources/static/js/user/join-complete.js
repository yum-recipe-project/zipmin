/**
 * 회원가입 정보를 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const user = sessionStorage.getItem("user");
	
	if (user) {
		document.getElementById("username").innerText = result.data.username;
		document.getElementById("name").innerText = result.data.name;
		document.getElementById("nickname").innerText = result.data.nickname;
		document.getElementById("tel").innerText = result.data.tel;
		document.getElementById("email").innerText = result.data.email;
		sessionStorage.removeItem("user");
	}
	else {
		alert("잘못된 접근입니다");
		window.location.href = "/";
	}
});