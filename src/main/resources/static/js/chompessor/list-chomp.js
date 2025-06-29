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
			
			fetchChompList(page);
		});
	});

	// 초기 실행
	fetchChompList(page);
});



/**
 * 쩝쩝박사 목록 데이터를 가져오는 함수
 */
function fetchChompList(num) {
	
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
	.catch(error => console.error(error));
}



/**
 * 카테고리에 일치하는 목록을 렌더링 하는 함수
 */
function renderChompList() {
	const html = chompList.map(data => getChompHTML(data)).join("");
	document.getElementById("chomp").innerHTML = html;
}



/**
 * 개별 데이터를 렌더링하는 함수
 */
function getChompHTML(data) {
	const today = new Date();

	if (data.category === 'vote') {
		const opendate = new Date(data.vote_dto.opendate);
		const closedate = new Date(data.vote_dto.closedate);
		const status = (today >= opendate && today <= closedate)
			? '<p class="ing_flag">투표중</p>'
			: '<p class="end_flag">투표 종료</p>';
		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewVote.do?id=${data.vote_dto.id}">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">투표</p>
						<h5>${data.vote_dto.title}</h5>
						<div class="info">
							${status}
							<p class="date">${formatOpendate} - ${formatClosedate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}

	if (data.category === 'megazine') {
		const postdate = new Date(data.megazine_dto.postdate);
		const formatDate = `${postdate.getFullYear()}년 ${postdate.getMonth() + 1}월 ${postdate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewMegazine.do?id=${data.megazine_dto.id}">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">매거진</p>
						<h5>${data.megazine_dto.title}</h5>
						<div class="info">
							<p class="date">${formatDate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}

	if (data.category === 'event') {
		const opendate = new Date(data.event_dto.opendate);
		const closedate = new Date(data.event_dto.closedate);
		const status = (today >= opendate && today <= closedate)
			? '<p class="ing_flag">행사 진행중</p>'
			: '<p class="end_flag">행사 종료</p>';
		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewEvent.do?id=${data.event_dto.id}">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">이벤트</p>
						<h5>${data.event_dto.title}</h5>
						<div class="info">
							${status}
							<p class="date">${formatOpendate} - ${formatClosedate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}
}




function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML= '';
	
	// 이전 버튼
	pagination.innerHTML += `
		<li>
			<a href="#" class="prev" data-page="${page - 1}" ${page === 0 ? 'style="pointer-events:none;opacity:0;"' : ''}>
				<img src="/images/common/chevron_left.png">
			</a>
		</li>
	`;

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		pagination.innerHTML += `
			<li>
				<a href="#" class="page ${i === page ? 'active' : ''}" data-page="${i}">${i + 1}</a>
			</li>
		`;
	}

	// 다음 버튼
	pagination.innerHTML += `
		<li>
			<a href="#" class="next" data-page="${page + 1}" ${page === totalPages - 1 ? 'style="pointer-events:none;opacity:0;"' : ''}>
				<img src="/images/common/chevron_right.png">
			</a>
		</li>
	`;

	// 바인딩
	document.querySelectorAll(".pagination a").forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.getAttribute("data-page"));
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				fetchChompList(newPage);
			}
		});
	});
}
