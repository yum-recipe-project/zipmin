/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 15;
let keyword = '';
let category = '';
let sortKey = 'id';
let sortOrder = 'desc';
let recipeList = [];





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin('/');
	}
	
});





/**
 * 레시피 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchRecipeList();
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab a').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab a.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			keyword = '';
			document.getElementById('text-srh').value = '';
			sortKey = 'id';
			sortOrder = 'desc';
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			document.querySelector(`.sort_btn[data-key="${sortKey}"]`).classList.add(sortOrder);
			
			recipeList = [];
			fetchRecipeList();
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
			fetchRecipeList();
		});
	});
	
	fetchRecipeList();
});





/**
 * 서버에서 레시피 목록 데이터를 가져오는 함수
 */
async function fetchRecipeList(scrollTop = true) {
	
	try {
		const params = new URLSearchParams({
			category: category,
			sort: sortKey + '-' + sortOrder,
			keyword: keyword,
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/admin/recipes?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'RECIPE_READ_LIST_SUCCESS') {
			// 전역 변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			recipeList = response.data.data.content;
			
			// 렌더링
			renderRecipeList(recipeList);
			renderAdminPagination(fetchRecipeList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;

			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'RECIPE_READ_LIST_FAIL') {
			alertDanger('레시피 목록 조회에 실패했습니다.');
		}
		else if (code === 'RECIPE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'AUTH_TOKEN_INVALID') {
			redirectToAdminLogin();
		}
		else if (code === 'RECIPE_FORBIDDEN') {
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
 * 레시피 목록을 화면에 렌더링하는 함수
 */
function renderRecipeList(recipeList) {
	
	const container = document.querySelector('.recipe_list');
	container.innerHTML = '';
	
	// 레시피 목록이 존재하지 않는 경우
	if (recipeList == null || recipeList.length === 0) {
		document.querySelector('.table_th').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.fixed-table').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}
	
	// 레시피 목록이 존재하는 경우
	document.querySelector('.search_empty')?.remove();
	document.querySelector('.table_th').style.display = '';
	
	recipeList.forEach((recipe, index) => {
		const tr = document.createElement('tr');
		tr.dataset.id = recipe.id;
		
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
		
		// 제목
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-1 view';
		titleH6.textContent = recipe.title;
		titleH6.dataset.bsToggle = 'modal';
		titleH6.dataset.bsTarget = '#viewRecipeModal';
		titleH6.dataset.id = recipe.id;
		const subInfo = document.createElement('small');
		subInfo.className = 'text-muted d-block';
		subInfo.textContent = `${recipe.cooklevel} · ${recipe.cooktime} · ${recipe.spicy}`;
		titleTd.append(titleH6, subInfo);
		
		// 작성자
		const writerTd = document.createElement('td');
		const writerH6 = document.createElement('h6');
		writerH6.className = 'fw-semibold mb-0';
		writerH6.textContent = `${recipe.nickname} (${recipe.username})`;
		writerTd.appendChild(writerH6);
		
		// 작성일
		const dateTd = document.createElement('td');
		const dateH6 = document.createElement('h6');
		dateH6.className = 'fw-semibold mb-0';
		dateH6.textContent = formatDateTime(recipe.postdate);
		dateTd.appendChild(dateH6);
		
		// 별점
		const scoreTd = document.createElement('td');
		const scoreH6 = document.createElement('h6');
		scoreH6.className = 'fw-semibold mb-0';
		scoreH6.textContent = `${recipe.reviewscore} (${recipe.reviewcount})`;
		scoreTd.appendChild(scoreH6);
		
		// 좋아요수
		const likeTd = document.createElement('td');
		const likeH6 = document.createElement('h6');
		likeH6.className = 'fw-semibold mb-0';
		likeH6.textContent = recipe.likecount;
		likeTd.appendChild(likeH6);
		
		// 댓글수
		const commentTd = document.createElement('td');
		const commentH6 = document.createElement('h6');
		commentH6.className = 'fw-semibold mb-0';
		commentH6.textContent = recipe.commentcount;
		commentTd.appendChild(commentH6);
		
		// 신고수
		const reportTd = document.createElement('td');
		reportTd.classList.add('report-td');
		const reportWrap = document.createElement('div');
		reportWrap.className = 'report-cell d-inline-block position-relative';
		const reportCount = document.createElement('h6');
		reportCount.className = 'fw-semibold mb-0';
		reportCount.textContent = recipe.reportcount;
		
		// 신고 모달창 열기
		if ((recipe.reportcount ?? 0) > 0) {
			reportCount.className = 'fw-semibold mb-0 view';
			reportCount.dataset.bsToggle = 'modal';
			reportCount.dataset.bsTarget = '#listReportModal';
			reportCount.dataset.recodenum = recipe.id;
			reportCount.dataset.tablename = 'recipe';
			reportCount.addEventListener('click', (event) => {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					return;
				}
			});
		}
		reportWrap.appendChild(reportCount);
		reportTd.appendChild(reportWrap);
		
		// 기능
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 기능 버튼 조건
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		const canAction =
		    payload.role === 'ROLE_SUPER_ADMIN' ||
		    (payload.role === 'ROLE_ADMIN' && recipe.role === 'ROLE_USER') ||
		    (payload.id === recipe.user_id);
			
		if (canAction) {
			// 삭제 버튼
			const deleteBtn = document.createElement('button');
			deleteBtn.type = 'button';
			deleteBtn.className = 'btn btn-sm btn-outline-danger';
			deleteBtn.innerHTML = '삭제';
			deleteBtn.onclick = () => deleteRecipe(recipe.id);
			
			btnWrap.appendChild(deleteBtn);
		}
		
		actionTd.appendChild(btnWrap);
		
		tr.append(noTd, titleTd, writerTd, dateTd, scoreTd, likeTd, commentTd, reportTd, actionTd);
		container.appendChild(tr);
	});
}





/**
 * 레시피를 삭제하는 함수
 */
async function deleteRecipe(id) {
	
	try {
		const response = await instance.delete(`/recipes/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'RECIPE_DELETE_SUCCESS') {
			alertPrimary('레시피가 성공적으로 삭제되었습니다.');
			fetchRecipeList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'RECIPE_DELETE_FAIL') {
			alertDanger('레시피 삭제에 실패했습니다.');
		}
		else if (code === 'RECIPE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'RECIPE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'RECIPE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'RECIPE_NOT_FOUND') {
			alertDanger('해당 레시피를 찾을 수 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code == 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			alertDanger('알 수 없는 오류가 발생했습니다.')
			console.log(error);
		}
	}
}