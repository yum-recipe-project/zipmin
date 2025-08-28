/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 20;
let keyword = '';
let categoryList = [];
let sort = 'postdate-desc';
let recipeList = [];





/**
 * 레시피 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="recipe"]');
	searchForm.addEventListener('submit', function (event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word')?.value.trim();
		page = 0;
		fetchRecipeList();
	});
	
	// 정렬
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.stopPropagation();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			recipeList = [];
			
			fetchRecipeList();
		});
	});
	
	// 카테고리
	const categoryMenu = document.querySelector('.category_menu');
	const categoryTitle = document.querySelector('.category_title .category_type');
	const categoryContent = document.querySelector('.category_content');
	const categoryTab = document.querySelector('.category_title .tab');
	const categoryContentList = categoryMenu
		? Array.from(categoryMenu.querySelectorAll('.category_content_list')) : [];

	// 카테고리 내용 토글
	categoryTitle.addEventListener('click', function (event) {
		if (event.target.closest('.tab')) return;
		categoryContent.classList.toggle('is-open');
	});
	
	// 카테고리 그룹별 선택값 초기화
	const state = {};
	categoryContentList.forEach(list => (state[list.dataset.group] = null));

	// 카테고리 파라미터 제거
	function removeCategorySearchParams() {
		const url = new URL(location.href);
		if (url.searchParams.has('category')) {
			url.searchParams.delete('category');
			history.replaceState(null, '', url);
		}
	}
	
	// 카테고리 선택 목록 초기화 (전체 탭 표시)
	function showInitPill(isShow) {
		const allTab = categoryTab?.querySelector('.btn_tab');
		allTab.style.display = isShow ? '' : 'none';
		allTab.classList.toggle('active', isShow);
	}
	
	// 카테고리 선택 목록 생성 및 교체
	function showCategoryPill(groupKey, label) {
		showInitPill(false);
		let pill = categoryTab.querySelector(`.btn_tab_close[data-key="${groupKey}"]`);
		// 생성
		if (!pill) {
			pill = document.createElement('a');
			pill.href = '';
			pill.className = 'btn_tab_close active';
			pill.dataset.key = groupKey;
			pill.innerHTML = `<span>${label}</span><img src="/images/common/close.png" alt="닫기">`;
			categoryTab.appendChild(pill);
		}
		// 교체
		else {
			pill.classList.add('active');
			pill.querySelector('span').textContent = label;
		}
	}
	
	// 파라미터 카테고리를 한번만 반영하고 즉시 제거하는 함수
	function useCategorySearchParamsOnce() {
		const raw = new URLSearchParams(location.search).get('category');
		if (!raw) return;
		
		const cleaned = raw.replace(/^['"]|['"]$/g, '').trim();
		if (!cleaned) return;
		
		// 내부 헬퍼: 특정 그룹과 라벨 적용
		const apply = (groupKey, label) => {
			const box = categoryContentList.find(b => b.dataset.group === groupKey);
			if (!box) return false;
			const ul = box.querySelector('ul');
			if (!ul) return false;
			
			const li = Array.from(ul.querySelectorAll('li')).find(x => x.textContent.trim() === label);
			if (!li) return false;
			
			ul.querySelectorAll('li.active').forEach(x => x.classList.remove('active'));
			li.classList.add('active');
			
			state[groupKey] = label;
			showCategoryPill(groupKey, label);
			showInitPill(false);
			return true;
		};
		
		// 내부 헬퍼: 라벨만으로 매칭 가능한 그룹 찾기
		const findGroupsByLabel = (label) => {
			const hits = [];
			categoryContentList.forEach(b => {
				const ok = Array.from(b.querySelectorAll('ul li')).some(li => li.textContent.trim() === label);
				if (ok) hits.push(b.dataset.group);
			});
			return hits;
		};
		
		// 파싱
		let groupKey = null;
		let label = cleaned;
		const idx = cleaned.indexOf(':');
		if (idx !== -1) {
			groupKey = cleaned.slice(0, idx).trim();
			label = cleaned.slice(idx + 1).trim();
		}
		
		// 적용 시도
		let applied = false;
		if (groupKey) {
			applied = apply(groupKey, label);
		}
		if (!applied) {
			const groups = findGroupsByLabel(label);
			if (groups.length > 0) {
				applied = apply(groups[0], label);
			}
		}
		
		// 1회만 사용하고 URL에서 제거
		if (applied) {
			removeCategorySearchParams();
		}
	}

	// 지정한 그룹의 선택을 해제하고 선택이 하나도 없으면 전체를 다시 표시
	function clearGroup(groupKey) {
		const box = categoryContentList.find(b => b.dataset.group === groupKey);
		if (box) box.querySelectorAll('ul li.active').forEach(li => li.classList.remove('active'));
		state[groupKey] = null;
		categoryTab?.querySelector(`.btn_tab_close[data-key="${groupKey}"]`)?.remove();
		if (Object.values(state).filter(Boolean).length === 0) showInitPill(true);
	}
	
	// 그룹별 카테고리 단일 선택
	categoryContentList.forEach(box => {
		const groupKey = box.dataset.group;
		const ul = box.querySelector('ul');
		if (!ul) return;
		
		ul.addEventListener('click', function(event) {
			const li = event.target.closest('li');
			if (!li) return;
			
			 const wasActive = li.classList.contains('active');
			 ul.querySelectorAll('li.active').forEach(x => x.classList.remove('active'));
			 
			 if (wasActive) {
				clearGroup(groupKey);
			}
			else {
				li.classList.add('active');
				const label = li.textContent.trim();
				state[groupKey] = label;
				showCategoryPill(groupKey, label);
			}
			
			removeCategorySearchParams();

			page = 0;
			fetchRecipeList();
		});
	});
	
	// 카테고리 선택 목록에서 선택한 카테고리 제거
	if (categoryTab) {
		categoryTab.addEventListener('click', function(event) {
			event.stopPropagation();
			
			const closeImg = event.target.closest('.btn_tab_close img');
			const pill = event.target.closest('.btn_tab_close');
			
			if (closeImg && pill) {
				event.preventDefault();
				const key = pill.dataset.key;
				if (key) {
					clearGroup(key);
					removeCategorySearchParams();
					page = 0;
					fetchRecipeList();
				}
				return;
			}
			
			if (pill) {
				event.preventDefault();
				return;
			}
		});
	}

	// 초기 로딩
	useCategorySearchParamsOnce();
	showInitPill(Object.values(state).filter(Boolean).length === 0);
	fetchRecipeList();
});





/**
 * 레시피 목록 데이터를 가져오는 함수
 */
