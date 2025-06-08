/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		redirectToLogin();
	}

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		console.log(error);
	}
});



/**
 * 비밀번호 검증을 확인하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const isPasswordVerified = sessionStorage.getItem('passwordVerified') === 'true';
	if (!isPasswordVerified) {
		sessionStorage.setItem('path', window.location.pathname);
		location.href = '/user/verifyPassword.do';
	}
});



/**
 * 회원 정보를 조회하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		redirectToLogin();
	}

	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const response = await instance.get(`/users/${payload.id}`);
		if (response.data.code === 'USER_PROFILE_FETCH_SUCCESS') {
			document.querySelector('.username_field .username').innerText = response.data.data.username;
			document.querySelector('.name_field .name').innerText = response.data.data.name;
			document.querySelector('.nickname_field .nickname').innerText = response.data.data.nickname;
			document.querySelector('.tel_field .tel').innerText = response.data.data.tel;
			document.querySelector('.email_field .email').innerText = response.data.data.email;
			
			document.querySelector('input[name="name"]').value = response.data.data.name;
			document.querySelector('input[name="nickname"]').value = response.data.data.nickname;
			document.querySelector('input[name="tel"]').value = response.data.data.tel;
			document.querySelector('input[name="email"]').value = response.data.data.email;
		}
	}
	catch (error) {
		console.log(error);
	}
});


/**
 * 회원 정보를 수정 및 취소 동작하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 기본 정보 수정
	document.getElementById('edit-basic-info-btn').addEventListener('click', function(event) {
		event.preventDefault();
		
		const icon = document.querySelector('#edit-basic-info-btn img');
		const label = document.querySelector('#edit-basic-info-btn a');
		const button = document.getElementById('submit-basic-info-btn');
		const isEdit = label.innerText === '취소';
		
		label.innerText = isEdit ? '수정' : '취소';
		icon.src = isEdit ? '/images/user/modify_member.png' : '/images/user/cancel_modify.png';
		button.style.display = !isEdit ? 'block' : 'none';
		
		document.querySelector('input[name="name"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('input[name="nickname"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('input[name="tel"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('.name_field .name').style.display = isEdit ? 'block' : 'none';
		document.querySelector('.nickname_field .nickname').style.display = isEdit ? 'block' : 'none';
		document.querySelector('.tel_field .tel').style.display = isEdit ? 'block' : 'none';
	});
	
	// 이메일 정보 수정
	document.getElementById('edit-email-info-btn').addEventListener('click', function(event) {
		event.preventDefault();
		
		const icon = document.querySelector('#edit-email-info-btn img');
		const label = document.querySelector('#edit-email-info-btn a');
		const button = document.getElementById('submit-email-info-btn');
		const isEdit = label.innerText === '취소';
		
		label.innerText = isEdit ? '수정' : '취소';
		icon.src = isEdit ? '/images/user/modify_member.png' : '/images/user/cancel_modify.png';
		button.style.display = !isEdit ? 'block' : 'none';
		
		document.querySelector('input[name="email"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('.email_field .email').style.display = isEdit ? 'block' : 'none';
	});
});



/**
 * 정보 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const basicInfoForm = document.getElementById('basic-info-form');
	const emailInfoForm = document.getElementById('email-info-form');
	
	// 이름 실시간 검사
	basicInfoForm.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNameEmpty);
		document.querySelector('.name_field .name_hint').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 닉네임 실시간 검사
	basicInfoForm.nickname.addEventListener('input', function() {
		const isNicknameEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isNicknameEmpty);
		document.querySelector('.nickname_field .nickname_hint').style.display = isNicknameEmpty ? 'block' : 'none';
	});
	
	// 휴대폰 번호 실시간 검사
	basicInfoForm.tel.addEventListener('input', function() {
		let tel = this.value.replace(/[^0-9]/g, '');
		if (tel.length <= 3) {
			this.value = tel;
		}
		else if (tel.length <= 7) {
			this.value = tel.slice(0, 3) + '-' + tel.slice(3);
		}
		else {
			this.value = tel.slice(0, 3) + '-' + tel.slice(3, 7) + "-" + tel.slice(7, 11);
		}
		const isTelEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isTelEmpty);
		document.querySelector('.tel_field .tel_hint').style.display = isTelEmpty ? 'block' : 'none';
	});
	
	// 이메일 실시간 검사
	emailInfoForm.email.addEventListener('input', function() {
		const isEmailEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isEmailEmpty);
		document.querySelector('.email_field .email_hint').style.display = isEmailEmpty ? 'block' : 'none';
	});
});



/**
 * 회원 정보를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 기본 정보를 수정
	const basicInfoForm = document.getElementById('basic-info-form');
	basicInfoForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		if (basicInfoForm.tel.value.trim() === '') {
			basicInfoForm.tel.classList.add('danger');
			document.querySelector('.tel_field .tel_hint').style.display = 'block';
			basicInfoForm.tel.focus();
			isValid = false;
		}
		
		if (basicInfoForm.nickname.value.trim() === '') {
			basicInfoForm.nickname.classList.add('danger');
			document.querySelector('.nickname_field .nickname_hint').style.display = 'block';
			basicInfoForm.nickname.focus();
			isValid = false;
		}
		
		if (basicInfoForm.name.value.trim() === '') {
			basicInfoForm.name.classList.add('danger');
			document.querySelector('.name_field .name_hint').style.display = 'block';
			basicInfoForm.name.focus();
			isValid = false;
		}
		
		if (isValid) {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
				
			const data = {
				name: basicInfoForm.name.value.trim(),
				nickname: basicInfoForm.nickname.value.trim(),
				tel: basicInfoForm.tel.value.trim()
			};
			
			try {
				const response = await instance.put(`/users/${payload.id}`, data);
				
				if (response.data.code === 'USER_PROFILE_UPDATE_SUCCESS') {
					location.reload();
				}
			}
			catch(error) {
				console.log(error);
			}
		}
	});
	
	// 이메일 정보를 수정
	const emailInfoForm = document.getElementById('email-info-form');
	emailInfoForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;

		if (emailInfoForm.email.value.trim() === '') {
			emailInfoForm.email.classList.add('danger');
			document.querySelector('.email_field .email_hint').style.display = 'block';
			emailInfoForm.email.focus();
			isValid = false;
		}

		if (isValid) {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				email: emailInfoForm.email.value.trim()
			}
			
			try {
				const response = await instance.put(`/users/${payload.id}`, data);
				
				if (response.data.code === 'USER_PROFILE_UPDATE_SUCCESS') {
					location.reload();
				}
			}
			catch (error) {
				console.log(error);
			}
		}
		
	});
});



/**
 * 회원을 탈퇴하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('user_delete_btn').addEventListener('click', async function() {
		
		if (confirm('정말로 탈퇴하시겠습니까?')) {
			if (confirm('이 동작은 되돌릴 수 없습니다. 정말로 탈퇴하시겠습니까?')) {
				const token = localStorage.getItem('accessToken');
				const payload = parseJwt(token);
				
				try {
					const response = await instance.delete(`/users/${payload.id}`);
					
					if (response.data.code === 'USER_DELETE_SUCCESS') {
						localStorage.removeItem('accessToken');
						alert('회원을 탈퇴했습니다.');
						location.href = '/';
					}
				}
				catch (error) {
					alert('회원 탈퇴에 실패했습니다.');
					console.log(error);
				}
			}
		}
	}) 
});









