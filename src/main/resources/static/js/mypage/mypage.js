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
 * 초기 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	fetchUser();
	fetchLikeUserList();
	fetchLikedUserList();
	
});





/**
 * 사용자 프로필 데이터를 가져오는 함수
 */
async function fetchUser() {
	
	const userWrap = document.getElementById('userWrap');
	
	// 사용자 프로필 조회
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		// *** TODO : 인스턴스로 변경 ***
		const response = await fetch(`/users/${id}/profile`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
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
 * 사용자가 좋아요한 사용자 목록 데이터를 가져오는 함수
 */
async function fetchLikeUserList() {
	
	const modal = document.getElementById('viewLikeUserModal');
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
				
		const response = await instance.get(`/users/${id}/like-users`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {
			modal.querySelector('.user_count span').innerText = `총 ${response.data.data.length}명`;
			renderLikeUserList(response.data.data);
		}
	}
	catch(error) {
		console.log(error);
		
		// *** TODO : 에러코드 작성하기 ***
	}
	
}





/**
 * 사용자가 좋아요한 사용자 목록 데이터를 가져오는 함수
 */
async function fetchLikedUserList() {
	
	const modal = document.getElementById('viewLikedUserModal');
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
				
		const response = await instance.get(`/users/${id}/liked-users`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {
			modal.querySelector('.user_count span').innerText = `총 ${response.data.data.length}명`;
			renderLikedUserList(response.data.data);
		}
		
	}
	catch(error) {
		console.log(error);
		
		// *** TODO : 에러코드 작성하기 ***
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





/**
 * 사용자가 좋아요한 사용자 목록을 화면에 렌더링하는 함수
 */
function renderLikeUserList(userList) {
	
	const modal = document.getElementById('viewLikeUserModal');
	const container = modal.querySelector('.user_list');
	container.innerHTML = '';
	
	// 사용자 목록이 존재하지 않는 경우
	if (userList == null || userList.length === 0) {
		container.style.display = 'none';
		modal.querySelector('list_empty')?.remove();
		
		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';
		
		const span = document.createElement('span');
		span.textContent = '내용이 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);
		
		return;
	}
	
	// 사용자 목록이 존재하는 경우
	userList.forEach(user => {
		container.style.disaply = 'block';
		modal.querySelector('list_empty')?.remove();
		
		const li = document.createElement('li');
		
		const img = document.createElement('img');
		img.src = user.avatar;
		
		const info = document.createElement('div');
		info.className = 'user_info';
		
		const nickname = document.createElement('span');
		nickname.textContent = user.nickname;
		info.appendChild(nickname);
		
		if (user.introduce) {
			const intro = document.createElement('p');
			intro.textContent = user.introduce;
			info.appendChild(intro);
		}
		
		const button = document.createElement('button');
		button.className = user.liked ? 'btn_outline_small' : 'btn_primary_small';
		button.dataset.isLiked = user.liked;
		button.dataset.userId = user.id;
		button.addEventListener('click', function(event) {
			event.preventDefault();
			likeUser(button);
		});
		
		const btnText = document.createElement('span');
		btnText.textContent = user.liked ? '팔로잉' : '팔로우';
		
		button.appendChild(btnText);
		
		li.append(img, info, button);
		container.appendChild(li);
	});
	
}





/**
 * 사용자를 좋아요한 사용자 목록을 화면에 렌더링하는 함수
 */
function renderLikedUserList(userList) {
	
	const modal = document.getElementById('viewLikedUserModal');
	const container = modal.querySelector('.user_list');
	container.innerHTML = '';
	
	// 사용자 목록이 존재하지 않는 경우
	if (userList == null || userList.length === 0) {
		container.style.display = 'none';
		modal.querySelector('list_empty')?.remove();
		
		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';
		
		const span = document.createElement('span');
		span.textContent = '내용이 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);
		
		return;
	}
	
	// 사용자 목록이 존재하는 경우
	userList.forEach(user => {
		container.style.disaply = 'block';
		modal.querySelector('list_empty')?.remove();
		
		const li = document.createElement('li');
		
		const img = document.createElement('img');
		img.src = user.avatar;
		
		const info = document.createElement('div');
		info.className = 'user_info';
		
		const nickname = document.createElement('span');
		nickname.textContent = user.nickname;
		info.appendChild(nickname);
		
		if (user.introduce) {
			const intro = document.createElement('p');
			intro.textContent = user.introduce;
			info.appendChild(intro);
		}
		
		const button = document.createElement('button');
		button.className = user.liked ? 'btn_outline_small' : 'btn_primary_small';
		button.dataset.isLiked = user.liked;
		button.dataset.userId = user.id;
		button.addEventListener('click', function(event) {
			event.preventDefault();
			likeUser(button);
		});
		
		const btnText = document.createElement('span');
		btnText.textContent = user.liked ? '팔로잉' : '팔로우';
		
		button.appendChild(btnText);
		
		li.append(img, info, button);
		container.appendChild(li);
	});
	
}





/**
 * 사용자 좋아요 및 좋아요를 취소하는 함수
 */
async function likeUser(button) {
	
	if (!isLoggedIn()) {
		redirectToLogin();
		return;
	}
	
	// 사용자 좋아요 취소
	if (button.dataset.isLiked === 'true') {
		try {
			const id = button.dataset.userId;
			
			const data = {
				tablename: 'users',
				recodenum: id
			}
			
			const response = await instance.delete(`/users/${id}/likes`, {
				data: data,
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'USER_UNLIKE_SUCCESS') {
				fetchLikeUserList();
				fetchLikedUserList();
				alertPrimary('사용자 좋아요 취소에 성공했습니다.');
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
			
			if (code === 'USER_UNLIKE_FAIL') {
				alertDanger('사용자 좋아요 취소에 실패했습니다.');
			}
			else if (code === 'LIKE_DELETE_FAIL') {
				alertDanger('좋아요 삭제에 실패했습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.')
			}
			else if (code === 'LIKE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_UNAUTHORIZED') {
				alertDanger('로그인되지 않은 사용자입니다.')
			}
			else if (code === 'LIKE_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'LIKE_NOT_FOUND') {
				alertDanger('해당 좋아요를 찾을 수 없습니다');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
	
	// 사용자 좋아요
	else if (button.dataset.isLiked === 'false') {
		try {
			const id = button.dataset.userId;
			
			const data = {
				tablename: 'users',
				recodenum: id
			}
			
			const response = await instance.post(`/users/${id}/likes`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'USER_LIKE_SUCCESS') {
				fetchLikeUserList();
				fetchLikedUserList();
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
			
			if (code === 'USER_LIKE_FAIL') {
				alertDanger('사용자 좋아요에 실패했습니다.');
			}
			else if (code === 'LIKE_CREATE_FAIL') {
				alertDanger('좋아요 작성에 실패했습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.')
			}
			else if (code === 'LIKE_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_UNAUTHORIZED') {
				alertDanger('로그인되지 않은 사용자입니다.')
			}
			else if (code === 'LIKE_NOT_FOUND') {
				alertDanger('해당 좋아요를 찾을 수 없습니다');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'LIKE_DUPLICATE') {
				alertDanger('이미 좋아요한 사용자입니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
}


