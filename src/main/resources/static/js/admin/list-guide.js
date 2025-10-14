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

/**
 * 서버에서 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchGuideList() {
	
	try {
		const params = new URLSearchParams({
			category: category,
			keyword: keyword,  
			sort: sort,
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
//            renderPagination();
			
		}
	}
	catch (error) {
		console.log(error);
	}
	
}




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

        const noTd = document.createElement('td');
        const noH6 = document.createElement('h6');
        noH6.className = 'fw-semibold mb-0';
        noH6.textContent = totalPages * size - index; // 역순 번호
        noTd.appendChild(noH6);

		const categoryTd = document.createElement('td');
		const categoryH6 = document.createElement('h6');
		categoryH6.className = 'fw-semibold mb-0';

		switch (guide.category) {
		    case 'preparation':
		        categoryH6.textContent = '손질법';
		        break;
		    case 'storage':
		        categoryH6.textContent = '보관법';
		        break;
		    case 'info':
		        categoryH6.textContent = '요리 정보';
		        break;
		    case 'etc':
		        categoryH6.textContent = '기타 정보';
		        break;
		    default:
		        categoryH6.textContent = guide.category || '';
		}
		categoryTd.appendChild(categoryH6);

        // 제목 (title + subtitle)
        const titleTd = document.createElement('td');
        const titleH6 = document.createElement('h6');
        titleH6.className = 'fw-semibold mb-0 truncate text-start';
        titleH6.textContent = guide.title + (guide.subtitle ? ' - ' + guide.subtitle : '');
        titleTd.appendChild(titleH6);

        // 작성일
        const dateTd = document.createElement('td');
        const dateH6 = document.createElement('h6');
        dateH6.className = 'fw-semibold mb-0';
        dateH6.textContent = formatDateTime(guide.postdate);
        dateTd.appendChild(dateH6);
		
		// 작성자
		const adminTd = document.createElement('td');
		const adminH6 = document.createElement('h6');
		dateH6.className = 'fw-semibold mb-0';
		dateH6.textContent = guide.username;
		dateTd.appendChild(dateH6);
		
		

        // 좋아요 수
        const likeTd = document.createElement('td');
        const likeH6 = document.createElement('h6');
        likeH6.className = 'fw-semibold mb-0';
        likeH6.textContent = guide.likecount ?? 0;
        likeTd.appendChild(likeH6);

        // 신고 수 (임시 0으로 표시, 필요시 API에서 가져오기)
        const reportTd = document.createElement('td');
        const reportH6 = document.createElement('h6');
        reportH6.className = 'fw-semibold mb-0';
//        reportH6.textContent = guide.reportcount ?? 0;
        reportH6.textContent = 0;
        reportTd.appendChild(reportH6);

        tr.append(noTd, categoryTd, titleTd, dateTd, likeTd, reportTd);
        container.appendChild(tr);
    });
}
