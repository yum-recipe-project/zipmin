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
	
	fetchLikeUserList();
	
});





/**
 * 사용자가 좋아요한 사용자 목록 데이터를 가져오는 함수
 */
async function fetchLikeUserList() {
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
				
		const response = await instance.get(`/users/${id}/like-users`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {
			// *** TODO : ***
			
			renderLikeUserList(response.data.data);
		}
	}
	catch(error) {
		console.log(error);
		
		// *** TODO : 에러코드 작성하기 ***
	}
	
}




/**
 * 사용자가 좋아요한 사용자 목록을 화면에 렌더링하는 함수
 */
function renderLikeUserList(userList) {
	
	const modal = document.getElementById('viewLikeUserModal');
	const container = modal.querySelector('.user_list');
	container.innerHTML = '';
	
	userList.forEach(user => {
		const li = document.createElement('li');
		
		const img = document.createElement('img');
		img.src = user.avatar;
		
		const info = document.createElement('div');
		info.className = 'user_info';
		
		const nickname = document.createElement('span');
		nickname.textContent = user.nickname;
		
		const intro = document.createElement('p');
		intro.textContent = user.introduce;
		
		info.appendChild(nickname);
		info.appendChild(intro);
		
		const button = document.createElement('button');
		button.className = user.liked ? 'btn_outline_small' : 'btn_primary_small';
		button.dataset.isLiked = user.liked;
		button.dataset.userId = user.id;
		button.addEventListener('click', function(event) {
			event.preventDefault();
			test(button);
		});
		
		const btnText = document.createElement('span');
		btnText.textContent = user.liked ? '팔로우 취소' : '팔로우';
		
		button.appendChild(btnText);
		
		li.append(img, info, button);
		container.appendChild(li);
	});
	
}





/**
 * 사용자 좋아요 및 좋아요를 취소하는 함수
 */
// *** TODO : dataset 수정 ***
async function test(button) {
	
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
			









