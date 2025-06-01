/**
 * 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
	
	// 아이디 실시간 검사
	form.username.addEventListener("input", function() {
		if (form.username.value.trim() === "") {
			form.username.classList.add("danger");
			document.querySelector(".username_field p").style.display = "block";
		}
		else {
			form.username.classList.remove("danger");
			document.querySelector(".username_field p").style.display = "none";
		}
	});
	
	// 비밀번호 실시간 검사
	form.password.addEventListener("input", function() {
		if (form.password.value.trim() === "") {
			form.password.classList.add("danger");
			document.querySelector(".password_field p").style.display = "block";
		}
		else {
			form.password.classList.remove("danger");
			document.querySelector(".password_field p").style.display = "none";
		}
	});
});



/**
 * 일반 로그인을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector("form");
	form.addEventListener("submit", function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼 제출 시 최종 검사
		if (form.password.value.trim() === "") {
			form.password.classList.add("danger");
			document.querySelector(".password_field p").style.display = "block";
			form.password.focus();
			isValid = false;
		}
		
		if (form.username.value.trim() === "") {
			form.username.classList.add("danger");
			document.querySelector(".username_field p").style.display = "block";
			form.username.focus();
			isValid = false;
		}
		
		if (isValid) {
			const data = {
				username : form.username.value.trim(),
				password : form.password.value.trim()
			};
			
			fetch(`${API_BASE_URL}/login`, {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(data)
			})
			.then((response) => response.json())
			.then(result => {
				console.log("결과 : ",result);
				if (result.code === "SUCCESS") {
					alert(result.data.accessToken);
					localStorage.setItem("accessToken", result.data.accessToken);
					window.location.href = "/";
				}
				else if (result.code === "ACCESS_DENIED_EXCEPTION") {
					form.username.value = "";
					form.password.value = "";
					form.username.focus();
					document.querySelector(".alert").style.display = "flex";
				}
			})
			.catch(error => console.log(error));
		}
	})
});






