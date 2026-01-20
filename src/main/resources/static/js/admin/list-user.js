/**
 * 전역변수
 */
let page = 0;
const size = 15;
let totalPages = 0;
let totalElements = 0;
let field = 'username';
let keyword = '';
let category = '';
let sortKey = 'id';
let sortOrder = 'desc';
let userList = [];





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin();
	}
	
});





/**
 * 카테고리 클릭 시 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.getElementById('field-srh').addEventListener('change', function() {
		field = this.value;
		fetchUserList();
	});
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchUserList();
	});
	document.getElementById('text-srh')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchUserList();
		}
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab a').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab a.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			field = 'username';
			keyword = '';
			document.getElementById('field-srh').value = 'username';
			document.getElementById('text-srh').value = '';
			sortKey = 'id';
			sortOrder = 'desc';
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			document.querySelector(`.sort_btn[data-key="${sortKey}"]`).classList.add(sortOrder);
			
			userList = [];
			fetchUserList();
		});
	});
	
	// 정렬 버튼
	document.querySelectorAll('.sort_btn').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			const key = btn.dataset.key;
		    if (sortKey === key) {
		      sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
		    }
			else {
		      sortKey = key;
		      sortOrder = 'desc';
		    }
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			this.classList.add(sortOrder);
			page = 0;
			fetchUserList();
		});
	});
	
	// 관리자 추가 버튼
	const token = localStorage.getItem('accessToken');
	const payload = parseJwt(token);
	if (payload.role === 'ROLE_SUPER_ADMIN') {
		renderAddAdminButton();
	}
	
	// 초기 실행
	fetchUserList();
});





/**
 * 서버에서 회원 목록 데이터를 가져오는 함수
 */
async function fetchUserList(scroll = true) {
	
	try {
		const params = new URLSearchParams({
			field: field,
			keyword: keyword,
			category: category,
			sort: sortKey + '-' + sortOrder,
			page: page,
			size: size,
		}).toString();
		
		const response = await instance.get(`/admin/users?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {

			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			userList = response.data.data.content;
			
			renderUserList(userList);
			renderAdminPagination(fetchUserList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			if (scroll) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'USER_READ_LIST_FAIL') {
			alertDanger('사용자 목록 조회에 실패했습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'AUTH_TOKEN_INVALID') {
			redirectToAdminLogin();
		}
		else if (code === 'USER_FORBIDDEN') {
			redirectToAdminLogin();
		}
		else if (code === 'USER_NOT_FOUND') {
			redirectToAdminLogin();
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
	
}




/**
 * 관리자 추가 버튼을 화면에 렌더링하는 함수
 */
function renderAddAdminButton() {
	
	const createBtn = document.createElement('button');
	createBtn.type = 'button';
	createBtn.className = 'btn btn-info m-1';
	createBtn.dataset.bsToggle = 'modal';
	createBtn.dataset.bsTarget = '#addAdminModal';

	const icon = document.createElement('i');
	icon.className = 'ti ti-plus fs-4';

	const text = document.createTextNode('관리자 생성하기');

	createBtn.append(icon, text);
	document.querySelector('.bar').appendChild(createBtn);
	
};





/**
 * 회원 목록을 화면에 렌더링하는 함수
 */
function renderUserList(userList) {
	
	const container = document.querySelector('.user_list');
	container.innerHTML = '';
	
	// 사용자 목록이 존재하지 않는 경우
	if (userList == null || userList.length === 0) {
		document.querySelector('.table_th').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.fixed-table').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}
	
	// 사용자 목록이 존재하는 경우
	document.querySelector('.search_empty')?.remove();
	document.querySelector('.table_th').style.display = '';
	
	userList.forEach((user, index) => {
		const tr = document.createElement('tr');
		tr.dataset.id = user.id;
		
		// 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		const offset = page * size + index;
		if (sortKey === 'id' && sortOrder === 'asc') {
			noH6.textContent = offset + 1;
		}
		else {
			noH6.textContent = totalElements - offset;
		}
		noTd.appendChild(noH6);
		
		// 아이디
		const usernameTd = document.createElement('td');
		const usernameH6 = document.createElement('h6');
		usernameH6.className = 'fw-semibold mb-0';
		usernameH6.textContent = user.username;
		usernameTd.appendChild(usernameH6);
		
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
			user.role === 'ROLE_SUPER_ADMIN' ? '총관리자' :
			user.role === 'ROLE_ADMIN' ? '관리자' : '일반 회원';
		roleTd.appendChild(roleH6);

		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 기능 버튼 조건
		const canAction =
		    payload.role === 'ROLE_SUPER_ADMIN' ||
		    (payload.role === 'ROLE_ADMIN' && user.role === 'ROLE_USER') ||
		    (payload.id === user.id);

		if (canAction) {
			// 수정 버튼
		    const editBtn = document.createElement('button');
		    editBtn.type = 'button';
		    editBtn.className = 'btn btn-sm btn-outline-info';
			editBtn.dataset.bsToggle = 'modal';
			editBtn.dataset.bsTarget = '#editUserModal';
		    editBtn.innerHTML = '수정';
			// admin/list-comment.js보고 수정
		    editBtn.onclick = () => {
				if (!isLoggedIn()) {
					redirectToLogin();
					return;
				}
				const form = document.getElementById('editUserForm');
				form.id.value = user.id;
				form.username.value = user.username;
				form.name.value = user.name;
				form.nickname.value = user.nickname;
				form.tel.value = user.tel;
				form.email.value = user.email;
			};

			// 삭제 버튼
		    const deleteBtn = document.createElement('button');
		    deleteBtn.type = 'button';
		    deleteBtn.className = 'btn btn-sm btn-outline-danger';
		    deleteBtn.innerHTML = '삭제';
		    deleteBtn.onclick = () => deleteUser(user.id);
			
		    btnWrap.append(editBtn, deleteBtn);
		}

		actionTd.appendChild(btnWrap);

		tr.append(noTd, usernameTd, nameTd, nicknameTd, telTd, emailTd, roleTd, actionTd);

		container.appendChild(tr);
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
			
			if (code === 'USER_DELETE_FAIL') {
				alert('사용자 삭제에 실패했습니다.');
			}
			if (code === 'USER_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.')
			}
			if (code === 'USER_UNAUTHORIZED_ACCESS') {
				alert('로그인되지 않은 사용자입니다.');
			}
			if (code === 'USER_NOT_FOUND') {
				alert('해당 사용자를 찾을 수 없습니다.');
			}
			if (code === 'USER_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			if (code === 'USER_SUPER_ADMIN_FORBIDDEN') {
				alert('총관리자는 삭제할 수 없습니다.');
			}
			if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
}













