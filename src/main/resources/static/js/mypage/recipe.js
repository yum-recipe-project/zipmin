/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
	
	// 게시글 진행상태 선택
	document.querySelectorAll('.recipe_tap li').forEach(tab => {
	    tab.addEventListener('click', function() {
			
			// 모든 탭 active 제거
	        document.querySelectorAll('.recipe_tap li').forEach(item => {
	            item.classList.remove('active');
	        });

	        // 클릭된 탭 active 추가
	        this.classList.add('active');

	        // 모든 탭 컨텐츠 active 제거
	        document.querySelectorAll('.recipe_content').forEach(content => {
	            content.classList.remove('active');
	        });

			// 클릭된 탭과 클래스명이 동일한 컨텐츠 활성화
	        const tabName = this.classList[0]; 
	        document.querySelector(`.recipe_content.${tabName}`).classList.add('active');
	    });
	});


	

});