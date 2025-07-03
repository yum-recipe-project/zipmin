/**
 * 전역 변수 선언
 */
let category = 'all';
let totalPages = 0;
let page = 0; // 현재 페이지 번호
const size = 10; // 한번에 가져올 데이터 개수
let chompList = [];



/**
 * 카테고리 클릭 시 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.querySelectorAll(".btn_tab").forEach(tab => {
		tab.addEventListener("click", function (event) {
			event.preventDefault();
			document.querySelector(".btn_tab.active")?.classList.remove("active");
			this.classList.add("active");
			
			category = this.getAttribute("data-tab");
			page = 0;
			
			chompList = [];
			
			loadChompList(page);
		});
	});

	// 초기 실행
	loadChompList(page);
});



/**
 * 쩝쩝박사 목록 데이터를 가져오는 함수
 */
function loadChompList(num) {
	
	const parameters = new URLSearchParams({
		category : category,
		page : num,
		size : size
	}).toString();
	
	// 
	fetch(`/chomp?${parameters}`, {
		method: 'GET'
	})
	.then(response => response.json())
	.then(result => {
		const data = result.data;
		if (!data) return;
		
		page = data.number;
		totalPages = data.totalPages;
		chompList = data.content;
		
		renderChompList();
		renderPagination();
	})
	.catch(error => console.log(error));
}



/**
 * 카테고리에 일치하는 목록을 렌더링 하는 함수
 */
function renderChompList() {
	const container = document.getElementById("chomp");
	container.innerHTML = '';

	chompList.forEach(data => {
		const element = createChomp(data);
		container.appendChild(element);
	});
}



/**
 * 개별 데이터를 렌더링하는 함수
 */
function createChomp(data) {
	const today = new Date();
	let dto, typeLabel, href, title, dateRange, status;

	if (data.category === 'vote') {
		dto = data.vote_dto;
		typeLabel = '투표';
		href = `/chompessor/viewVote.do?id=${dto.id}`;

		const opendate = new Date(dto.opendate);
		const closedate = new Date(dto.closedate);
		title = dto.title;

		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;
		dateRange = `${formatOpendate} - ${formatClosedate}`;
		status = (today >= opendate && today <= closedate) ? '투표중' : '투표 종료';
	}
	else if (data.category === 'megazine') {
		dto = data.megazine_dto;
		typeLabel = '매거진';
		href = `/chompessor/viewMegazine.do?id=${dto.id}`;

		const postdate = new Date(dto.postdate);
		title = dto.title;
		dateRange = `${postdate.getFullYear()}년 ${postdate.getMonth() + 1}월 ${postdate.getDate()}일`;
	}
	else if (data.category === 'event') {
		dto = data.event_dto;
		typeLabel = '이벤트';
		href = `/chompessor/viewEvent.do?id=${dto.id}`;

		const opendate = new Date(dto.opendate);
		const closedate = new Date(dto.closedate);
		title = dto.title;

		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;
		dateRange = `${formatOpendate} - ${formatClosedate}`;
		status = (today >= opendate && today <= closedate) ? '행사 진행중' : '행사 종료';
	}

	// li
	const li = document.createElement('li');
	li.className = 'forum';

	// a
	const a = document.createElement('a');
	a.href = href;

	// thumbnail
	const thumbnailDiv = document.createElement('div');
	thumbnailDiv.className = 'forum_thumbnail';
	const img = document.createElement('img');
	img.src = '/images/common/test.png';
	thumbnailDiv.appendChild(img);

	// info
	const infoDiv = document.createElement('div');
	infoDiv.className = 'forum_info';

	const typeP = document.createElement('p');
	typeP.className = 'type';
	typeP.textContent = typeLabel;

	const titleH5 = document.createElement('h5');
	titleH5.textContent = title;

	const infoDetailDiv = document.createElement('div');
	infoDetailDiv.className = 'info';

	if (status) {
		const statusP = document.createElement('p');
		statusP.className = status.includes('중') ? 'ing_flag' : 'end_flag';
		statusP.textContent = status;
		infoDetailDiv.appendChild(statusP);
	}

	const dateP = document.createElement('p');
	dateP.className = 'date';
	dateP.textContent = dateRange;

	infoDetailDiv.appendChild(dateP);
	infoDiv.appendChild(typeP);
	infoDiv.appendChild(titleH5);
	infoDiv.appendChild(infoDetailDiv);

	a.appendChild(thumbnailDiv);
	a.appendChild(infoDiv);
	li.appendChild(a);

	return li;
}



/**
 * 페이지네이션을 생성하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				fetchChompList(newPage);
			}
		});
	});
}

