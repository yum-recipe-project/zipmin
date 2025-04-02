/**
 * 
 */
let AllDataList = [];

document.addEventListener('DOMContentLoaded', function() {
	// 데이터 패치
	fetch('http://localhost:8586/chomp', {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		AllDataList = dataList;
		renderChompDataList("all");
	})
	.catch(error => console.log(error));
	
	// 탭 버튼 클릭 이벤트 처리
	const tabs = document.querySelectorAll(".btn_tab");
	tabs.forEach(tab => {
		tab.addEventListener("click", function(event) {
			event.preventDefault();
			document.querySelector(".btn_tab.active")?.classList.remove("active");
			this.classList.add("active");
			renderChompDataList(this.getAttribute("data-category"));
		});
	});
});



/**
 * 카테고리에 일치하는 목록을 출력하는 함수
 * 
 * @param {String} category - 선택한 카테고리
 */
function renderChompDataList(category) {
	const categoryMap = {
		vote: "투표",
		megazine: "매거진",
		event: "이벤트",
		all: "전체"
	};
	const selectDataList = category === "all"
		? AllDataList
		: AllDataList.filter(chomp => chomp.category === categoryMap[category]);
	
	document.getElementById("chomp").innerHTML = selectDataList.map(data => {
		if (data.category === '투표') {
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
								<p class="ing_flag">모집중</p>
								<p class="date">2025.03.04 - 2025.04.03</p>
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
								<p class="ing_flag">모집중</p>
								<p class="date">2025.03.04 - 2025.04.03</p>
							</div>
						</div>
					</a>
				</li>`;
		}
	}).join("");
}