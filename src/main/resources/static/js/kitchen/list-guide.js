/**
 * 전역 변수
 */
let category = 'all';
let totalPages = 0;
let page = 0;
const size = 5;

document.addEventListener('DOMContentLoaded', () => {
    init();
});

/**
 * 초기화 함수
 * - 가이드 목록 조회
 * - 찜 버튼 이벤트 등록
 * - 카테고리 탭 이벤트 등록
 */
function init() {
    fetchGuideList(page);
    initFavoriteButtons();
    initCategoryTabs();
}

/**
 * 키친가이드 목록 fetch 함수
 * @param {number} num - 요청할 페이지 번호
 */
function fetchGuideList(num) {
    console.log(`[fetchGuideList] Fetching URL: /guides?category=${category}&page=${num}&size=${size}`);

	fetch(`/guides?category=${category}&page=${num}&size=${size}`)
	        .then(res => res.json())
	        .then(result => {
	            const data = result.data;
	            if (!data?.content) return;

	            totalPages = data.totalPages;
	            page = num;  // 현재 페이지 갱신

	            renderGuideList(data.content);
	            renderPagination(); // 동적 페이지네이션 렌더링
				
				// 총 게시글 개수 
				const totalCountElement = document.querySelector('.guide_util .total');
				if (totalCountElement && typeof data.totalElements === 'number') {
				    totalCountElement.textContent = `총 ${data.totalElements}개`;
				}

	        })
	        .catch(console.error);
}

/**
 * 가이드 리스트 UI 렌더링 함수
 * @param {Array} items - 가이드 항목 배열
 */
function renderGuideList(items) {
    const guideList = document.querySelector('.guide_list');
    guideList.innerHTML = '';

    items.forEach(item => {
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

    initFavoriteButtons(); // 찜 버튼 이벤트 재등록
}

/**
 * 찜 버튼 클릭 이벤트 등록 함수
 */
function initFavoriteButtons() {
    document.querySelectorAll(".favorite_btn").forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            e.stopPropagation();
            button.classList.toggle("active");
        });
    });
}

/**
 * 카테고리 탭 클릭 이벤트 등록 함수
 */
function initCategoryTabs() {
    document.querySelectorAll(".btn_tab").forEach(tab => {
        tab.addEventListener("click", e => {
            e.preventDefault();
            document.querySelector(".btn_tab.active")?.classList.remove("active");
            tab.classList.add("active");

            category = tab.dataset.category || 'all'; // 선택된 카테고리 설정
            page = 0;
            fetchGuideList(page); // 선택된 카테고리 목록 fetch
        });
    });
}


/**
 * 페이지네이션 렌더링 함수
 */
function renderPagination() {
    const paginationContainer = document.querySelector(".pagination ul");
    paginationContainer.innerHTML = '';

    const currentGroup = Math.floor(page / size);
    const start = currentGroup * size;
    const end = Math.min(start + size, totalPages);

    // prev
    const prev = document.createElement('li');
    prev.innerHTML = `<a href="#" class="prev"><img src="/images/common/chevron_left.png"></a>`;
    if (start === 0) prev.style.visibility = 'hidden';
    prev.addEventListener('click', e => {
        e.preventDefault();
        if (start > 0) {
            fetchGuideList(start - 1);
        }
    });
    paginationContainer.appendChild(prev);

    // page 개수
    for (let i = start; i < end; i++) {
        const li = document.createElement('li');
        li.innerHTML = `<a href="#" class="page ${i === page ? 'active' : ''}">${i + 1}</a>`;
        li.querySelector('a').addEventListener('click', e => {
            e.preventDefault();
            fetchGuideList(i);
        });
        paginationContainer.appendChild(li);
    }

    // next
    const next = document.createElement('li');
    next.innerHTML = `<a href="#" class="next"><img src="/images/common/chevron_right.png"></a>`;
    if (end >= totalPages) next.style.visibility = 'hidden';
    next.addEventListener('click', e => {
        e.preventDefault();
        if (end < totalPages) {
            fetchGuideList(end);
        }
    });
    paginationContainer.appendChild(next);
}

/**
 * 날짜 포맷 함수 (yyyy.mm.dd)
 * @param {string} isoString - ISO 날짜 문자열
 * @returns {string} 포맷된 날짜 문자열
 */
function formatDate(isoString) {
    const date = new Date(isoString);
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    return `${yyyy}.${mm}.${dd}`;
}
