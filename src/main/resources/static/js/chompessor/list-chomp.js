/**
 * 
 */
let AllChompList = [];

document.addEventListener('DOMContentLoaded', function() {
	// 데이터 패치
	fetch('http://localhost:8586/chomp', {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		AllChompList = dataList;
		renderChompList("all");
	});
	
	// 탭 버튼 클릭 이벤트 처리
	const tabs = document.querySelectorAll(".btn_tab");
	tabs.forEach(tab => {
		tab.addEventListener("click", function(event) {
			event.preventDefault();
			document.querySelector(".btn_tab.active")?.classList.remove("active");
			this.classList.add("active");
			renderChompList(this.getAttribute("data-category"));
		});
	});
});



/**
 * 카테고리에 일치하는 목록을 출력하는 함수
 * 
 * @param {String} category - 선택한 카테고리
 */
function renderChompList(category) {
	const chompElement = document.getElementById("chomp");
	const selectChompList = category === "all"
		? AllChompList
		: AllChompList.filter(chomp => chomp.category === category);
	
	chompElement.innerHTML = selectChompList.map(chomp => {
		if (chomp.category === 'vote') {
			return `
				<li class="forum">
					<a href="/chompessor/viewVote.do">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">투표</p>
							<h5>${ chomp.title }</h5>
							<div class="info">
								<p class="ing_flag">모집중</p>
								<p class="date">2025.03.04 - 2025.04.03</p>
							</div>
						</div>
					</a>
				</li>`;
		}
		else if (chomp.category === 'megazine') {
			return `
				<li class="forum">
					<a href="/chompessor/viewMegazine.do">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">매거진</p>
							<h5>${ chomp.title }</h5>
						</div>
					</a>
				</li>`;
		}
		else if (chomp.category === 'event') {
			return `
				<li class="forum">
					<a href="/chompessor/viewEvent.do">
						<div class="forum_thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="forum_info">
							<p class="type">이벤트</p>
							<h5>${ chomp.title }</h5>
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