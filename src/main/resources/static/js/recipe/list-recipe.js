/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 20;
let keyword = '';
let categoryList = [];
let sortKey = 'id';
let sortOrder = 'desc';
let recipeList = [];





/**
 * 레시피 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="recipe"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word').value.trim();
		page = 0;
		fetchRecipeList();
	});
	
	// ***** 카테고리 *****
	
	
	
	
	
	// 정렬 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.stopPropagation();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			recipeList = [];
			
			fetchRecipeList();
		});
	});
	
	fetchRecipeList();




/**
 * 카테고리 동작
 */
	const categoryMenu = document.querySelector('.category_menu');
	if (!categoryMenu) return;
	
	const categoryTitle   = categoryMenu.querySelector('.category_title');
	const categoryContent = categoryMenu.querySelector('.category_content');
	const categoryTab = categoryTitle.querySelector('.tab');
	
	// 그룹 키 부여
	const categoryContentList = Array.from(categoryMenu.querySelectorAll('.category_content_list'));
	categoryContentList.forEach((box, i) => {
		const key = (box.querySelector('.category_type p')?.textContent || `group-${i}`).trim();
		box.dataset.group = key;
	});
	
	// 그룹별 선택 라벨
	const state = {};
	categoryContentList.forEach(b => (state[b.dataset.group] = null));
	
	// 유틸
	const selectedCount = () => Object.values(state).filter(Boolean).length;
	const allTab = categoryTab.querySelector('.btn_tab');   // "전체" 탭(a.btn_tab)
	const showAll = (show) => {
		if (!allTab) return;
		allTab.style.display = show ? '' : 'none';
		allTab.classList.toggle('active', show);
	};
	
	// pill 추가 및 교체
	function upsertPill(groupKey, label) {
		showAll(false);
		let pill = categoryTab.querySelector(`.btn_tab_close[data-key="${groupKey}"]`);
		if (!pill) {
			pill = document.createElement('a');
			pill.href = '';
			pill.className = 'btn_tab_close active';
			pill.dataset.key = groupKey;
			pill.innerHTML = `<span>${label}</span><img src="/images/common/close.png" alt="닫기">`;
			categoryTab.appendChild(pill);
		}
		else {
			pill.classList.add('active');
			const span = pill.querySelector('span');
			if (span) span.textContent = label;
		}
	}
	
	// 그룹 해제 & pill 제거
	function clearGroup(groupKey) {
		const box = categoryContentList.find(b => b.dataset.group === groupKey);
		if (box) box.querySelectorAll('ul li.active').forEach(li => li.classList.remove('active'));
		state[groupKey] = null;
		
		const pill = categoryTab.querySelector(`.btn_tab_close[data-key="${groupKey}"]`);
		if (pill) pill.remove();
		
		if (selectedCount() === 0) showAll(true);
	}
	
	// 카테고리 토글
	categoryTitle.addEventListener('click', (e) => {
	  if (e.target.closest('.tab')) return;
	  const open = categoryContent.style.display === 'block';
	  categoryContent.style.display = open ? 'none' : 'block';
	});
	
	// 그룹별 단일 선택
	categoryContentList.forEach(box => {
		const groupKey = box.dataset.group;
		const ul = box.querySelector('ul');
		if (!ul) return;
		
		ul.addEventListener('click', (e) => {
			const li = e.target.closest('li');
			if (!li) return;
			
			const wasActive = li.classList.contains('active');
			ul.querySelectorAll('li.active').forEach(x => x.classList.remove('active'));
			
			if (wasActive) {
				// 해제
				clearGroup(groupKey);
			}
			else {
				// 새 선택
				li.classList.add('active');
				const label = li.textContent.trim();
				state[groupKey] = label;
				upsertPill(groupKey, label);
			}
			
			// 선택 변경 시 첫 페이지부터 로딩
			window.page = 0;
			fetchRecipeList();
		});
	});
	

	// 탭 클릭 처리
	categoryTab.addEventListener('click', (e) => {
		e.stopPropagation();
		
		const closeImg = e.target.closest('.btn_tab_close img');
		const pill     = e.target.closest('.btn_tab_close');
		const all      = e.target.closest('.btn_tab');
		
		// X 클릭 → 해당 그룹 해제
		if (closeImg && pill) {
			e.preventDefault();
			const key = pill.dataset.key;
			if (key) {
				clearGroup(key);
				page = 0;
				fetchRecipeList();
			}
			return;
		}
		
		// pill 자체 클릭은 네비게이션 막기
		if (pill) {
			e.preventDefault();
			return;
		}
		
		// "전체" 클릭 → 모든 선택 초기화 후 목록 로딩
		if (all) {
			e.preventDefault();
			lists.forEach(b => clearGroup(b.dataset.group));
			showAll(true);
			page = 0;
			fetchRecipeList();
			return;
		}
	});
	
	showAll(selectedCount() === 0);

	// 최초 로딩
	fetchRecipeList();

	
	
	
	
	
	// 현재 UI로부터 categoryList 계산
		function getCategoryList() {
			
			// 상단 pill에서 읽기
			const pills = Array.from(categoryTab.querySelectorAll('.btn_tab_close span')).map(el => el.textContent.trim()).filter(Boolean);
			if (pills.length) return pills;
			
			// 없으면 좌측 목록의 active에서 읽기
			const actives = Array.from(categoryMenu.querySelectorAll('.category_content_list li.active')).map(li => li.textContent.trim());
			if (actives.length) return actives;
			
			// 3) 초기 진입: 쿼리스트링(category) 1개만
			const q = new URLSearchParams(location.search).get('category');
			return q ? [q.replace(/^['"]|['"]$/g, '')] : [];
		}
	
	
	/**
	 * 서버에서 레시피 목록 데이터를 가져오는 함수
	 */
	async function fetchRecipeList() {
		
		try {
			categoryList = getCategoryList();
			
			const params = new URLSearchParams();
			categoryList.forEach(category => params.append('categoryList', category));
			params.append('sort', `${sortKey}-${sortOrder}`);
			params.append('keyword', keyword);
			params.append('page', page);
			params.append('size', size);
			
			const response = await fetch(`/recipes?${params.toString()}`, {
				method: 'GET',
				headers: getAuthHeaders()
			});
			
			const result = await response.json();
			
			console.log(result);
			
			if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
				
				// 전역 변수 설정
				totalPages = result.data.totalPages;
				totalElements = result.data.totalElements;
				page = result.data.number;
				recipeList = result.data.content;
				
				// 렌더링
				renderRecipeList(recipeList);
				// renderPagination(fetchRecipeList);
				document.querySelector('.total').innerText = `총 ${totalElements}개`;
				
				// 검색 결과 없음 표시
				if (result.data.totalPages === 0) {
					document.querySelector('.recipe_list').style.display = 'none';
					document.querySelector('.search_empty')?.remove();
					const table = document.querySelector('.recipe_content');
					table.insertAdjacentElement('afterend', renderSearchEmpty());
				}
				// 검색 결과 표시
				else {
					document.querySelector('.search_empty')?.remove();
					document.querySelector('.recipe_list').style.display = '';
				}
	
				// 스크롤 최상단 이동
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
			
			/***** 에러코드 추가 *****/
			
		}
		catch (error) {
			console.log(error);
		}
		
	}
});















/**
 * 쩝쩝박사 목록을 화면에 렌더링하는 함수
 */
function renderRecipeList(recipeList) {
	
}


















