/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let totalElements = 0;
let keyword = '';
let category = '';
let sort = 'id-desc';
let guideList = [];





/**
 * 카테고리 버튼 클릭시 연결된 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="kitchen"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		page = 0;	  
		keyword = this.querySelector('.search_word').value.trim();
		guideList = [];
		fetchGuideList();
	});
	
	// 검색창 빈 경우 초기화
	searchForm.querySelector('.search_word')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			page = 0;
			keyword = '';
			guideList = [];
			fetchGuideList();
		}
	});
	
	// 카테고리 버튼
	document.querySelectorAll('.btn_tab').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab.active')?.classList.remove('active');
			btn.classList.add('active');
			page = 0;
			category = btn.dataset.category;
			guideList = [];
			fetchGuideList();
		});
	});
	
	// 정렬 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			page = 0;
			sort = btn.dataset.sort;
			guideList = [];
			fetchGuideList();
		});
	});
	
	fetchGuideList();
});





/**
 * 서버에서 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchGuideList(scroll = true) {
	
	try {
		const params = new URLSearchParams({
			keyword: keyword,  
			category: category,
			sort: sort,
			page: page,
			size: size
		}).toString();
		
		const response = await fetch(`/guides?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'KITCHEN_READ_LIST_SUCCESS') {

			page = result.data.number;
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			guideList = result.data.content;
			
			renderGuideList(result.data.content);
			renderPagination(fetchGuideList);
			document.querySelector('.guide_util .total').innerText = `총 ${totalElements}개`;
			
			if (scroll) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
		else if (result.code === 'KITCHEN_READ_LIST_FAIL') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'KITCHEN_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'LIKE_EXIST_FAIL') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'KITCHEN_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'LIKE_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'USER_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (result.code === 'INTERVAL_SERVER_ERROR') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
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
	
	// 키친가이드 목록이 존재하지 않는 경우
	if (guideList == null || guideList.length === 0) {
		document.querySelector('.guide_list').style.display = 'none';
		document.querySelector('.search_empty')?.remove();
		document.querySelector('.guide_content').insertAdjacentElement('afterend', renderSearchEmpty());
		return;
	}

	// 키친가이드 목록이 존재하는 경우
	container.style.display = 'block';
	document.querySelector('.search_empty')?.remove();
	
	guideList.forEach(guide => {
		const li = document.createElement('li');

		const a = document.createElement('a');
		a.href = `/kitchen/viewGuide.do?id=${guide.id}`;

		const itemDiv = createDiv('guide_item');
		const detailDiv = createDiv('guide_details');
		
		const topDiv = createDiv('guide_top');
		const subtitleDiv = createSpan(guide.subtitle);
		
		const likeButton = createButton('favorite_btn');
		const likeImg = createImg(
			guide.likestatus ? '/images/common/star_full.png' : '/images/recipe/star_empty.png'
		);
		likeButton.appendChild(likeImg);
		likeButton.addEventListener('click', function(event) {
			event.preventDefault();
			if (!isLoggedIn()) {
				redirectToLogin();
				return;
			}
			if (guide.likestatus) {
				unlikeGuide(guide.id);
			}
			else {
				likeGuide(guide.id);
			}
		});
		
		topDiv.append(subtitleDiv, likeButton);
		
		const titleSpan = createSpan(guide.title);

		const infoDiv = createDiv('info');
		infoDiv.append(
			createP(`스크랩 ${guide.likecount}`),
			createP(formatDate(guide.postdate))
		);

		const writerDiv = createDiv('writer');
		if (guide.avatar) {
			writerDiv.appendChild(createImg(guide.avatar));
		}
		else {
			writerDiv.appendChild(createSpan('', profile_img));
		}
		writerDiv.appendChild(createP('집밥의민족'));

		detailDiv.append(topDiv, titleSpan, infoDiv, writerDiv);
		itemDiv.appendChild(detailDiv);
		a.appendChild(itemDiv);
		li.appendChild(a);
		container.appendChild(li);
	});
}





/**
 * 키친가이드에 좋아요를 표시하는 함수
 */
async function likeGuide(id) {
	
	try {
		const data = {
			tablename: 'guide',
			recodenum: id,
		}
		
		const response = await instance.post(`/guides/${id}/likes`, data, {
			headers: getAuthHeaders(),
		});
		
		if (response.data.code === 'KITCHEN_LIKE_SUCCESS') {
			fetchGuideList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'KITCHEN_LIKE_FAIL') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'LIKE_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_UNAUTHORIZED') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'LIKE_FORBIDDEN') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_NOT_FOUND') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
	}
}





/**
 * 키친가이드의 좋아요를 취소하는 함수
 */
async function unlikeGuide(id) {
	
	try {
		const data = {
			tablename: 'guide',
			recodenum: id,
		}
		
		const response = await instance.delete(`/guides/${id}/likes`, {
			headers: getAuthHeaders(),
			data: data
		});
		
		if (response.data.code === 'KITCHEN_UNLIKE_SUCCESS') {
			fetchGuideList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'KITCHEN_UNLIKE_FAIL') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'LIKE_DELETE_FAIL') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'LIKE_INVALID_INPUT') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_UNAUTHORIZED') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'LIKE_FORBIDDEN') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'KITCHEN_NOT_FOUND') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
		else {
			alertDanger('일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		}
	}
}