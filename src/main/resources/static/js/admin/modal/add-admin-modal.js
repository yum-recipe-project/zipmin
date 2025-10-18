/**
 * 회원 정보 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const form = document.getElementById('addAdminForm');

	// 아이디 실시간 검사
	form.username.addEventListener('input', function() {
		const isUsernameEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isUsernameEmpty);
		document.getElementById('usernameHint').style.display = isUsernameEmpty ? 'block' : 'none';
	});
	
	// 이름 실시간 검사
	form.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isNameEmpty);
		document.getElementById('nameHint').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 비밀번호 실시간 검사
	form.password1.addEventListener('blur', function() {
		const isPassword1Empty = form.password1.value.trim() === '';
		const isPassword2Empty = form.password2.value.trim() === '';
		const isPasswordAccord = form.password1.value.trim() === form.password2.value.trim();
		this.classList.toggle('is-invalid', isPassword1Empty);
		document.getElementById('passwordHint').style.display = isPassword1Empty ? 'block' : 'none';
		if (!isPasswordAccord && !isPassword2Empty) {
			this.classList.add('is-invalid');
			document.getElementById('passwordHint').style.display = 'block';
		}
	});
	form.password2.addEventListener('blur', function() {
		const isPassword1Empty = form.password1.value.trim() === '';
		const isPassword2Empty = form.password2.value.trim() === '';
		const isPasswordAccord = form.password1.value.trim() === form.password2.value.trim();
		this.classList.toggle('is-invalid', isPassword2Empty);
		document.getElementById('passwordHint').style.display = isPassword2Empty ? 'block' : 'none';
		if (!isPasswordAccord && !isPassword1Empty) {
			this.classList.add('is-invalid');
			document.getElementById('passwordHint').style.display = 'block';
		}
	});
	
	// 닉네임 실시간 검사
	form.nickname.addEventListener('input', function() {
		const isNicknameEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isNicknameEmpty);
		document.getElementById('nicknameHint').style.display = isNicknameEmpty ? 'block' : 'none';
	});
	
});





/**
 * 관리자를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('addAdminForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.nickname.value.trim() === '') {
			form.nickname.classList.add('is-invalid');
			document.getElementById('nicknameHint').style.display = 'block';
			form.nickname.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === '') {
			form.name.classList.add('is-invalid');
			document.getElementById('nameHint').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		if (form.password1.value.trim() !== form.password2.value.trim()) {
			form.password1.classList.add('is-invalid');
			form.password2.classList.add('is-invalid');
			document.getElementById('passwordHint').style.display = 'block';
			form.password1.focus();
			isValid = false;
		}
		
		if (form.password2.value.trim() === '') {
			form.password2.classList.add('is-invalid');
			document.getElementById('passwordHint').style.display = 'block';
			form.password2.focus();
			isValid = false;
		}
		
		if (form.password1.value.trim() === '') {
			form.password1.classList.add('is-invalid');
			document.getElementById('passwordHint').style.display = 'block';
			form.password1.focus();
			isValid = false;
		}
		
		if (form.username.value.trim() === '') {
			form.username.classList.add('is-invalid');
			document.getElementById('usernameHint').style.display = 'block';
			form.username.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const token = localStorage.getItem('accessToken');
				
				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}
					
				const data = {
					username: form.username.value.trim(),
					password: form.password1.value.trim(),
					name: form.name.value.trim(),
					nickname: form.nickname.value.trim()
				};
				
				const response = await instance.post(`/users`, data, {
					headers: headers
				});
				
				if (response.data.code === 'USER_CREATE_SUCCESS') {
					location.reload();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'USER_CREATE_FAIL') {
					alert('관리자 생성에 실패했습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alert('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_USERNAME_DUPLICATED') {
					alert('이미 존재하는 아이디입니다.');
				}
				else if (code === 'USER_TEL_DUPLICATED') {
					alert('이미 존재하는 전화번호입니다.');
				}
				else if (code === 'USER_EMAIL_DUPLICATED') {
					alert('이미 존재하는 이메일입니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alert('서버 내부 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
	});
});
















