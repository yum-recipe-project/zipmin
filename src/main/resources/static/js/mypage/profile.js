/**
 * 전역변수
 */
const recipeSize = 16;
const classSize = 16;
let totalPages = 0;
let recipeTotalElements = 0;
let classTotalElements = 0;
let page = 0;
let recipeSort = 'postdate-desc';
let classStatus = '';
let recipeList = [];
let classList = [];





/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const tabItems = document.querySelectorAll('.btn_tab a');
    const contentItems = document.querySelectorAll('.tab_content');

    // 탭 클릭 이벤트 설정
    tabItems.forEach((item, index) => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            
            tabItems.forEach(button => button.classList.remove('active'));
            this.classList.add('active');
            contentItems.forEach(content => content.style.display = 'none');
            contentItems[index].style.display = 'flex';
			
			recipeSort = 'postdate-desc';
			classStatus = '';
			page = 0;
			recipeList = [];
			classList = [];

			recipeWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			recipeWrap.querySelectorAll('.btn_sort')[0].classList.add('active');
			classWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			classWrap.querySelectorAll('.btn_sort')[0].classList.add('active');
			
			fetchUserRecipeList();
			fetchUserClassList();
        });
    });

    // 선택된 탭 활성화
    tabItems.forEach(button => button.classList.remove('active'));
    contentItems.forEach(content => content.style.display = 'none');
    tabItems[0].classList.add('active');
    contentItems[0].style.display = 'flex';
});





/**
 * 레시피와 클래스 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const recipeWrap = document.getElementById('recipeWrap');
	const classWrap = document.getElementById('classWrap');
	
	// 레시피 정렬 버튼
	recipeWrap.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			recipeWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			recipeList = [];
			
			fetchUserRecipeList();
		});
	});
	
	// 클래스 정렬 버튼
	classWrap.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			classWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			classStatus = btn.dataset.status;
			page = 0;
			classList = [];
			
			fetchUserClassList();
		});
	});
	
	fetchUser();
	fetchUserRecipeList();
	fetchUserClassList();
});





/**
 * 사용자 프로필 데이터를 가져오는 함수
 */
