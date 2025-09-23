/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin();
	}
	
});





/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	fetchUser();
});






/**
 * 사용자 프로필 데이터를 가져오는 함수
 */
async function fetchUser() {
	
	const userWrap = document.getElementById('userWrap');
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const response = await fetch(`/users/${id}/profile`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'USER_READ_SUCCESS') {
			if (result.data.avatar) {
				userWrap.querySelector('.user_avatar').style.backgroundImage = `url(${result.data.avatar})`;
				userWrap.querySelector('.user_avatar').style.backgroundColor = '#F1F6FD';
				userWrap.querySelector('.user_avatar').style.backgroundSize = '65%';
				userWrap.querySelector('.user_avatar').style.backgroundPosition = 'center';
				userWrap.querySelector('.user_avatar').style.backgroundRepeat = 'no-repeat';
				userWrap.querySelector('.user_avatar').style.borderColor = '#D7DBE6';
			}
				userWrap.querySelector('.user_nickname').innerText = result.data.nickname;
				userWrap.querySelector('.user_introduce').innerText = result.data.introduce;
		}
		else if (result.code === 'LIKE_COUNT_FAIL') {
			alertDanger('좋아요 수 집계에 실패했습니다.');
		}
		else if (result.code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'LIKE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
	catch(error) {
		console.log(error);
		alertDanger('알 수 없는 오류가 발생했습니다.');
	}
}





/**
 * 사용자 정보 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const userWrap = document.getElementById('userWrap');
	
	// 이미지 선택 영역 토글
	const avatarEditButton = userWrap.querySelector('.image_field .edit_btn');
	const avatarContent = userWrap.querySelector('.image_field .user_avatar');
	const avatarForm = document.getElementById('editUserAvatarForm');
	avatarEditButton.addEventListener('click', function(event) {
		event.preventDefault();
		
		if (avatarForm.querySelector('.image_list').style.display === 'flex') {
			avatarForm.querySelector('.image_list').style.display = 'none';
			avatarEditButton.querySelector('p').textContent = '프로필 이미지 설정';
			avatarEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
		}
		else {
			avatarForm.querySelector('.image_list').style.display = 'flex';
			avatarEditButton.querySelector('p').textContent = '취소';
			avatarEditButton.querySelector('img').src = '/images/mypage/cancel_1a7ce2.png';
		}
	});

	// 이미지 선택 시 적용
	avatarForm.querySelectorAll('.image_list button').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			const src = this.querySelector('img').src;
			avatarContent.style.backgroundImage = `url("${src}")`;
			avatarContent.style.backgroundColor = '#F1F6FD';
			avatarContent.style.backgroundSize = '65%';
			avatarContent.style.backgroundPosition = 'center';
			avatarContent.style.backgroundRepeat = 'no-repeat';
			avatarContent.style.borderColor = '#D7DBE6';
			
			avatarForm.querySelector('.image_list').style.display = 'none';
			avatarEditButton.querySelector('p').textContent = '프로필 이미지 설정';
			avatarEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
			
			avatarForm.avatar.value = src.substring(src.indexOf('/images')); ;
			editUserAvatar();
		});
	});
	
	// 닉네임 수정폼 토글
	const nicknameEditButton = userWrap.querySelector('.nickname_field .edit_btn');
	const nicknameContent = userWrap.querySelector('.nickname_field .nickname_content');
	const nicknameForm = document.getElementById('editUserNicknameForm');
	nicknameEditButton.addEventListener('click', function(event) {
		event.preventDefault();
		
		if (nicknameContent.style.display === 'none') {
			nicknameContent.style.display = 'flex';
			nicknameForm.style.display = 'none';
			nicknameEditButton.querySelector('p').textContent = '수정';
			nicknameEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
			nicknameForm.nickname.value = '';
			nicknameForm.querySelector('button[type="submit"]').classList.add('disable');
			nicknameForm.querySelector('button[type="submit"]').disabled = true;
		}
		else {
			nicknameContent.style.display = 'none';
			nicknameForm.style.display = 'block';
			nicknameForm.nickname.value = nicknameContent.querySelector('p').innerText;
			nicknameEditButton.querySelector('p').textContent = '취소';
			nicknameEditButton.querySelector('img').src = '/images/mypage/cancel_1a7ce2.png';
		}
	});
	
	// 닉네임 실시간 검사
	nicknameForm.nickname.addEventListener('input', function(event) {
		event.preventDefault();
		const isNicknameEmpty = this.value.trim() === '';
		nicknameForm.querySelector('button[type="submit"]').classList.toggle('disable', isNicknameEmpty);
		nicknameForm.querySelector('button[type="submit"]').disabled = isNicknameEmpty;
	});
	
	// 소개 수정폼 토글
	const introduceEditButton = userWrap.querySelector('.introduce_field .edit_btn');
	const introduceContent = userWrap.querySelector('.introduce_field .user_introduce');
	const introduceForm = document.getElementById('editUserIntroduceForm');
	introduceEditButton.addEventListener('click', function(event) {
		event.preventDefault();
		
		if (introduceContent.style.display === 'none') {
			introduceContent.style.display = 'block';
			introduceForm.style.display = 'none';
			introduceEditButton.querySelector('p').textContent = '수정';
			introduceEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
			introduceForm.introduce.value = '';
			introduceForm.querySelector('button[type="submit"]').classList.add('disable');
			introduceForm.querySelector('button[type="submit"]').disabled = true;
		}
		else {
			introduceContent.style.display = 'none';
			introduceForm.style.display = 'block';
			introduceForm.introduce.value = introduceContent.innerText;
			introduceEditButton.querySelector('p').textContent = '취소';
			introduceEditButton.querySelector('img').src = '/images/mypage/cancel_1a7ce2.png';
		}
	});
	
	// 소개 실시간 검사
	introduceForm.introduce.addEventListener('input', function(event) {
		event.preventDefault();
		const isIntroduceEmpty = this.value.trim() === '';
		introduceForm.querySelector('button[type="submit"]').classList.toggle('disable', isIntroduceEmpty);
		introduceForm.querySelector('button[type="submit"]').disabled = isIntroduceEmpty;
	});
	
});





/**
 * 사용자 이미지를 수정하는 함수
 */
async function editUserAvatar() {
	
	// 이미지 수정 폼
	const form = document.getElementById('editUserAvatarForm');
	
	// 폼값 검사
	if (form.avatar.value.trim() === '') {
		return;
	}
	
	// 이미지 수정
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const data = {
			avatar: form.avatar.value.trim()
		};
		
		const response = await instance.patch(`/users/${id}`, data);
		
		if (response.data.code === 'USER_UPDATE_SUCCESS') {
			fetchUser();
		}
	}
	catch(error) {
		const code = error?.response?.data?.code;
						
		if (code === 'USER_UPDATE_FAIL') {
			alertDanger('사용자 정보 수정에 실패했습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인하지 않은 사용자입니다.');
		}
		else if (code === 'USER_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'USER_TEL_DUPLICATED') {
			alertDanger('사용자 전화번호가 중복되었습니다.');
		}
		else if (code === 'USER_EMAIL_DUPLICATED') {
			alertDanger('사용자 이메일이 중복되었습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 알 수 없는 오류가 발생했습니다.');
		}
		else {
			console.log('알 수 없는 오류가 발생했습니다.');
		}
	}
}





