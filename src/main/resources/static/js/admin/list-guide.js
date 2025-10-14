/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	fetchGuideList();
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin('/');
	}
	
});


/**
 * 전역 변수
 */
let category = '';
let sort = 'id-desc';
let totalPages = 0;
let page = 0;
let keyword = '';
const size = 10;
let guideList = [];

let sortKey = 'postdate';  // 기본 정렬 키
let sortOrder = 'desc';    // 기본 정렬 순서

/**
 * 서버에서 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchGuideList() {
	
	try {
		const params = new URLSearchParams({
			category: category,
			keyword: keyword,  
//			sort: sort,
			sort: sortKey + '-' + sortOrder,
			page: page,
			size: size
		});
		
		const response = await fetch(`/guides?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'KITCHEN_READ_LIST_SUCCESS') {
			
            totalPages = result.data.totalPages;
            page = result.data.number;
			guideList = result.data.content;
			
            renderGuideList(result.data.content);
			renderAdminPagination(fetchGuideList);
			
		}
	}
	catch (error) {
		console.log(error);
	}
	
}


/**
 * 키친가이드 목록 내용 정렬 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 정렬 버튼
	document.querySelectorAll('.sort_btn').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			const key = btn.dataset.key;

		    if (sortKey === key) {
		      sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
		    }
			else {
		      sortKey = key;
		      sortOrder = 'desc';
		    }
			
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			this.classList.add(sortOrder);
			
			page = 0;
			
			console.log("sortOrder: " + sortOrder)
			fetchGuideList();
		});
	});
});





/**
 * 키친가이드 목록을 화면에 렌더링하는 함수
 */
function renderGuideList(guideList) {
    const container = document.querySelector('.guide_list');
    container.innerHTML = '';

    if (!Array.isArray(guideList)) return;

    guideList.forEach((guide, index) => {
        const tr = document.createElement('tr');
        tr.dataset.id = guide.id;

        // No
        const noTd = document.createElement('td');
        const noH6 = document.createElement('h6');
        noH6.className = 'fw-semibold mb-0';
		noH6.textContent = guide.id;
        noTd.appendChild(noH6);

        // 카테고리
        const categoryTd = document.createElement('td');
        const categoryH6 = document.createElement('h6');
        categoryH6.className = 'fw-semibold mb-0';
        categoryH6.textContent = convertCategory(guide.category);
        categoryTd.appendChild(categoryH6);

		// 제목 
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-1 view';
		titleH6.textContent = guide.title;
		titleH6.dataset.id = guide.id;
		const subInfo = document.createElement('small');
		subInfo.className = 'text-muted d-block';
		subInfo.textContent = guide.subtitle || '-';
		
		titleTd.append(subInfo,titleH6);

        // 작성자
        const adminTd = document.createElement('td');
        const adminH6 = document.createElement('h6');
        adminH6.className = 'fw-semibold mb-0';
        adminH6.textContent = guide.username || '-';
        adminTd.appendChild(adminH6);

        // 작성일
        const dateTd = document.createElement('td');
        const dateH6 = document.createElement('h6');
        dateH6.className = 'fw-semibold mb-0';
        dateH6.textContent = formatDateDot(guide.postdate);
        dateTd.appendChild(dateH6);

        // 좋아요 수
        const likeTd = document.createElement('td');
        const likeH6 = document.createElement('h6');
        likeH6.className = 'fw-semibold mb-0';
        likeH6.textContent = guide.likecount ?? 0;
        likeTd.appendChild(likeH6);

        // 신고 수 
		// TODO: 신고 기능 연결하기
        const reportTd = document.createElement('td');
        const reportH6 = document.createElement('h6');
        reportH6.className = 'fw-semibold mb-0';
        reportH6.textContent = guide.reportcount ?? 0;
        reportTd.appendChild(reportH6);

        tr.append(noTd, categoryTd, titleTd, adminTd, dateTd, likeTd, reportTd);
        container.appendChild(tr);
    });
}


/**
 * 카테고리 code 한글 변환 함수
 */
function convertCategory(code) {
    switch (code) {
        case 'preparation': return '손질법';
        case 'storage': return '보관법';
        case 'info': return '요리 정보';
        case 'etc': return '기타 정보';
        default: return code || '';
    }
}
