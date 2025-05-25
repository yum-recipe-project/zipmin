/**
 * 회원가입 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

	// 이름 실시간 검사
	form.name.addEventListener("input", function() {
		const isNameEmpty = form.name.value.trim() === "";
		form.name.classList.toggle("danger", isNameEmpty);
		document.querySelector(".name_field p").style.display = isNameEmpty ? "block" : "none";
	});

	// 휴대폰 번호 실시간 검사
	form.tel.addEventListener("input", function() {
		const isTelEmpty = form.tel.value.trim() === "";
		form.tel.classList.toggle("danger", isTelEmpty);
		document.querySelector(".tel_field p").style.display = isTelEmpty ? "block" : "none";
	});

	// 아이디 실시간 검사
	form.username.addEventListener("input", function() {
		const isUsernameEmpty = form.username.value.trim() === "";
		form.username.classList.toggle("danger", isUsernameEmpty);
		document.querySelector(".username_field p").style.display = isUsernameEmpty ? "block" : "none";
	});
	
	// 비밀번호 실시간 검사
	form.password1.addEventListener("input", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		form.password1.classList.toggle("danger", isPassword1Empty);
		document.querySelector(".password_field p:nth-of-type(1)").style.display = isPassword1Empty ? "block" : "none";
		if (form.password2.classList.contains("danger")) {
			document.querySelector(".password_field p:nth-of-type(2)").style.display = !isPassword1Empty && isPassword2Empty ? "block" : "none";	
		}
	});
	
	// 비밀번호 재입력 실시간 검사
	form.password2.addEventListener("input", function() {
		const isPassword1Empty = form.password1.value.trim() === "";
		const isPassword2Empty = form.password2.value.trim() === "";
		form.password2.classList.toggle("danger", isPassword2Empty);
		document.querySelector(".password_field p:nth-of-type(2)").style.display = !isPassword1Empty && isPassword2Empty ? "block" : "none";
	});
	
	// 닉네임 실시간 검사
	form.nickname.addEventListener("input", function() {
		const isNicknameEmpty = form.nickname.value.trim() === "";
		form.nickname.classList.toggle("danger", isNicknameEmpty);
		document.querySelector(".nickname_field p").style.display = isNicknameEmpty ? "block" : "none";
	});
	
	// 이메일 실시간 검사
	form.email.addEventListener("input", function() {
		const isEmailEmpty = form.email.value.trim() === "";
		form.email.classList.toggle("danger", isEmailEmpty);
		document.querySelector(".email_field p").style.display = isEmailEmpty ? "block" : "none";
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		
		// 아이디 중복 검사
		// event.preventDefault();
		
		if (form.email.value.trim() === "") {
			event.preventDefault();
			form.email.classList.add("danger");
			querySelector(".email_field p").style.display = "block";
			form.email.focus();
		}
		
		if (form.nickname.value.trim() === "") {
			event.preventDefault();
			form.nickname.classList.add("danger");
			querySelector(".nickname_field p").style.display = "block";
			form.nickname.focus();
		}
		
		if (form.password2.value.trim() === "") {
			event.preventDefault();
			form.password2.classList.add("danger");
			if (form.password1.value.trim() !== "") {
				document.querySelector(".password_field p:nth-of-type(2)").style.display = "block";
			}
			form.password2.focus();
		}
		
		if (form.password1.value.trim() === "") {
			event.preventDefault();
			form.password1.classList.add("danger");
			document.querySelector(".password_field p:nth-of-type(1)").style.display = "block";
			form.password1.focus();
		}
		
		if (form.username.value.trim() === "") {
			event.preventDefault();
			form.username.classList.add("danger");
			querySelector(".username_field p").style.display = "block";
			form.username.focus();
		}
		
		if (form.tel.value.trim() === "") {
			event.preventDefault();
			form.tel.classList.add("danger");
			querySelector(".tel_field p").style.display = "block";
			form.tel.focus();
		}
		
		if (form.name.value.trim() === "") {
			event.preventDefault();
			form.name.classList.add("danger");
			querySelector(".name_field p").style.display = "block";
			form.name.focus();
		}
    });
});





/**
 * 회원가입을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector("form");
	
	form.addEventListener("submit", function(event) {
		event.preventDefault();
				
		const data = {
			name: form.name.value.trim(),
			username: form.username.value.trim(),
			password: form.password1.value.trim(),
			nickname: form.nickname.value.trim(),
			tel: form.tel.value.trim(),
			email: form.email.value.trim()
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
	})
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







