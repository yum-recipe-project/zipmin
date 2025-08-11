/**
 * 회원 정보 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const form = document.getElementById('editUserForm');

	// 아이디 실시간 검사
	form.username.addEventListener('input', function() {
		const isUsernameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isUsernameEmpty);
		document.querySelector('.username_field p').style.display = isUsernameEmpty ? 'block' : 'none';
	});
	
	// 이름 실시간 검사
	form.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNameEmpty);
		document.querySelector('.name_field p').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 닉네임 실시간 검사
	form.nickname.addEventListener('input', function() {
		const isNicknameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNicknameEmpty);
		document.querySelector('.nickname_field p').style.display = isNicknameEmpty ? 'block' : 'none';
	});
	
	// 휴대폰 번호 실시간 검사
	form.tel.addEventListener('input', function() {
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
	});
	
});





/**
 * 회원 정보를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editUserForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (form.nickname.value.trim() === '') {
			form.nickname.classList.add('danger');
			document.querySelector('.nickname_field p').style.display = 'block';
			form.nickname.focus();
			isValid = false;
		}
		
		if (form.name.value.trim() === '') {
			form.name.classList.add('danger');
			document.querySelector('.name_field p').style.display = 'block';
			form.name.focus();
			isValid = false;
		}
		
		if (form.username.value.trim() === '') {
			form.username.classList.add('danger');
			document.querySelector('.username_field p:nth-of-type(1)').style.display = 'block';
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
				
				const id = form.id.value;
					
				const data = {
					username: form.username.value.trim(),
					name: form.name.value.trim(),
					nickname: form.nickname.value.trim(),
					tel: form.tel.value.trim(),
					email: form.email.value.trim()
				};
				
				const response = await instance.patch(`/users/${id}`, data, {
					headers: headers
				});
				
				console.log(response);
				
				if (response.data.code === 'USER_UPDATE_SUCCESS') {
					location.reload();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'USER_UPDATE_FAIL') {
					alert('사용자 정보 수정에 실패했습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alert('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_UNAUTHORIZED_ACCESS') {
					alert('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'USER_FORBIDDEN') {
					alert('접근 권한이 없습니다.');
				}
				else if (code === 'USER_NOT_FOUND') {
					alert('해당 사용자를 찾을 수 없습니다.');
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
















