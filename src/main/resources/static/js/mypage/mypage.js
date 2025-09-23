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
	
	const userWrap = document.getElementById('userWrap');
	
	// 이미지 선택 영역 토글
	const avatarEditButton = userWrap.querySelector('.image_field .edit_btn');
	const avatarForm = document.getElementById('editUserAvatarForm');
	avatarEditButton.addEventListener('click', function () {
		const isVisible = userWrap.querySelector('.image_list').style.display === 'block';
		userWrap.querySelector('.image_list').style.display = isVisible ? 'none' : 'block';
	});

	// 이미지 선택 시 적용
	avatarForm.querySelectorAll('.image_list button').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			const src = this.querySelector('img').src;
			userWrap.querySelector('.user_avatar').style.backgroundImage = `url("${src}")`;
			userWrap.querySelector('.user_avatar').style.backgroundColor = '#F1F6FD';
			userWrap.querySelector('.user_avatar').style.backgroundSize = '65%';
			userWrap.querySelector('.user_avatar').style.backgroundPosition = 'center';
			userWrap.querySelector('.user_avatar').style.backgroundRepeat = 'no-repeat';
			userWrap.querySelector('.user_avatar').style.borderColor = '#D7DBE6';
			userWrap.querySelector('.image_list').style.display = 'none';
			
			avatarForm.avatar.value = src.substring(src.indexOf('/images')); ;
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
		}
		else {
			nicknameContent.style.display = 'none';
			nicknameForm.style.display = 'block';
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
		}
		else {
			introduceContent.style.display = 'none';
			introduceForm.style.display = 'block';
			introduceEditButton.querySelector('p').textContent = '취소';
			introduceEditButton.querySelector('img').src = '/images/mypage/cancel_1a7ce2.png';
		}
	});
	
	// *** TODO : 소개 실시간 검사 ***
	
});











