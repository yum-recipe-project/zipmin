/**
 * 비밀번호 변경 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const oldPasswordInput = document.getElementById("oldPasswordInput");
    const oldPasswordHint = document.getElementById("oldPasswordHint");
    const newPasswordInput = document.getElementById("newPasswordInput");
    const newPasswordHint = document.getElementById("newPasswordHint");
    const checkPasswordInput = document.getElementById("checkPasswordInput");
    const checkPasswordHint = document.getElementById("checkPasswordHint");

	// 이전 비밀번호 실시간 검사
	oldPasswordInput.addEventListener("input", function() {
		if (oldPasswordInput.value.trim() === "") {
			oldPasswordInput.classList.add("danger");
			oldPasswordHint.style.display = "block";
		}
		else {
			oldPasswordInput.classList.remove("danger");
			oldPasswordHint.style.display = "none";
		}
	});
	
	// 새 비밀번호 실시간 검사
	newPasswordInput.addEventListener("input", function() {
		if (newPasswordInput.value.trim() === "") {
			newPasswordInput.classList.add("danger");
			newPasswordHint.style.display = "block";
		}
		else {
			newPasswordInput.classList.remove("danger");
			newPasswordHint.style.display = "none";
		}
	});
	
	// 비밀번호 확인 실시간 검사
	checkPasswordInput.addEventListener("input", function() {
		if (checkPasswordInput.value.trim() === "") {
			checkPasswordInput.classList.add("danger");
			checkPasswordHint.style.display = "block";
		}
		else {
			checkPasswordInput.classList.remove("danger");
			checkPasswordHint.style.display = "none";
		}
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		if (checkPasswordInput.value.trim() === "") {
			event.preventDefault();
			checkPasswordInput.classList.add("danger");
			checkPasswordHint.style.display = "block";
			checkPasswordInput.focus();
		}
		
		if (newPasswordInput.value.trim() === "") {
			event.preventDefault();
			newPasswordInput.classList.add("danger");
			newPasswordHint.style.display = "block";
			newPasswordInput.focus();
		}
		
		if (oldPasswordInput.value.trim() === "") {
			event.preventDefault();
			oldPasswordInput.classList.add("danger");
			oldPasswordHint.style.display = "block";
			oldPasswordInput.focus();
		}
    });
});