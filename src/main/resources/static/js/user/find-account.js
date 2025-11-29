/**
 * 탭 메뉴를 클릭하면 해당 탭을 활성화하고 연결된 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
    const buttons = document.querySelectorAll('.tab_button button');
	const forms = [document.getElementById('findUsernameForm'), document.getElementById('findPasswordForm')];

    // 기본 활성화 탭 설정
    const mode = new URLSearchParams(window.location.search).get('mode');
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

    // 초기화 및 기본 탭 활성화
    buttons.forEach(button => button.classList.remove('active'));
    forms.forEach(content => content.style.display = 'none');

    buttons[activeIndex].classList.add('active');
    forms[activeIndex].style.display = 'block';
});





/**
 * 아이디 찾기 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
    const form = document.getElementById('findUsernameForm');
	
	// 이름 실시간 검사
	form.name.addEventListener('blur', function() {
		const isNameEmpty = this.value.trim() === '';
		form.classList.toggle('danger', isNameEmpty);
		document.querySelector('.name_field p').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 휴대폰 번호 실시간 검사
	form.tel.addEventListener('blur', function() {
		let tel = this.value.replace(/[^0-9]/g, '');
		if (tel.length <= 3) {
			this.value = tel;
		}
		else if (tel.length <= 7) {
			this.value = tel.slice(0, 3) + '-' + tel.slice(3);
		}
		else {
			this.value = tel.slice(0, 3) + '-' + tel.slice(3, 7) + '-' + tel.slice(7, 11);
		}
		const isTelEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isTelEmpty);
		form.querySelector('.tel_field p').style.display = isTelEmpty ? 'block' : 'none';
	});
	
});





/**
 * 비밀번호 찾기 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
    const form = document.getElementById('findPasswordForm');
	
	// 아이디 실시간 검사
	form.username.addEventListener('blur', function() {
		const isUsernameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isUsernameEmpty);
		form.querySelector('.username_field p').style.display = isUsernameEmpty ? 'block' : 'none';
	});
	
	// 이메일 실시간 검사
	form.email.addEventListener('blur', function() {
		const isEmailEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isEmailEmpty);
		form.querySelector('.email_field p').style.display = isEmailEmpty ? 'block' : 'none';
	});
	
});




/**
 * 사용자의 아이디를 찾는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 아이디 찾기 폼
	const form = document.getElementById('findUsernameForm');
	
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼 전송시 폼값 검증
		if (form.tel.value.trim() === '') {
			form.tel.classList.add('danger');
			form.querySelector('.tel_field p').style.display = 'block';
			form.tel.focus();
			isValid = false;
		}
		if (form.name.value.trim() === '') {
			form.name.classList.add('danger');
			form.querySelector('.name_field p').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		// 아이디 찾기
		if (isValid) {
			try {
				const data = {
					name: form.name.value.trim(),
					tel: form.tel.value.trim()
				}
				
				const response = await fetch('/users/find-username', {
					method: 'POST',
					headers: getAuthHeaders(),
					body: JSON.stringify(data)
				});
				
				const result = await response.json();
				
				if (result.code === 'USER_READ_USERNAME_SUCCESS') {
					sessionStorage.setItem('username', result.data.username);
					location.href = '/user/findAccount/idResult.do';
				}
				else if (result.code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (result.code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (result.code === 'INTERVAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				}
			}
			catch (error) {
				console.log(error);
			}
		}
	});
});






/**
 * 사용자의 비밀번호를 찾는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 비밀번호 찾기 폼
	const form = document.getElementById('findPasswordForm');
	
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		isValid = true;
		
		// 폼값 검증
		if (form.email.value.trim() === '') {
			form.email.classList.add('danger');
			form.querySelector('.email_field p').style.display = 'block';
			form.email.focus();
			isValid = false;
		}
		if (form.username.value.trim() === '') {
			form.username.classList.add('danger');
			form.querySelector('.username_field p').style.display = 'block';
			form.username.focus();
			isValid = false;
		}
		
		// 이메일 검증
		if (isValid) {
			try {
				const data = {
					username: form.username.value.trim(),
					email: form.email.value.trim()
				}
				
				const response = await fetch('/users/check-email', {
					method: 'POST',
					headers: getAuthHeaders(),
					body: JSON.stringify(data)
				});
				
				const result = await response.json();
				
				if (result.code === 'USER_READ_SUCCESS') {
					sessionStorage.setItem('emailChecked', 'true');
					sessionStorage.setItem('username-email', result.data.username + "-" + result.data.email);
					location.href = '/user/findAccount/passwordResult.do';
				}
				else if (result.code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (result.code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (result.code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.')
				}
			}
			catch(error) {
				console.log(error);
			}
		}
	});
	
});