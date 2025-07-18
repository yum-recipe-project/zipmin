/**
 * 드롭다운 메뉴 작동 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('recipeSortBtn').addEventListener('click', function () {
	    document.querySelector('.dropdown').classList.toggle('active');
	});

	document.querySelectorAll('.dropdown_menu li').forEach(item => {
	    item.addEventListener('click', function () {
	        document.querySelector('.dropdown').classList.remove('active');
	    });
	});


});



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
			link.textContent = `'${result}' 레시피 보러가기`;
			link.onclick = () => location.href = `/recipes?search=${encodeURIComponent(result)}`;
			link.style.display = 'block';
		}
	}

	roulette();
});
