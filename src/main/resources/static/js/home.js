/**
 * 전역 변수
 */
let recipeList = [];
let guideList = [];





/**
 * 룰렛을 돌리는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 랜덤한 음식 배열
	const foodList = [
	  '된장찌개', '김치찌개', '부대찌개', '청국장',
	  '김치볶음밥', '참치김밥', '제육볶음', '고등어조림',
	  '닭갈비', '닭볶음탕', '간장계란밥', '불고기',
	  '잡채', '비빔밥', '소고기미역국', '떡국',
	  '콩나물국밥', '순두부찌개', '감자조림', '계란장조림',
	  '감자탕', '삼겹살구이', '낙지볶음', '오징어볶음',
	  '갈치조림', '두부조림', '멸치볶음', '소시지야채볶음',
	  '라면', '잔치국수'
	];
	const randomList = [...foodList];
	for (let i = randomList.length - 1; i > 0; i--) {
	  const j = Math.floor(Math.random() * (i + 1));
	  [randomList[i], randomList[j]] = [randomList[j], randomList[i]];
	}
	
	// 현재 시간 표시
	function getMeal() {
		const now = new Date();
		const hour = now.getHours();
		
		if (hour >= 5 && hour < 11) return '아침';
		if (hour >= 11 && hour < 16) return '점심';
		if (hour >= 16 && hour < 21) return '저녁';
		return '야식';
	}
	
	document.querySelector('.meal').textContent = getMeal();
	
	// 룰렛 돌리기
	let index = 0;
	let delay = 10;
	const maxDelay = 600;
	const delayFactor = 1.15;
	
	const display = document.querySelector('.roulette');
	const link = document.querySelector('.roulette_link');
	
	function roulette() {
		display.textContent = randomList[index];
		index = (index + 1) % randomList.length;

		if (delay < maxDelay) {
			delay *= delayFactor;
			setTimeout(roulette, delay);
		} else {
			const result = randomList[(index - 1 + randomList.length) % randomList.length];
			link.innerHTML = `${result} 레시피 보러가기&nbsp;&nbsp;→`;
			link.href = `/recipe/listRecipe.do?keyword=${encodeURIComponent(result)}`;
			link.classList.add('show');
		}
	}

	roulette();
});





/**
 * 카테고리로 레시피를 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.category_list').forEach(ul => {
		ul.addEventListener('click', (event) => {
			const li = event.target.closest('li');
			if (!li) return;
			
			const category = (li.querySelector('p')?.textContent).trim().replace(/^['"]|['"]$/g, '');
			if (!category) return;
			
			location.href = '/recipe/listRecipe.do?category=' + category;
		});
	});
});







/**
 * 레시피 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	


	fetchRecipeList();
	fetchGuideList();
});





/**
 * 서버에서 레시피를 가져오는 함수
 */
async function fetchRecipeList() {
	try {
		const params = new URLSearchParams({
			sort: 'likecount-desc',
			page: 0,
			size: 5
		}).toString();

		const response = await fetch(`/recipes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();

		if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
			// 전역변수 설정
			recipeList = result.data.content;			
						
			// 렌더링
			renderRecipeList(recipeList);
		}
	} catch (error) {
		console.error(error);
	}
}




/**
 * 서버에서 키친가이드를 가져오는 함수
 */
async function fetchGuideList() {
	try {
		const params = new URLSearchParams({
			sort: 'likecount-desc',
			page: 0,
			size: 6
		}).toString();

		const response = await fetch(`/guides?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);

		if (result.code === 'KITCHEN_READ_LIST_SUCCESS') {
			// 전역변수 설정
			guideList = result.data.content;			
			
			// 렌더링
			renderGuideList(guideList);
		}
	} catch (error) {
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
		const detail = document.createElement('div');
		detail.className = 'recipe_detail';
		
		const detailData = [
			{ icon: '/images/recipe/level.png', alt: '난이도', text: recipe.cooklevel },
			{ icon: '/images/recipe/time.png',  alt: '조리시간', text: recipe.cooktime.split(' ')[0] },
			{ icon: '/images/recipe/spicy.png', alt: '매운맛',   text: recipe.spicy }
		];
		for (const d of detailData) {
			const box = document.createElement('div');
			box.className = 'detail_item';
			const i = document.createElement('img');
			i.src = d.icon; i.alt = d.alt;
			const p = document.createElement('p');
			p.textContent = d.text;
			box.append(i, p);
			detail.appendChild(box);
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
		info.append(h5, detail, scoreBox);
		a.append(thumb, info);
		li.appendChild(a);
		container.appendChild(li);
	});
}





/**
 * 키친가이드 목록을 화면에 렌더링하는 함수
 */
function renderGuideList(guideList) {
	const container = document.querySelector('.guide_list');
	container.innerHTML = '';
	
	guideList.forEach((guide, index) => {
		const li = document.createElement('li');
		li.addEventListener('click', function() {
			location.href = `/kitchen/viewGuide.do?id=${guide.id}`;
		});
		
		const guideDiv = document.createElement('div');
		guideDiv.className = 'guide';
		
		// 번호
		const span = document.createElement('span');
		span.textContent = index + 1;
		
		// 제목		
		const p = document.createElement('p');
		p.textContent = guide.title;
		
		guideDiv.append(span, p);
		li.appendChild(guideDiv);
		container.appendChild(li);
	});
}