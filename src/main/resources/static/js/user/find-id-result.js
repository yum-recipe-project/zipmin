/**
 * 아이디 정보를 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const data = sessionStorage.getItem('username');
	
	if (data) {
		document.getElementById('username').innerText = data;
		sessionStorage.removeItem('username');
	}
	else {
		alert("잘못된 접근입니다. find-id-result.js");
		window.location.href = "/user/login.do";
	}
});
