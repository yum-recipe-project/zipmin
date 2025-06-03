/**
 * 전역 변수 선언
 * allChompList:
 * selectTab: 선택된 탭
 * selectSort: 선택된 정렬 기준
 * page: 현재 페이지 번호
 * size: 한번에 가져올 데이터 개수
 */
let allChompList = [];
let selectTab = "all";
let selectSort = "all";
let page = 0;
const size = 10;



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function () {
	// 데이터 불러오기
	fetch('http://localhost:8586/chomp', {
		method: "GET",
		headers: {
			"Authorization": "Bearer " + localStorage.getItem("accessToken")
		}
	})
		.then(response => response.json())
		.then(dataList => {
			allChompList = dataList;
			page = 0;
			renderChompDataList();
		})
		.catch(error => console.error(error));

	// 카테고리 탭 클릭
	document.querySelectorAll(".btn_tab").forEach(tab => {
		tab.addEventListener("click", function (e) {
			e.preventDefault();
			document.querySelector(".btn_tab.active")?.classList.remove("active");
			this.classList.add("active");
			selectTab = this.getAttribute("data-tab");
			page = 0;
			renderChompDataList();
		});
	});

	// 정렬 기준 필터 클릭
	document.querySelectorAll(".btn_sort").forEach(sort => {
		sort.addEventListener("click", function (e) {
			e.preventDefault();
			document.querySelector(".btn_sort.active")?.classList.remove("active");
			this.classList.add("active");
			selectSort = this.getAttribute("data-sort");
			page = 0;
			renderChompDataList();
		});
	});

	// 더보기 버튼
	document.querySelector(".btn_more").addEventListener("click", function () {
		page++;
		renderChompDataList();
	});
});



/**
 * 카테고리에 일치하는 목록을 렌더링 하는 함수
 */
function renderChompDataList() {
	const categoryMap = {
		vote: "투표",
		megazine: "매거진",
		event: "이벤트",
		all: "전체"
	};

	// 필터 적용
	let filteredList = selectTab === "all"
		? allChompList
		: allChompList.filter(chomp => chomp.category === categoryMap[selectTab]);

	filteredList = selectSort === "all"
		? filteredList
		: filteredList.filter(chomp => {
			if (chomp.category === "투표") {
				return chomp.chomp_vote_dto?.status === selectSort;
			}
			if (chomp.category === "이벤트") {
				return chomp.chomp_event_dto?.status === selectSort;
			}
			return true;
		});

	// 페이징 처리
	const start = 0;
	const end = (page + 1) * size;
	const currentList = filteredList.slice(start, end);

	// 렌더링
	const html = currentList.map(data => renderChompData(data)).join("");
	document.getElementById("chomp").innerHTML = html;

	// 더보기 버튼 제어
	const btnMore = document.querySelector(".btn_more");
	if (end >= filteredList.length) {
		btnMore.style.display = "none";
	} else {
		btnMore.style.display = "block";
	}
}



/**
 * 개별 데이터를 렌더링하는 함수
 */
function renderChompData(data) {
	const today = new Date();

	if (data.category === '투표') {
		const opendate = new Date(data.chomp_vote_dto.opendate);
		const closedate = new Date(data.chomp_vote_dto.closedate);
		const status = (today >= opendate && today <= closedate)
			? '<p class="ing_flag">투표중</p>'
			: '<p class="end_flag">투표 종료</p>';
		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewVote.do">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">투표</p>
						<h5>${data.title}</h5>
						<div class="info">
							${status}
							<p class="date">${formatOpendate} - ${formatClosedate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}

	if (data.category === '매거진') {
		const postdate = new Date(data.chomp_megazine_dto.postdate);
		const formatDate = `${postdate.getFullYear()}년 ${postdate.getMonth() + 1}월 ${postdate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewMegazine.do?megazineId=${data.chomp_megazine_dto.id}">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">매거진</p>
						<h5>${data.title}</h5>
						<div class="info">
							<p class="date">${formatDate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}

	if (data.category === '이벤트') {
		const opendate = new Date(data.chomp_event_dto.opendate);
		const closedate = new Date(data.chomp_event_dto.closedate);
		const status = (today >= opendate && today <= closedate)
			? '<p class="ing_flag">행사 진행중</p>'
			: '<p class="end_flag">행사 종료</p>';
		const formatOpendate = `${opendate.getFullYear()}년 ${opendate.getMonth() + 1}월 ${opendate.getDate()}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${closedate.getMonth() + 1}월 ${closedate.getDate()}일`;

		return `
			<li class="forum">
				<a href="/chompessor/viewEvent.do">
					<div class="forum_thumbnail"><img src="/images/common/test.png"></div>
					<div class="forum_info">
						<p class="type">이벤트</p>
						<h5>${data.title}</h5>
						<div class="info">
							${status}
							<p class="date">${formatOpendate} - ${formatClosedate}</p>
						</div>
					</div>
				</a>
			</li>`;
	}
}