async function fetchRecipeList() {
	
	try {
		
		// 현재 UI에서 categoryList 계산
		let categoryList = [];
		const categoryTab = document.querySelector('.category_title .tab');
		const categoryMenu = document.querySelector('.category_menu');
		if (categoryTab) {
			const pills = Array.from(categoryTab.querySelectorAll('.btn_tab_close span'))
				.map(el => el.textContent.trim())
				.filter(Boolean);
			if (pills.length) categoryList = pills;
		}
		if (categoryList.length === 0 && categoryMenu) {
			const actives = Array.from(categoryMenu.querySelectorAll('.category_content_list li.active'))
				.map(li => li.textContent.trim());
			if (actives.length) categoryList = actives;
		}
		
		const params = new URLSearchParams();
		categoryList.forEach(c => params.append('categoryList', c));
		params.append('sort', sort);
		params.append('keyword', keyword ?? '');
		params.append('page', String(page));
		params.append('size', String(size));
		
		const response = await fetch(`/recipes?${params.toString()}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			recipeList = result.data.content;
			
			// 렌더링
			renderRecipeList(recipeList);
			renderPagination(fetchRecipeList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.recipe_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const content = document.querySelector('.recipe_content');
				content.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('.recipe_list').style.display = '';
			}
			
			// 스크롤 상단 이동
			scrollTo({ top: 0, behavior: 'smooth' });
		}
		
		// ****** TODO: 에러코드 분기 ******
		
	}
	catch (error) {
		console.error(error);
	}
}


  
  
  
/**
 * 쩝쩝박사 목록을 화면에 렌더링하는 함수
 */
function renderRecipeList(recipeList) {
	const container = document.querySelector('.recipe_list');
	container.innerHTML = '';
	
	recipeList.forEach(recipe => {
		// li
		const li = document.createElement('li');
		li.className = 'recipe';
		
		// a
		const a = document.createElement('a');
		a.href = `/recipe/viewRecipe.do?id=${recipe.id}`;
		
		// 썸네일
		const thumb = document.createElement('div');
		thumb.className = 'recipe_thumbnail';
		const img = document.createElement('img');
		img.src = recipe.image;
		img.loading = 'lazy';
		thumb.appendChild(img);
		
		// 정보
		const info = document.createElement('div');
		info.className = 'recipe_info';
		
		const h5 = document.createElement('h5');
		h5.textContent = recipe.title;
		
		// 상세(난이도/시간/매움)
		const details = document.createElement('div');
		details.className = 'recipe_details';
		
		const detailData = [
			{ icon: '/images/recipe/level.png', alt: '난이도', text: recipe.cooklevel },
			{ icon: '/images/recipe/time.png',  alt: '조리시간', text: recipe.cooktime },
			{ icon: '/images/recipe/spicy.png', alt: '매운맛',   text: recipe.spicy }
		];
		for (const d of detailData) {
			const box = document.createElement('div');
			box.className = 'details_item';
			const i = document.createElement('img');
			i.src = d.icon; i.alt = d.alt;
			const p = document.createElement('p');
			p.textContent = d.text;
			box.append(i, p);
			details.appendChild(box);
		}
		// 별점
		const scoreBox = document.createElement('div');
		scoreBox.className = 'recipe_score';
		
		const starWrap = document.createElement('div');
		starWrap.className = 'star';
		
		const full = Math.floor(recipe.reviewscore);
		const empty = 5 - full;
		for (let i = 0; i < full; i++) {
			const s = document.createElement('img');
			s.src = '/images/recipe/star_full.png';
			s.loading = 'lazy';
			starWrap.appendChild(s);
		}
		for (let i = 0; i < empty; i++) {
			const s = document.createElement('img');
			s.src = '/images/recipe/star_empty.png';
			s.loading = 'lazy';
			starWrap.appendChild(s);
		}
		
		const scoreText = document.createElement('p');
		scoreText.textContent = `${recipe.reviewscore} (${recipe.reviewcount})`;
		
		scoreBox.append(starWrap, scoreText);
		info.append(h5, details, scoreBox);
		a.append(thumb, info);
		li.appendChild(a);
		container.appendChild(li);
	});
}