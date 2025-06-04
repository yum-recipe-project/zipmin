/**
 * 폼 입력을 실시간으로 검증하는 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
	
	// 이름 실시간 검사
	form.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isNameEmpty);
		document.querySelector(".name_field p").style.display = isNameEmpty ? "block" : "none";
	});
	
	// 휴대폰 번호 실시간 검사
	form.tel.addEventListener('input', function() {
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
	form.username.addEventListener('input', function() {
		const isUsernameEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isUsernameEmpty);
		document.querySelector(".username_field p").style.display = isUsernameEmpty ? "block" : "none";
	});
	
	// 이메일 실시간 검사
	form.email.addEventListener("input", function() {
		const isEmailEmpty = this.value.trim() === "";
		this.classList.toggle("danger", isEmailEmpty);
		document.querySelector(".email_field p").style.display = isEmailEmpty ? "block" : "none";
	});
});



/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
    const buttons = document.querySelectorAll('.tab_button button');
	const findUsernameForm = document.getElementById('find-username-form');
	const findPasswordForm = document.getElementById('find-password-form');
	const forms = [findUsernameForm, findPasswordForm];

    // URL 파라미터 확인
    const mode = new URLSearchParams(window.location.search).get('mode');

    // 기본 활성화할 탭 인덱스 설정
	let activeIndex = (mode === 'password') ? 1 : 0;
	
	buttons.forEach((button, index) => {
		button.addEventListener('click', function(event) {
			event.preventDefault();
			
			buttons.forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			
			forms.forEach(form => form.style.display = 'none');
			forms[index].style.display = 'block';
		});
	});

    // 초기 탭 활성화
    buttons.forEach(button => button.classList.remove('active'));
    forms.forEach(content => content.style.display = 'none');

    buttons[activeIndex].classList.add('active');
    forms[activeIndex].style.display = 'block';
});



/**
 * 아이디를 찾는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const findUsernameForm = document.getElementById('find-username-form');
	findUsernameForm.addEventListener('submit', function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼 전송 시 폼값 검사
		if (findUsernameForm.tel.value.trim() === '') {
			findUsernameForm.tel.classList.add('danger');
			document.querySelector('.tel_field p').style.display = 'block';
			findUsernameForm.tel.focus();
			isValid = false;
		}
		if (findUsernameForm.name.value.trim() === '') {
			findUsernameForm.name.classList.add('danger');
			document.querySelector('.name_field p').style.display = 'block';
			findUsernameForm.name.focus();
			isValid = false;
		}
		
		// 아이디를 찾는 API 호출
		if (isValid) {
			const data = {
				name: findUsernameForm.name.value.trim(),
				tel: findUsernameForm.tel.value.trim()
			}
			fetch("/users/username", {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(data)
			})
			.then(response => response.json())
			.then(result => {
				console.log(result);
				if (result.code === 'SUCCESS') {
					sessionStorage.setItem("username", result.data.username);
					window.location.href = "/user/findAccount/idResult.do";
				}
				else if (result.code === 'NOT_FOUND_USERNAME') {
					alert("이름과 전화번호를 정확히 입력해주세요.");
					// 이후에 여러 동작들 만들기
					findUsernameForm.name.value = '';
					findUsernameForm.tel.value = '';
					findUsernameForm.name.focus();
				}
			})
			.catch(error => console.log(error));
		}
	});
});



/**
 * 비밀번호를 찾는 함수
 */



