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
			link.href = `/recipes?search=${encodeURIComponent(result)}`;
			link.classList.add('show');
		}
	}

	roulette();
});



/**
 * 드롭다운 동작 및 정렬방식 변경하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	let sort = 'hot';

	const dropdown = document.querySelector('.dropdown');
	const sortButton = document.getElementById('recipeSortBtn');
	const sortItems = document.querySelectorAll('.dropdown_menu li');

	sortButton.addEventListener('click', function () {
		dropdown.classList.toggle('active');
	});

	sortItems.forEach((item, index) => {
		item.addEventListener('click', async function () {
			sort = index === 0 ? 'hot' : 'score';
			dropdown.classList.remove('active');
			sortButton.innerHTML = `${this.textContent} <div class="btn_img"></div>`;

			await fetchRecipeList(sort);
		});
	});

	fetchRecipeList(sort);
});



/**
 * 서버에서 레시피를 가져오는 함수
 */
async function fetchRecipeList(sort) {
	try {
		const params = new URLSearchParams({
			sort: sort,
			page: 0,
			size: 6
		}).toString();

		const response = await fetch(`/recipes?${params}`);
		const result = await response.json();

		if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
			renderRecipeList(result.data.content);
		}
	} catch (error) {
		console.error(error);
	}
}



/**
 * 레시피 목록을 화면에 렌더링 하는 함수
 */
function renderRecipeList(list) {
	const container = document.querySelector('.recipe_list');
	container.innerHTML = '';

	list.forEach(recipe => {
		const li = document.createElement('li');
		
		const a = document.createElement('a');
		a.href = `/recipe/viewRecipe.do?id=${recipe.id}`;

		const card = document.createElement('div');
		card.className = 'recipe_card';
		
		const imageDiv = document.createElement('div');
		imageDiv.className = 'image';
		imageDiv.style.backgroundImage = `url(${recipe.image})`;
		
		const titleP = document.createElement('p');
		titleP.textContent = recipe.title;
		
		const infoSpan = document.createElement('span');
		infoSpan.textContent = `${recipe.cooklevel} / ${recipe.cooktime} / ${recipe.spicy}`;
		
		card.appendChild(imageDiv);
		card.appendChild(titleP);
		card.appendChild(infoSpan);
		
		a.appendChild(card);
		li.appendChild(a);
		container.appendChild(li);
	});
}