async function fetchUser() {
	
	const userWrap = document.getElementById('userWrap');
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/users/${id}/profile`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'USER_READ_SUCCESS') {
			userWrap.querySelector('.user_avatar').style.backgroundImage = `url(${result.data.avatar})`;
			userWrap.querySelector('.user_nickname').innerText = result.data.nickname;
			userWrap.querySelector('.user_title button').className = result.data.liked ? 'btn btn_outline' : 'btn btn_dark';
			userWrap.querySelector('.user_title button').innerText = result.data.liked ? '팔로우 취소' : '팔로우';
			userWrap.querySelector('.user_title button').dataset.isLiked = result.data.liked ? 'true' : 'false';
			userWrap.querySelector('.user_introduce').innerText = result.data.introduce;
			userWrap.querySelector('.likecount').innerText = `${result.data.likecount}명`;
			if (result.data.link) {
				userWrap.querySelector('.user_link').innerHTML = '';
				const p = document.createElement('p');
				p.className = 'user_link';
				p.textContent = result.data.link;
				userWrap.querySelector('.user_link').appendChild(p);
			}
		}
		else if (result.code === 'LIKE_COUNT_FAIL') {
			alertDanger('프로필 조회에 실패했습니다.');
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
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			throw new Error('서버 내부 오류 발생');
		}
	}
	catch(error) {
		console.log(error);
	}
}





/**
 * 사용자의 레시피 목록 데이터를 가져오는 함수
 */
async function fetchUserRecipeList() {
	
	const userWrap = document.getElementById('userWrap');
	const recipeWrap = document.getElementById('recipeWrap');
	
	// 레시피 목록 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const params = new URLSearchParams({
			sort: recipeSort,
			page : page,
			size : recipeSize
		}).toString();
		
		const response = await fetch(`/users/${id}/recipes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = result.data.totalPages;
			recipeTotalElements = result.data.totalElements;
			page = result.data.number;
			recipeList = result.data.content;
			
			// 렌더링
			userWrap.querySelector('.recipecount').innerText = `${recipeTotalElements}개`;
			recipeWrap.querySelector('.recipe_header').innerText = `레시피 ${recipeTotalElements}개`;
			renderUserRecipeList(recipeList);
			renderPagination(fetchUserRecipeList, recipeWrap);
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		else if (result.code === 'RECIPE_READ_LIST_FAIL') {
			alertDanger('사용자의 레시피 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'RECIPE_CATEGORY_READ_LIST_FAIL') {
			alertDanger('사용자의 레시피 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'LIKE_COUNT_FAIL') {
			alertDanger('사용자의 레시피 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'RECIPE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'LIKE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			throw new Error('서버 내부 오류 발생');
		}
	}
	catch (error) {
		console.log(error);
	}
}




/**
 * 사용자 클래스 목록 데이터를 가져오는 함수
 */
async function fetchUserClassList() {
	
	const classWrap = document.getElementById('classWrap');
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
				
		const params = new URLSearchParams({
			approval: 'APPROVED',
			status: classStatus.toUpperCase(),
			page : page,
			size : classSize
		}).toString();
		
		const response = await fetch(`/users/${id}/classes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'CLASS_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = result.data.totalPages;
			classTotalElements = result.data.totalElements;
			page = result.data.number;
			classList = result.data.content;
			
			// 렌더링
			classWrap.querySelector('.class_header').innerText = `클래스 ${classTotalElements}개`;
			renderUserClassList(classList);
			renderPagination(fetchUserClassList, classWrap);
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		else if (result.code === 'CLASS_READ_LIST_FAIL') {
			alertDanger('사용자의 클래스 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			throw new Error('서버 내부 오류 발생');
		}
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 사용자 레시피 목록을 화면에 렌더링하는 함수
 */
function renderUserRecipeList(recipeList) {
	
	const recipeWrap = document.getElementById('recipeWrap');
	const container = recipeWrap.querySelector('.recipe_list');
	container.innerHTML = '';
	
	// 사용자의 레시피 목록이 존재하지 않는 경우
	if (recipeList === null || recipeList.length === 0) {
		recipeWrap.querySelector('.recipe_list').style.display = 'none';
		recipeWrap.querySelector('.list_empty')?.remove();
		
		const wrapper = document.createElement('div');
	    wrapper.className = 'list_empty';
		
	    const span = document.createElement('span');
	    span.textContent = '작성된 레시피가 없습니다';
	    wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

	    return;
	}
	
	// 사용자의 레시피 목록이 존재하는 경우
	recipeWrap.querySelector('.list_empty')?.remove();
	recipeWrap.querySelector('.recipe_list').style.display = '';
	
	recipeList.forEach(recipe => {
		const li = document.createElement('li');
		li.className = 'recipe';
		
		const link = document.createElement('a');
		link.href = `/recipe/viewRecipe.do?id=${recipe.id}`;
		const img = document.createElement('img');
		img.className = 'recipe_image';
		img.src = recipe.image;
		link.appendChild(img);

		const info = document.createElement('div');
		info.className = 'recipe_info';

		const title = document.createElement('h3');
		title.className = 'recipe_title';
		title.textContent = recipe.title;
		
		const introduce = document.createElement('p');
		introduce.className = 'recipe_introduce';
		introduce.textContent = recipe.introduce;

		const detail = document.createElement('div');
		detail.className = 'recipe_detail';

		const levelItem = document.createElement('div');
		levelItem.className = 'detail_item';
		levelItem.innerHTML = `<img src="/images/recipe/level.png"><p>${recipe.cooklevel}</p>`;

		const timeItem = document.createElement('div');
		timeItem.className = 'detail_item';
		timeItem.innerHTML = `<img src="/images/recipe/time.png"><p>${recipe.cooktime}</p>`;

		const spicyItem = document.createElement('div');
		spicyItem.className = 'detail_item';
		spicyItem.innerHTML = `<img src="/images/recipe/spicy.png"><p>${recipe.spicy}</p>`;

		detail.append(levelItem, timeItem, spicyItem);

		const categoryItem = document.createElement('div');
		categoryItem.className = 'recipe_category';
		recipe.category_list.forEach(category => {
			const a = document.createElement('a');
			a.href = '#';
			a.textContent = category.tag;
			categoryItem.appendChild(a);
		});
		
		info.append(title, introduce, detail, categoryItem);
		li.append(link, info);
		container.appendChild(li);
	});
	
}





/**
 * 클래스 목록을 화면에 랜더링하는 함수
 */
function renderUserClassList(classList) {
	
	const classWrap = document.getElementById('classWrap');
	const container = classWrap.querySelector('.class_list');
	container.innerHTML = '';
	
	// 사용자의 클래스 목록이 존재하지 않는 경우
	if (classList === null || classList.length === 0) {
		classWrap.querySelector('.class_list').style.display = 'none';
		classWrap.querySelector('.list_empty')?.remove();
		
		const wrapper = document.createElement('div');
	    wrapper.className = 'list_empty';
		
	    const span = document.createElement('span');
	    span.textContent = '작성된 클래스가 없습니다';
	    wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

	    return;
	}

	// 사용자의 클래스 목록이 존재하는 경우
	classWrap.querySelector('.list_empty')?.remove();
	classWrap.querySelector('.class_list').style.display = '';
	
	classList.forEach(classs => {
		const li = document.createElement('li');
		li.className = 'class';
		
		const link = document.createElement('a');
		link.href = `/cooking/viewClass.do?id=${classs.id}`;
		
		const img = document.createElement('img');
		img.className = 'class_image';
		img.src = classs.image;
		link.appendChild(img);
		
		const flag = document.createElement('p');
		flag.className = classs.opened ? 'flag open' : 'flag';
		flag.textContent = classs.opened ? '모집중' : '마감';
		link.appendChild(flag);

		const info = document.createElement('div');
		info.className = 'class_info';

		const title = document.createElement('h3');
		title.className = 'class_title';
		title.textContent = classs.title;
		
		const introduce = document.createElement('p');
		introduce.className = 'class_introduce';
		introduce.textContent = classs.introduce;
		
		const detail = document.createElement('div');
		detail.className = 'class_detail';

		const dateItem = document.createElement('div');
		dateItem.className = 'detail_item';
		dateItem.innerHTML = `<img src="/images/recipe/time.png"><p>${formatDate(classs.eventdate)}</p>`;
		
		detail.appendChild(dateItem);

		info.append(title, introduce, detail);
		li.append(link, info);
		container.appendChild(li);
	});
}














/**
 * 사용자 좋아요 및 좋아요를 취소하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const userWrap = document.getElementById('userWrap');
	const button = userWrap.querySelector('.user_title button');
	
	button.addEventListener('click', async function(event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 사용자 좋아요 취소
		if (button.dataset.isLiked === 'true') {
			try {
				const id = new URLSearchParams(window.location.search).get('id');
				
				const data = {
					tablename: 'users',
					recodenum: id
				}
				
				const response = await instance.delete(`/users/${id}/likes`, {
					data: data,
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'USER_UNLIKE_SUCCESS') {
					fetchUser();
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
				const id = new URLSearchParams(window.location.search).get('id');
				
				const data = {
					tablename: 'users',
					recodenum: id
				}
				
				const response = await instance.post(`/users/${id}/likes`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'USER_LIKE_SUCCESS') {
					fetchUser();
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
	});
});








