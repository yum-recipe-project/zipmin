/**
 * 드롭다운 메뉴 작동 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById("recipeSortBtn").addEventListener("click", function () {
	    document.querySelector(".dropdown").classList.toggle("active");
	});

	document.querySelectorAll(".dropdown_menu li").forEach(item => {
	    item.addEventListener("click", function () {
	        document.querySelector(".dropdown").classList.remove("active");
	    });
	});


});













