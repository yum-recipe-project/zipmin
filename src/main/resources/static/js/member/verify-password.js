/**
 * 비밀번호 확인 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const passwordInput = document.getElementById("passwordInput");
    const passwordHint = document.getElementById("passwordHint");

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
    });
});