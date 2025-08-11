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
		redirectToLogin();
	}
});





/**
 * 전역변수
 */
let category = '';
let totalPages = 0;
let page = 0;
const size = 10;
let userList = [];





/**
 * 카테고리 클릭 시 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.querySelectorAll('.btn_tab a').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab a.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			userList = [];
			
			fetchUserList();
		});
	});
	
	fetchUserList();
});





/**
 * 서버에서 회원 목록 데이터를 가져오는 함수
 */
async function fetchUserList() {
	
	if (!isLoggedIn()) {
		directToLogin();
	}
	
	try {
		const token = localStorage.getItem('accessToken');
		
		const params = new URLSearchParams({
			page : page,
			size : size,
			category : category
		}).toString();
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		}
		
		const response = await instance.get(`/users?${params}`, {
			headers: headers
		});
		
		console.log(response);
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {
			totalPages = response.data.data.totalPages;
			page = response.data.data.number;
			megazineList = response.data.data.content;
			
			renderUserList(response.data.data.content);
			renderPagination();
			
			document.querySelector('.total').innerText = `총 ${response.data.data.totalElements}개`;
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		/******* 이거 코드 더 추가해야함 !!!!!  */
		if (code === 'USER_READ_LIST_FAIL') {
			alert('사용자 목록 조회에 실패했습니다.');
		}
		else {
			console.log(error);
		}
	}
	
}





/**
 * 회원 목록을 화면에 렌더링하는 함수
 */
function renderUserList(userList) {
	const container = document.querySelector('.user_list');
	container.innerHTML = '';
	
	userList.forEach((user, index) => {
		const tr = document.createElement('tr');
		tr.dataset.id = user.id;
		
		// 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		noH6.textContent = index + 1;
		noTd.appendChild(noH6);
		
		// 아이디
		const usernameTd = document.createElement('td');
		const usernameH6 = document.createElement('h6');
		usernameH6.className = 'fw-semibold mb-0';
		usernameH6.textContent = user.username;
		usernameTd.appendChild(usernameH6);
		// usernameTd.onclick = () => location.href = `/admin/viewMegazine.do?id=${megazine.id}`; 
		
		// 이름
		const nameTd = document.createElement('td');
		const nameH6 = document.createElement('h6');
		nameH6.className = 'fw-semibold mb-0';
		nameH6.textContent = user.name;
		nameTd.appendChild(nameH6);

		// 닉네임
		const nicknameTd = document.createElement('td');
		const nicknameH6 = document.createElement('h6');
		nicknameH6.className = 'fw-semibold mb-0';
		nicknameH6.textContent = user.nickname;
		nicknameTd.appendChild(nicknameH6);
		
		// 휴대폰 번호
		const telTd = document.createElement('td');
		const telH6 = document.createElement('h6');
		telH6.className = 'fw-semibold mb-0';
		telH6.textContent = user.tel;
		telTd.appendChild(telH6);

		// 이메일
		const emailTd = document.createElement('td');
		const emailH6 = document.createElement('h6');
		emailH6.className = 'fw-semibold mb-0';
		emailH6.textContent = user.email;
		emailTd.appendChild(emailH6);
		
		// 권한
		const roleTd = document.createElement('td');
		const roleH6 = document.createElement('h6');
		roleH6.className = 'fw-semibold mb-0';
		roleH6.textContent =
			user.role === 'ROLE_SUPER_ADMIN' ? '최고 관리자' :
			user.role === 'ROLE_ADMIN' ? '관리자' : '일반 회원';
		roleTd.appendChild(roleH6);

		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 수정 버튼 조건
		const canEdit =
		    payload.role === 'ROLE_SUPER_ADMIN' ||
		    (payload.role === 'ROLE_ADMIN' && user.role === 'ROLE_USER') ||
		    (payload.id === user.id);

		if (canEdit) {
		    const editBtn = document.createElement('button');
		    editBtn.type = 'button';
		    editBtn.className = 'btn btn-sm btn-outline-info';
		    editBtn.innerHTML = '수정';
		    // editBtn.onclick = () => { location.href = `/admin/editUser.do?id=${user.id}`; };
		    btnWrap.appendChild(editBtn);
		}

		// 삭제 버튼 조건
		const canDelete =
		    (payload.role === 'ROLE_SUPER_ADMIN' && payload.id !== user.id) ||
		    (payload.role === 'ROLE_ADMIN' && user.role === 'ROLE_USER') || 
			(payload.id === user.id);

		if (canDelete) {
		    const deleteBtn = document.createElement('button');
		    deleteBtn.type = 'button';
		    deleteBtn.className = 'btn btn-sm btn-outline-danger';
		    deleteBtn.innerHTML = '삭제';
		    deleteBtn.onclick = () => deleteUser(user.id);
		    btnWrap.appendChild(deleteBtn);
		}

		actionTd.appendChild(btnWrap);

		tr.append(noTd, usernameTd, nameTd, nicknameTd, telTd, emailTd, roleTd, actionTd);

		container.appendChild(tr);
	});
	
}






/**
 * 페이지네이션을 화면에 렌더링하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchUserList();
			}
		});
	});
}





/**
 * 회원을 삭제하는 함수
 */
async function deleteUser(id) {
	
	if (confirm('사용자를 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
			
			const response = await instance.delete(`/users/${id}`, {
				headers: headers
			});
			
			if (response.data.code === 'USER_DELETE_SUCCESS') {
				alert('사용자가 성공적으로 삭제되었습니다.');
				if (payload.id === id) {
					localStorage.removeItem('accessToken');
					location.href = '/admin/login.do';
				}
				else {
					const trElement = document.querySelector(`.user_list tr[data-id='${id}']`);
					if (trElement) trElement.remove();
				}
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			
			/**** 더 추가해야 함 !!!! */
			if (code === 'USER_DELETE_FAIL') {
				alert('사용자 삭제에 실패했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
}













