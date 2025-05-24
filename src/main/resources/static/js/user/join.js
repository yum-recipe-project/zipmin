/**
 * 회원가입 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const nameInput = document.getElementById("nameInput");
    const nameHint = document.getElementById("nameHint");
    const phoneInput = document.getElementById("phoneInput");
    const phoneHint = document.getElementById("phoneHint");
    const usernameInput = document.getElementById("usernameInput");
    const usernameHint = document.getElementById("usernameHint");
    const passwordInput = document.getElementById("passwordInput");
    const passwordInputCheck = document.getElementById("passwordInputCheck");
    const passwordHint = document.getElementById("passwordHint");
    const passwordHintCheck = document.getElementById("passwordHintCheck");
	const nicknameInput = document.getElementById("nicknameInput");
	const nicknameHint = document.getElementById("nicknameHint");
	const emailInput = document.getElementById("emailInput");
	const emailHint = document.getElementById("emailHint");

	// 이름 실시간 검사
	nameInput.addEventListener("input", function() {
		if (nameInput.value.trim() === "") {
			nameInput.classList.add("danger");
			nameHint.style.display = "block";
		}
		else {
			nameInput.classList.remove("danger");
			nameHint.style.display = "none";
		}
	});

	// 휴대폰 번호 실시간 검사
	phoneInput.addEventListener("input", function() {
		if (phoneInput.value.trim() === "") {
			phoneInput.classList.add("danger");
			phoneHint.style.display = "block";
		}
		else {
			phoneInput.classList.remove("danger");
			phoneHint.style.display = "none";
		}
	});

	// 아이디 실시간 검사
	usernameInput.addEventListener("input", function() {
		if (usernameInput.value.trim() === "") {
			usernameInput.classList.add("danger");
			usernameHint.style.display = "block";
		}
		else {
			usernameInput.classList.remove("danger");
			usernameHint.style.display = "none";
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
	
	// 비밀번호 재입력 실시간 검사
	passwordInputCheck.addEventListener("input", function() {
		if (passwordInputCheck.value.trim() === "") {
			passwordInputCheck.classList.add("danger");
			passwordHintCheck.style.display = "block";
		}
		else {
			passwordInputCheck.classList.remove("danger");
			passwordHintCheck.style.display = "none";
		}
	});
	
	// 닉네임 실시간 검사
	nicknameInput.addEventListener("input", function() {
		if (nicknameInput.value.trim() === "") {
			nicknameInput.classList.add("danger");
			nicknameHint.style.display = "block";
		}
		else {
			nicknameInput.classList.remove("danger");
			nicknameHint.style.display = "none";
		}
	});
	
	// 이메일 실시간 검사
	emailInput.addEventListener("input", function() {
		if (emailInput.value.trim() === "") {
			emailInput.classList.add("danger");
			emailHint.style.display = "block";
		}
		else {
			emailInput.classList.remove("danger");
			emailHint.style.display = "none";
		}
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		if (emailInput.value.trim() === "") {
			event.preventDefault();
			emailInput.classList.add("danger");
			emailHint.style.display = "block";
			emailInput.focus();
		}
		
		if (nicknameInput.value.trim() === "") {
			event.preventDefault();
			nicknameInput.classList.add("danger");
			nicknameHint.style.display = "block";
			nicknameInput.focus();
		}
		
		if (passwordInputCheck.value.trim() === "") {
			event.preventDefault();
			passwordInputCheck.classList.add("danger");
			if (passwordInput.value.trim() !== "") {
				passwordHintCheck.style.display = "block";
			}
			passwordInputCheck.focus();
		}
		
		if (passwordInput.value.trim() === "") {
			event.preventDefault();
			passwordInput.classList.add("danger");
			passwordHint.style.display = "block";
			passwordInput.focus();
		}
		
		if (usernameInput.value.trim() === "") {
			event.preventDefault();
			usernameInput.classList.add("danger");
			usernameHint.style.display = "block";
			usernameInput.focus();
		}
		
		if (phoneInput.value.trim() === "") {
			event.preventDefault();
			phoneInput.classList.add("danger");
			phoneHint.style.display = "block";
			phoneInput.focus();
		}
		
		if (nameInput.value.trim() === "") {
			event.preventDefault();
			nameInput.classList.add("danger");
			nameHint.style.display = "block";
			nameInput.focus();
		}
    });
});





/**
 * 회원가입을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("joinUserForm").addEventListener("submit", function (event) {
		event.preventDefault();
		
		const data = {
			name: document.getElementById("nameInput").value.trim(),
			phone: document.getElementById("phoneInput").value.trim(),
			username: document.getElementById("usernameInput").value.trim(),
			password: document.getElementById("passwordInput").value.trim(),
			nickname: document.getElementById("nicknameInput").value.trim(),
			tel: document.getElementById("phoneInput").value.trim(),
			email: document.getElementById("emailInput").value.trim()
		};
		
		fetch(`${API_BASE_URL}/users`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(data)
		})
		.then((response) => response.json())
		.then(result => {
			console.log("결과 : ", result);
			sessionStorage.setItem("user", JSON.stringify(result));
			window.location.href = "/user/join/complete.do";
		})
		.catch(error => console.log(error));
	});
});


