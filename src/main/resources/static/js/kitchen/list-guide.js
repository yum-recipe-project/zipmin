
/**
 * 전역 변수 선언
 */
let category = 'all';
let totalPages = 0;
let page = 0; // 현재 페이지 번호
const size = 5; // 한번에 가져올 데이터 개수
let guideList = [];

document.addEventListener('DOMContentLoaded', function() {
	
	// 키친가이드 목록 가져옴
	fetchGuideList(page);
	

	/**
	 * 키친가이드 목록 데이터를 가져오는 함수
	 */
	function fetchGuideList(num) {
	    fetch(`/guides?category=${category}&page=${num}&size=${size}`, {
	        method: 'GET'
	    })
	    .then(response => response.json())
	    .then(result => {
	        const data = result.data;
	        if (!data || !data.content) return;

	        console.log(data); // 콘솔 출력

	        const guideList = document.querySelector('.guide_list');

	        data.content.forEach(item => {
	            const li = document.createElement('li');
	            li.innerHTML = `
	                <a href="/kitchen/viewGuide.do?id=${item.id}">
	                    <div class="guide_item">
	                        <div class="guide_details">
	                            <div class="guide_top">
	                                <span>${item.subtitle}</span>
	                                <button class="favorite_btn"></button>
	                            </div>
	                            <span>${item.title}</span>
	                            <div class="info">
	                                <p>스크랩 ${item.scrapCount || 0}</p>
	                                <p>${formatDate(item.postdate)}</p>
	                            </div>
	                            <div class="writer">
	                                ${item.writerImage ?
	                                    `<img src="${item.writerImage}">` :
	                                    `<span class="profile_img"></span>`}
	                                <p>${item.writerNickname || '작성자'}</p>
	                            </div>
	                        </div>
	                    </div>
	                </a>
	            `;
	            guideList.appendChild(li);
	        });
	    })
	    .catch(error => console.error(error));
	}

	// 날짜 포맷 함수 (yyyy.mm.dd 형태로)
	function formatDate(isoString) {
	    const date = new Date(isoString);
	    const yyyy = date.getFullYear();
	    const mm = String(date.getMonth() + 1).padStart(2, '0');
	    const dd = String(date.getDate()).padStart(2, '0');
	    return `${yyyy}.${mm}.${dd}`;
	}



	
	/**
	 * 페이지네이션 
	 */
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

	/**
	 * 페이지네이션 - rev/next 버튼 클릭 
	 */
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

	/**
	 * 페이지네이션 - prev/next 버튼 비활성화 상태 업데이트 함수
	 */
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

	/**
	 * 가이드 찜
	 */
	document.querySelectorAll(".favorite_btn").forEach(button => {
	    button.addEventListener("click", function(event) {
	        event.stopPropagation();
	        event.preventDefault(); 
	        
	        this.classList.toggle("active");
	    });
	});
	
	
	/**
	 * 카테고리 선택 
	 */	
	const tabs = document.querySelectorAll(".btn_tab");

	tabs.forEach(tab => {
	    tab.addEventListener("click", function(event) {
	        event.preventDefault();

	        document.querySelector(".btn_tab.active")?.classList.remove("active");

	        this.classList.add("active");
	    });
	});

});