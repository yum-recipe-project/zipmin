/**
 * 탭 메뉴 클릭시 탭 메뉴를 활성화하고 해당 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.class_sort button');
	const contentItems = document.querySelectorAll('.class_list');
	// 탭 클릭 이벤트 설정
	tabItems.forEach((item, index) => {
	    item.addEventListener("click", function(event) {
	        event.preventDefault();
	        
	        tabItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	        
	        contentItems.forEach(content => content.style.display = 'none');
	        contentItems[index].style.display = 'block';
	    });
	});
	// 기본으로 첫번째 활성화
	tabItems.forEach(button => button.classList.remove('active'));
	contentItems.forEach(content => content.style.display = 'none');
	tabItems[0].classList.add('active');
	contentItems[0].style.display = 'block';
});
















