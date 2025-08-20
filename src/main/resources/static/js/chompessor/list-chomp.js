/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 16;
let keyword = '';
let category = '';
let sort = 'id-desc';
let chompList = [];





/**
 * 쩝쩝박사 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="chompessor"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word').value.trim();
		page = 0;
		fetchChompList();
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			chompList = [];
			
			fetchChompList();
		});
	});

	fetchChompList();
});





/**
 * 쩝쩝박사 목록 데이터를 가져오는 함수
 */
async function fetchChompList() {
	
	try {
		const params = new URLSearchParams({
			category: category,
			sort: sort,
			keyword : keyword,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/chomp?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'CHOMP_READ_LIST_SUCCESS') {
			
			// 전역변수 설정
			page = result.data.number;
			totalPages = result.data.totalPages;
			chompList = result.data.content;
			
			// 렌더링
			renderChompList();
			renderPagination(fetchChompList);
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		else if (result.code === 'CHOMP_READ_LIST_FAIL') {
			alertDanger('쩝쩝박사 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		
	}
	catch (error) {
		console.log(error);
	}
}





/**
 * 카테고리에 일치하는 목록을 렌더링 하는 함수
 */
function renderChompList() {
	const container = document.getElementById('chomp');
	container.innerHTML = '';

	chompList.forEach(chomp => {
		const li = document.createElement('li');
		li.className = 'forum';

		const a = document.createElement('a');
		switch(chomp.category) {
			case 'vote' :
				a.href = `/chompessor/viewVote.do?id=${chomp.id}`;
				break;
			case 'megazine' :
				a.href = `/chompessor/viewMegazine.do?id=${chomp.id}`;
				break;
			case 'event' :
				a.href = `/chompessor/viewEvent.do?id=${chomp.id}`;
				break;
		}

		const thumbnailDiv = document.createElement('div');
		thumbnailDiv.className = 'forum_thumbnail';
		const img = document.createElement('img');
		img.src = chomp.image || '/images/common/test.png';
		thumbnailDiv.appendChild(img);

		const infoDiv = document.createElement('div');
		infoDiv.className = 'forum_info';

		const typeP = document.createElement('p');
		typeP.className = 'type';
		switch(chomp.category) {
			case 'vote' :
				typeP.textContent = '투표';
				break;
			case 'megazine' :
				typeP.textContent = '매거진';
				break;
			case 'event' :
				typeP.textContent = '이벤트';
				break;
		}

		const titleH5 = document.createElement('h5');
		titleH5.textContent = chomp.title;

		const infoDetailDiv = document.createElement('div');
		infoDetailDiv.className = 'info';
		
		const statusP = document.createElement('p');
		switch(chomp.category) {
			case 'vote' :
				statusP.className = chomp.opened ? 'ing_flag' : 'end_flag';
				statusP.textContent = chomp.opened ? '투표 진행중' : '투표 종료';
				infoDetailDiv.appendChild(statusP);
				break;
			case 'event' :
				statusP.className = chomp.opened ? 'ing_flag' : 'end_flag';
				statusP.textContent = chomp.opened ? '행사 진행중' : '행사 종료';
				infoDetailDiv.appendChild(statusP);
				break;
		}

		const dateP = document.createElement('p');
		dateP.className = 'date';
		switch(chomp.category) {
			case 'vote' :
				dateP.textContent = `${formatDate(chomp.opendate)} - ${formatDate(chomp.closedate)}`;
				break;
			case 'megazine' :
				dateP.textContent = `${formatDate(chomp.closedate)}`;
				break;
			case 'event' :
				dateP.textContent = `${formatDate(chomp.opendate)} - ${formatDate(chomp.closedate)}`;
				break;
		}
		infoDetailDiv.appendChild(dateP);
		
		infoDiv.append(typeP, titleH5, infoDetailDiv);
		a.append(thumbnailDiv, infoDiv);
		li.appendChild(a);
		container.appendChild(li);
	});
}

