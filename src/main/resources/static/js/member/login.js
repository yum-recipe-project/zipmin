/**
 * 아이디와 비밀번호 폼을 검증하는 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const idInput = document.getElementById("idInput");
    const idHint = document.getElementById("idHint");
    const passwordInput = document.getElementById("passwordInput");
    const passwordHint = document.getElementById("passwordHint");
	
	// 아이디 실시간 검사
	idInput.addEventListener("input", function() {
		if (idInput.value.trim() === "") {
			idInput.classList.add("danger");
			idHint.style.display = "block";
		}
		else {
			idInput.classList.remove("danger");
			idHint.style.display = "none";
		}
	});
	
	// 비밀번호 실시간 검사
	passwordInput.addEventListener("input", function() {
		if (passwordInput.value.trim() === "") {
			passwordInput.classList.add("danger");
			passwordHint.style.display = "block";
		}
		else {
			passwordInput.classList.remove("danger");
			passwordHint.style.display = "none";
		}
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		if (passwordInput.value.trim() === "") {
			event.preventDefault();
			passwordInput.classList.add("danger");
			passwordHint.style.display = "block";
			passwordInput.focus();
		}
		
		if (idInput.value.trim() === "") {
			event.preventDefault();
			idInput.classList.add("danger");
			idHint.style.display = "block";
			idInput.focus();
		}
    });
});