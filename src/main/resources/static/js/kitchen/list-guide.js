/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 페이지네이션 클릭
	document.querySelectorAll('.pagination .page').forEach(page => {
	    page.addEventListener('click', function(e) {
	        e.preventDefault(); 

	        // 기존 active 제거
	        document.querySelectorAll('.pagination .page').forEach(item => {
	            item.classList.remove('active');
	        });

	        // 클릭한 페이지에 active 추가
	        this.classList.add('active');
	        
	        // 페이지 번호에 맞춰 prev/next 버튼 없앰
	        updatePaginationState();
	    });
	});

	// prev/next 버튼 클릭 이벤트
	document.querySelector('.pagination .prev').addEventListener('click', function(e) {
	    e.preventDefault();
	    
	    // 활성화된 페이지 번호에서 1 감소
	    const activePage = document.querySelector('.pagination .active');
	    const prevPage = activePage.previousElementSibling;
	    
	    if (prevPage && prevPage.classList.contains('page')) {
	        activePage.classList.remove('active');
	        prevPage.classList.add('active');
	    }
		
	    updatePaginationState();
	});

	document.querySelector('.pagination .next').addEventListener('click', function(e) {
	    e.preventDefault();

	    // 활성화된 페이지 번호에서 1 증가
	    const activePage = document.querySelector('.pagination .active');
	    const nextPage = activePage.nextElementSibling;

	    if (nextPage && nextPage.classList.contains('page')) {
	        activePage.classList.remove('active');
	        nextPage.classList.add('active');
	    }

	    updatePaginationState();
	});

	// prev/next 버튼 비활성화 상태 업데이트 함수
	function updatePaginationState() {
	    const pages = document.querySelectorAll('.pagination .page');
	    const prevButton = document.querySelector('.pagination .prev');
	    const nextButton = document.querySelector('.pagination .next');

	    // 첫 번째 페이지일 때 prev 버튼 숨김 
	    if (document.querySelector('.pagination .active') === pages[0]) {
	        prevButton.style.visibility = 'hidden'; 
	    } else {
	        prevButton.style.visibility = 'visible'; 
	    }

	    // 마지막 페이지일 때 next 버튼 숨김
	    if (document.querySelector('.pagination .active') === pages[pages.length - 1]) {
	        nextButton.style.visibility = 'hidden';  
	    } else {
	        nextButton.style.visibility = 'visible';  
	    }
	}

	updatePaginationState();

	// 가이드 찜 
	document.querySelectorAll(".favorite_btn").forEach(button => {
	    button.addEventListener("click", function(event) {
	        event.stopPropagation();
	        event.preventDefault(); 
	        
	        this.classList.toggle("active");
	    });
	});

	
	
	
	
	
	


});