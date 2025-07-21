/**
 * 아이디를 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const username = sessionStorage.getItem('username');

	if (username) {
		document.getElementById('username').innerText = username;
	}
	else {
		alert("잘못된 접근입니다.");
		window.location.href = "/user/login.do";
	}
});