/**
 * 사용자 닉네임을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 닉네임 수정 폼
	const nicknameForm = document.getElementById('editUserNicknameForm');
	const nicknameEditButton = userWrap.querySelector('.nickname_field .edit_btn');
	const nicknameContent = userWrap.querySelector('.nickname_field .nickname_content');
	
	nicknameForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		// 폼값 검사
		if (nicknameForm.nickname.value.trim() === '') {
			return;
		}
		
		// 이미지 수정
		try {
			const id = parseJwt(localStorage.getItem('accessToken')).id;
			
			const data = {
				nickname: nicknameForm.nickname.value.trim()
			};
			
			const response = await instance.patch(`/users/${id}`, data);
			
			if (response.data.code === 'USER_UPDATE_SUCCESS') {
				nicknameContent.style.display = 'flex';
				nicknameForm.style.display = 'none';
				nicknameEditButton.querySelector('p').textContent = '수정';
				nicknameEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
				nicknameForm.nickname.value = '';
				nicknameForm.querySelector('button[type="submit"]').classList.add('disable');
				nicknameForm.querySelector('button[type="submit"]').disabled = true;
				fetchUser();
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
							
			if (code === 'USER_UPDATE_FAIL') {
				alertDanger('사용자 정보 수정에 실패했습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인하지 않은 사용자입니다.');
			}
			else if (code === 'USER_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'USER_TEL_DUPLICATED') {
				alertDanger('사용자 전화번호가 중복되었습니다.');
			}
			else if (code === 'USER_EMAIL_DUPLICATED') {
				alertDanger('사용자 이메일이 중복되었습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 알 수 없는 오류가 발생했습니다.');
			}
			else {
				console.log('알 수 없는 오류가 발생했습니다.');
			}
		}
	});
});





/**
 * 사용자 소개를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 닉네임 수정 폼
	const introduceForm = document.getElementById('editUserIntroduceForm');
	const introduceEditButton = userWrap.querySelector('.introduce_field .edit_btn');
	const introduceContent = userWrap.querySelector('.introduce_field .user_introduce');
	
	introduceForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		// 폼값 검사
		if (introduceForm.introduce.value.trim() === '') {
			return;
		}
		
		// 이미지 수정
		try {
			const id = parseJwt(localStorage.getItem('accessToken')).id;
			
			const data = {
				introduce: introduceForm.introduce.value.trim()
			};
			
			const response = await instance.patch(`/users/${id}`, data);
			
			if (response.data.code === 'USER_UPDATE_SUCCESS') {
				introduceContent.style.display = 'block';
				introduceForm.style.display = 'none';
				introduceEditButton.querySelector('p').textContent = '수정';
				introduceEditButton.querySelector('img').src = '/images/mypage/edit_1a7ce2.png';
				introduceForm.introduce.value = '';
				introduceForm.querySelector('button[type="submit"]').classList.add('disable');
				introduceForm.querySelector('button[type="submit"]').disabled = true;
				fetchUser();
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
							
			if (code === 'USER_UPDATE_FAIL') {
				alertDanger('사용자 정보 수정에 실패했습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인하지 않은 사용자입니다.');
			}
			else if (code === 'USER_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'USER_TEL_DUPLICATED') {
				alertDanger('사용자 전화번호가 중복되었습니다.');
			}
			else if (code === 'USER_EMAIL_DUPLICATED') {
				alertDanger('사용자 이메일이 중복되었습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 알 수 없는 오류가 발생했습니다.');
			}
			else {
				console.log('알 수 없는 오류가 발생했습니다.');
			}
		}
	});
});






















