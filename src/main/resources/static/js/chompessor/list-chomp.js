/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	fetch('http://localhost:8586/chomp', {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		const chompElement = document.getElementById("chomp");
		chompElement.innerHTML = dataList.map((chomp, index) => `
			<li class="forum">
				<a href="/chompessor/viewVote.do">
					<div class="forum_thumbnail">
						<img src="/images/common/test.png">
					</div>
					<div class="forum_info">
						<p class="type">${ chomp.category }</p>
						<h5>${ chomp.title }</h5>
						<div class="info">
							<p class="ing_flag">모집중</p>
							<p class="date">2025.03.04 - 2025.04.03</p>
						</div>
					</div>
				</a>
			</li>`
		).join("");
	})
});













/**
 * 카테고리 선택 
 */	
document.addEventListener('DOMContentLoaded', function() {
	const tabs = document.querySelectorAll(".btn_tab");

	tabs.forEach(tab => {
	    tab.addEventListener("click", function(event) {
	        event.preventDefault(); 
	        document.querySelector(".btn_tab.active")?.classList.remove("active");
	        this.classList.add("active");
	    });
	});	
});