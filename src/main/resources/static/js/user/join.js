/**
 * 회원가입 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

	// 이름 실시간 검사
	form.name.addEventListener("input", function() {
		const isNameEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isNameEmpty);
		document.querySelector(".name_field p").style.display = isNameEmpty ? "block" : "none";
	});

	// 휴대폰 번호 실시간 검사
	form.tel.addEventListener("input", function() {
		let tel = this.value.replace(/[^0-9]/g, "");
		if (tel.length <= 3) {
			this.value = tel;
		}
		else if (tel.length <= 7) {
			this.value = tel.slice(0, 3) + "-" + tel.slice(3);
		}
		else {
			this.value = tel.slice(0, 3) + "-" + tel.slice(3, 7) + "-" + tel.slice(7, 11);
		}
		const isTelEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isTelEmpty);
		document.querySelector(".tel_field p").style.display = isTelEmpty ? "block" : "none";
	});
	
	// 아이디 실시간 검사
	form.username.addEventListener("input", function() {
		const isUsernameEmpty = this.value.trim() === "";
		document.querySelector(".username_field p:nth-of-type(2)").style.display = "none";
		document.querySelector(".username_field p:nth-of-type(3)").style.display = "none";
		document.querySelector(".username_field p:nth-of-type(4)").style.display = "none";
		this.classList.toggle("danger", isUsernameEmpty);
		if (window.getComputedStyle(document.querySelector(".username_field p:nth-of-type(2)")).display === "none") {
			document.querySelector(".username_field p:nth-of-type(1)").style.display = isUsernameEmpty ? "block" : "none";
		}
	});
	form.username.addEventListener("blur", function() {
		if (!/^(?=.*[a-zA-Z])[a-zA-Z0-9]{4,20}$/.test(this.value)) {
			if (window.getComputedStyle(document.querySelector(".username_field p:nth-of-type(1)")).display === "none") {
				this.classList.add("danger");
				document.querySelector(".username_field p:nth-of-type(2)").style.display = "block";
			}
		}
	});

	// 비밀번호 실시간 검사
	form.password1.addEventListener("input", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		if (!form.password2.classList.contains("danger")) {
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "none";
		}
		this.classList.toggle("danger", isPassword1Empty);
		document.querySelector(".password_field p:nth-of-type(1)").style.display = isPassword1Empty || (isPassword2Empty && form.password2.classList.contains("danger")) ? "block" : "none";
		if (window.getComputedStyle(document.querySelector(".password_field p:nth-of-type(1)")).display === "block") {
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "none";
		}
	});
	form.password1.addEventListener("blur", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		const isPassword1Valid = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{10,}$|^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])[a-zA-Z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,}$/.test(form.password1.value);
		const isPasswordAccord = form.password1.value.trim() === form.password2.value.trim();
		if (isPassword1Empty) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(1)").style.display = "block";
		}
		else if (!isPassword1Valid) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
		}
		else if (!isPasswordAccord && !isPassword2Empty) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
		}
	});
	form.password2.addEventListener("input", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		if (!form.password1.classList.contains("danger")) {
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "none";
		}
		this.classList.toggle("danger", isPassword2Empty);
		document.querySelector(".password_field p:nth-of-type(1)").style.display = isPassword2Empty || (isPassword1Empty && form.password1.classList.contains("danger")) ? "block" : "none";
		if (window.getComputedStyle(document.querySelector(".password_field p:nth-of-type(1)")).display === "block") {
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "none";
		}
	});
	form.password2.addEventListener("blur", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		const isPassword2Valid = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{10,}$|^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])[a-zA-Z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,}$/.test(form.password2.value);
		const isPasswordAccord = form.password1.value.trim() === form.password2.value.trim();
		if (isPassword2Empty) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(1)").style.display = "block";
		}
		else if (!isPassword2Valid) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
		}
		else if (!isPasswordAccord && !isPassword1Empty) {
			this.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
		}
	});
	
	
	// 닉네임 실시간 검사
	form.nickname.addEventListener("input", function() {
		const isNicknameEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isNicknameEmpty);
		document.querySelector(".nickname_field p").style.display = isNicknameEmpty ? "block" : "none";
	});
	
	// 이메일 실시간 검사
	form.email.addEventListener("input", function() {
		const isEmailEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isEmailEmpty);
		document.querySelector(".email_field p").style.display = isEmailEmpty ? "block" : "none";
	});
});



/**
 * 회원가입을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector("form");
	form.addEventListener("submit", function (event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.email.value.trim() === "") {
			form.email.classList.add("danger");
			document.querySelector(".email_field p").style.display = "block";
			form.email.focus();
			isValid = false;
		}
		
		if (form.nickname.value.trim() === "") {
			form.nickname.classList.add("danger");
			document.querySelector(".nickname_field p").style.display = "block";
			form.nickname.focus();
			isValid = false;
		}
		
		if (form.password2.value.trim() === "") {
			form.password2.classList.add("danger");
			if (form.password1.value.trim() !== "") {
				document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
			}
			form.password2.focus();
			isValid = false;
		}
		else if (!/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{10,}$|^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])[a-zA-Z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,}$/.test(form.password2.value)) {
			form.password2.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
			form.password2.focus();
			isValid = false;
		}
		else if (form.password1.value.trim() !== form.password2.value.trim()) {
			form.password1.classList.add("danger");
			form.password2.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(3)").style.display = "block";
			form.password1.focus();
			isValid = false;
		}

		if (form.password1.value.trim() === "") {
			form.password1.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(1)").style.display = "block";
			form.password1.focus();
			isValid = false;
		}
		else if (!/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{10,}$|^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])[a-zA-Z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,}$/.test(form.password1.value)) {
			form.password2.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
			form.password2.focus();
			isValid = false;
		}
		else if (form.password1.value.trim() !== form.password2.value.trim()) {
			form.password1.classList.add("danger");
			form.password2.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(3)").style.display = "block";
			form.password1.focus();
			isValid = false;
		}
		
		if (form.username.value.trim() === "") {
			form.username.classList.add("danger");
			document.querySelector(".username_field p:nth-of-type(1)").style.display = "block";
			form.username.focus();
			isValid = false;
		}
		else if (!/^(?=.*[a-zA-Z])[a-zA-Z0-9]{4,20}$/.test(form.username.value)) {
			form.username.classList.add("danger");
			document.querySelector(".username_field p:nth-of-type(2)").style.display = "block";
			form.username.focus();
			isValid = false;
		}
		
		if (form.tel.value.trim() === "") {
			form.tel.classList.add("danger");
			document.querySelector(".tel_field p").style.display = "block";
			form.tel.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === "") {
			form.name.classList.add("danger");
			document.querySelector(".name_field p").style.display = "block";
			form.name.focus();
			isValid = false;
		}
		
		if (isValid) {
			if (window.getComputedStyle(document.querySelector(".username_field p:nth-of-type(4)")).display === "none") {
				alert("아이디 중복 여부를 확인해주세요.");
				form.username.focus();
				isValid = false;
			}
		}
		
		if (isValid) {
			const data = {
				name: form.name.value.trim(),
				username: form.username.value.trim(),
				password: form.password1.value.trim(),
				nickname: form.nickname.value.trim(),
				tel: form.tel.value.trim(),
				email: form.email.value.trim()
			};
			
			fetch("/users", {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(data)
			})
			.then((response) => response.json())
			.then(result => {
				if (result.code === 'USER_SIGNUP_SUCCESS') {
					sessionStorage.setItem("user", JSON.stringify(result));
					window.location.href = "/user/join/complete.do";
				}
				else {
					alert("회원가입에 실패했습니다.");
				}
			})
			.catch(error => console.log(error));
		}
		
    });
});



/**
 * 랜덤으로 생성한 닉네임을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.querySelector(".nickname_field button").addEventListener("click", function(event) {
		event.preventDefault();
		const nickname = randomNickname();
		document.querySelector("form").nickname.value = nickname;
	});
})



// 랜덤 닉네임 생성 함수
function randomNickname() {
	const first = ["촉촉한", "파닥파닥", "싱싱한", "상큼한", "야망있는", "살금살금", "제멋대로", "거친 파도 속", "신출귀몰한", "야생의", "시들시들한", "트렌디한", "철푸덕", "새콤달콤한", "수줍어하는", "카리스마있는", "졸렬한", "배고픈", "비열한","뒷 골목의", "불타는", "노란머리","버섯머리", "버석한", "기괴한", "더조은","용의주도한", "괴로운", "비염걸린", "눈물 흘리는", "코찔찔이", "꼬들한", "소극적인", "화끈한"];	
	const last = ["열대어", "팽이버섯", "오리", "야자수", "숙주나물", "수박", "도둑", "어부", "헌터", "뽀야미", "파수꾼", "대주주", "알부자", "사천왕", "수족 냉증", "불주먹", "물주먹", "스나이퍼", "파스타", "수면핑", "농구공", "바다의 왕자", "아기돼지", "김치볶음밥", "파인애플", "지하철", "꼬질이"];
	const nickname = first[Math.floor(Math.random() * first.length)] + " " + last[Math.floor(Math.random() * last.length)];
	return nickname;
}



/**
 * 아이디 중복을 확인하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.querySelector(".username_field button").addEventListener("click", function(event) {
		event.preventDefault();
		const username = document.querySelector('input[name="username"]').value.trim();
		if (username === "") {
			if (window.getComputedStyle(document.querySelector(".username_field p:nth-of-type(2)")).display === "none") {
				document.querySelector("form").username.classList.add("danger");
				document.querySelector(".username_field p:nth-of-type(1)").style.display = "block";
			}
			return;
		}
		else if (!/^(?=.*[a-zA-Z])[a-zA-Z0-9]{4,20}$/.test(username)) {
			return;
		}
		
		fetch(`/users/check-username?username=${username}`, {
			method: "GET"
		})
		.then((response) => response.json())
		.then(result => {
			if (result.code === 'USER_USERNAME_AVAILABLE') {
				document.querySelector(".username_field p:nth-of-type(4)").style.display = "block";
			}
			// 입력값이 올바르지 않습니다.
			else if (result.code === 'USER_INVALID_PARAM') {
				document.querySelector("form").username.classList.add("danger");
				document.querySelector(".username_field p:nth-of-type(3)").style.display = "block";
			}
			// 이미 사용 중인 아이디입니다.
			else if (result.code === 'USER_USERNAME_DUPLICATED') {
				document.querySelector("form").username.classList.add("danger");
				document.querySelector(".username_field p:nth-of-type(3)").style.display = "block";
			}
		})
		.catch(error => console.log(error));
	});
});





