/**
 * 
 */
let AllDataList = [];
let selectTab = "all";
let selectSort = "all";

document.addEventListener('DOMContentLoaded', function() {
	// 데이터 패치
	fetch('http://localhost:8586/chomp', {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		AllDataList = dataList;
		renderChompDataList();
	})
	.catch(error => console.log(error));
	
	// 카테고리 탭 버튼 클릭 이벤트 처리
	const tabs = document.querySelectorAll(".btn_tab");
	tabs.forEach(tab => {
		tab.addEventListener("click", function(event) {
			event.preventDefault();
			document.querySelector(".btn_tab.active")?.classList.remove("active");
			this.classList.add("active");
			selectTab = this.getAttribute("data-tab");
			renderChompDataList();
		});
	});
	
	// 진행상태 탭 버튼 클릭 이벤트 처리
	const sorts = document.querySelectorAll(".btn_sort");
	sorts.forEach(sort => {
		sort.addEventListener("click", function(event) {
			event.preventDefault();
			document.querySelector(".btn_sort.active")?.classList.remove("active");
			this.classList.add("active");
			selectSort = this.getAttribute("data-sort");
			renderChompDataList();
		});
	});
});



/**
 * 카테고리에 일치하는 목록을 출력하는 함수
 */
function renderChompDataList() {
	const categoryMap = {
		vote: "투표",
		megazine: "매거진",
		event: "이벤트",
		all: "전체"
	};
	let selectDataList = selectTab === "all"
		? AllDataList
		: AllDataList.filter(chomp => chomp.category === categoryMap[selectTab]);

	selectDataList = selectSort === "all"
		? selectDataList
		: selectDataList.filter(chomp => {
				if (chomp.category === "투표") {
					return chomp.chomp_vote_dto.status === selectSort;
				}
				else if (chomp.category === "이벤트") {
					return chomp.chomp_event_dto.status === selectSort;
				}
				return true;
			}
		);
	
	document.getElementById("chomp").innerHTML = selectDataList.map(data => {
		if (data.category === '투표') {
			const today = new Date();
			const opendate = new Date(data.chomp_vote_dto.opendate);
			const formatOpendate = `${opendate.getFullYear()}년 ${String(opendate.getMonth()+1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일`;
			const closedate = new Date(data.chomp_vote_dto.closedate);
			const formatClosedate = `${closedate.getFullYear()}년 ${String(closedate.getMonth()+1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;	
						
			let status = '';
			if (today >= opendate && today <= closedate) {
				status = '<p class="ing_flag">투표중</p>';
			}
			else {
				status = '<p class="end_flag">투표 종료</p>'
			}
			
			return `
				<li class="forum">
					<a href="/chompessor/viewVote.do">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">투표</p>
							<h5>${ data.title }</h5>
							<div class="info">
								${ status }
								<p class="date">${ formatOpendate } - ${ formatClosedate }</p>
							</div>
						</div>
					</a>
				</li>`;
		}
		else if (data.category === '매거진') {
			const date = new Date(data.chomp_megazine_dto.postdate);
			const formatDate = `${date.getFullYear()}년 ${String(date.getMonth()+1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
			return `
				<li class="forum">
					<a href="/chompessor/viewMegazine.do?megazineId=${ data.chomp_megazine_dto.id }">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">매거진</p>
							<h5>${ data.title }</h5>
							<div class="info">
								<p class="date">${ formatDate }</p>
							</div>
						</div>
					</a>
				</li>`;
		}
		else if (data.category === '이벤트') {
			console.log(data);
			const today = new Date();
			const opendate = new Date(data.chomp_event_dto.opendate);
			const formatOpendate = `${opendate.getFullYear()}년 ${String(opendate.getMonth()+1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일`;
			const closedate = new Date(data.chomp_event_dto.closedate);
			const formatClosedate = `${closedate.getFullYear()}년 ${String(closedate.getMonth()+1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;
			
			let status = '';
			if (today >= opendate && today <= closedate) {
				status = '<p class="ing_flag">행사 진행중</p>';
			}
			else {
				status = '<p class="end_flag">행사 종료</p>'
			}
			
			return `
				<li class="forum">
					<a href="/chompessor/viewEvent.do">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">이벤트</p>
							<h5>${ data.title }</h5>
							<div class="info">
								${ status }
								<p class="date">${ formatOpendate } - ${ formatClosedate }</p>
							</div>
						</div>
					</a>
				</li>`;
		}
	}).join("");
}