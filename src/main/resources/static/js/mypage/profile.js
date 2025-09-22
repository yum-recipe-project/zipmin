/**
 * 전역변수
 */
const size = 20;
let totalPages = 0;
let totalElements = 0;
let page = 0;
let sort = 'postdate-desc';
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
			
			sort = 'postdate-desc';
			page = 0;
			recipeList = [];
			classList = [];

			recipeWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			recipeWrap.querySelectorAll('.btn_sort')[0].classList.add('active');
			classWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			classWrap.querySelectorAll('.btn_sort')[0].classList.add('active');
			
			fetchRecipeList();
			fetchClassList();
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
			
			fetchRecipeList(recipeList);
		});
	});
	
	// 클래스 정렬 버튼
	classWrap.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			classWrap.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			classList = [];
			
			fetchClassList(classList);
		});
	});
	
	fetchRecipeList(recipeList);
	fetchClassList(classList);
});





/**
 * 사용자 프로필 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
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
			userWrap.querySelector('.recipecount').innerText = `${result.data.recipecount}개`;
			if (result.data.link) {
				const p = document.createElement('p');
				p.className = 'user_link';
				p.textContent = result.data.link;
				userWrap.appendChild(p);
			}
		}
		
	}
	catch(error) {
		console.log(error);
		
		// *** TODO : 에러코드 작성하기 ***
	}
	
});





/**
 * 레시피 목록 데이터를 가져오는 함수
 */
async function fetchRecipeList() {
	
	const recipeWrap = document.getElementById('recipeWrap');
	
	// 레시피 목록 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const params = new URLSearchParams({
			sort: sort,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/users/${id}/recipes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'USER_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			recipeList = result.data.content;
			
			// 렌더링
			recipeWrap.querySelector('.recipe_header').innerText = `레시피 ${totalElements}개`;
			renderRecipeList(recipeList);
			renderRecipePagination();
			
			// 결과 없음 표시
			if (totalPages === 0) {
				recipeWrap.querySelector('.recipe_list').style.display = 'none';
				recipeWrap.querySelector('.list_empty')?.remove();
				recipeWrap.querySelector('.recipe_list').insertAdjacentElement('afterend', renderRecipeListEmpty());
			}
			else {
				recipeWrap.querySelector('.list_empty')?.remove();
				recipeWrap.querySelector('.recipe_list').style.display = '';
			}
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		
		
		// *** TODO : 에러 코드 추가하기 ***
	}
	catch (error) {
		alertDanger('알 수 없는 오류가 발생했습니다.');
		console.log(error);
	}
}




/**
 * 클래스 목록 데이터를 가져오는 함수
 */
async function fetchClassList() {
	
	const classWrap = document.getElementById('classWrap');
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
				
		const params = new URLSearchParams({
			sort: sort,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/users/${id}/classes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'CLASS_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			classList = result.data.content;
			
			// 렌더링
			classWrap.querySelector('.class_header').innerText = `클래스 ${totalElements}개`;
			renderClassList(classList);
			renderClassPagination();
			
			// 결과 없음 표시
			if (totalPages === 0) {
				classWrap.querySelector('.class_list').style.display = 'none';
				classWrap.querySelector('.list_empty')?.remove();
				classWrap.querySelector('.class_list').insertAdjacentElement('afterend', renderClassListEmpty());
			}
			else {
				classWrap.querySelector('.list_empty')?.remove();
				classWrap.querySelector('.class_list').style.display = '';
			}
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		
		
		// *** TODO : 에러 코드 추가하기 ***
		
		console.log(result);
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 레시피 목록을 화면에 렌더링하는 함수
 */
function renderRecipeList(recipeList) {
	const recipeWrap = document.getElementById('recipeWrap');
	const container = recipeWrap.querySelector('.recipe_list');
	container.innerHTML = '';
	
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
function renderClassList(classList) {
	const classWrap = document.getElementById('classWrap');
	const container = classWrap.querySelector('.class_list');
	container.innerHTML = '';
	
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
 * 레시피 목록 결과 없음을 화면에 렌더링하는 함수
 */
function renderRecipeListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'list_empty';
	
    const span = document.createElement('span');
    span.textContent = '작성된 레시피가 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}





/**
 * 클래스 목록 결과 없음을 화면에 렌더링하는 함수
 */
function renderClassListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'list_empty';
	
    const span = document.createElement('span');
    span.textContent = '개설된 쿠킹클래스가 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}





/**
 * 레시피 페이지네이션을 생성하는 함수
 */
function renderRecipePagination() {
	const recipeWrap = document.getElementById('recipeWrap');
	const pagination = recipeWrap.querySelector('.pagination ul');
	pagination.innerHTML = '';
	
	if (totalPages === 0) {
		return;
	}
	
	let startPage = Math.floor(page / 5) * 5;
	let endPage = Math.min(startPage + 5 - 1, totalPages - 1);
	
	// 이전 버튼 (5개씩 이동)
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = Math.max(0, startPage - 5);
	
	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	
	if (startPage === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0.3';
	}
	
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = startPage; i <= endPage; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = 'page' + (i === page ? ' active' : '');
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼 (5개씩 이동)
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = Math.min(totalPages - 1, startPage + 5);

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	
	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0.3';
	}

	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 데이터 패치
	pagination.querySelectorAll('a').forEach(link => {
		link.addEventListener('click', function (event) {
			event.preventDefault();
			const newPage = Number(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages) {
				page = newPage;
				fetchRecipeList();
			}
		});
	});
}





/**
 * 클래스 페이지네이션을 생성하는 함수
 */
function renderClassPagination() {
	const classWrap = document.getElementById('classWrap');
	const pagination = classWrap.querySelector('.pagination ul');
	pagination.innerHTML = '';
	
	if (totalPages === 0) {
		return;
	}
	
	let startPage = Math.floor(page / 5) * 5;
	let endPage = Math.min(startPage + 5 - 1, totalPages - 1);
	
	// 이전 버튼 (5개씩 이동)
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = Math.max(0, startPage - 5);
	
	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	
	if (startPage === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0.3';
	}
	
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = startPage; i <= endPage; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = 'page' + (i === page ? ' active' : '');
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼 (5개씩 이동)
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = Math.min(totalPages - 1, startPage + 5);

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	
	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0.3';
	}

	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 데이터 패치
	pagination.querySelectorAll('a').forEach(link => {
		link.addEventListener('click', function (event) {
			event.preventDefault();
			const newPage = Number(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages) {
				page = newPage;
				fetchClassList();
			}
		});
	});
}






document.addEventListener('DOMContentLoaded', function() {
	
	const userWrap = document.getElementById('userWrap');
	const button = userWrap.querySelector('.user_title button');
	
	button.addEventListener('click', async function(event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 좋아요 취소
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
					button.dataset.isLiked = 'false';
					button.className = 'btn btn_dark';
					button.innerText = '팔로우';
				}
			}
			catch(error) {
				const code = error?.response?.data?.code;
				
				// *** TODO : 에러코드 추가 ***
				console.log(error);
			}
		}
		
		// 좋아요
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
					button.dataset.isLiked = 'true';
					button.className = 'btn btn_outline';
					button.innerText = '팔로우 취소';
				}
			}
			catch(error) {
				const code = error?.response?.data?.code;
								
				// *** TODO : 에러코드 추가 ***
				console.log(error);
			}
		}
		
		
	});
	
});








