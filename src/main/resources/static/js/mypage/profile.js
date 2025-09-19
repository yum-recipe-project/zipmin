/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 10;
let recipeList = [];
let sort = 'postdate-desc';






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
        });
    });

    // 선택된 탭 활성화
    tabItems.forEach(button => button.classList.remove('active'));
    contentItems.forEach(content => content.style.display = 'none');

    tabItems[0].classList.add('active');
    contentItems[0].style.display = 'flex';
});



/**
 * 탭 메뉴 클릭시 탭 메뉴를 활성화하고 해당 내용을 표시하는 함수
 */
/*
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="cooking"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word').value.trim();
		page = 0;
		fetchClassList();
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			classList = [];
			
			fetchClassList();
		});
	});
	
	// 상태 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			status = btn.dataset.status;
			page = 0;
			classList = [];
			
			fetchClassList();
		});
	});

	fetchClassList();
});
*/




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
		
		const response = await fetch(`/users/${id}/recipe`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
	}
	catch (error) {
		
	}
}






























