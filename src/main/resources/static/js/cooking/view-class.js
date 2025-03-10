/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.tab a');
	// 탭 클릭 이벤트 설정
	tabItems.forEach((item) => {
	    item.addEventListener("click", function() {
	        tabItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	    });
	});
	tabItems.forEach(button => button.classList.remove('active'));
	tabItems[0].classList.add('active');
});
