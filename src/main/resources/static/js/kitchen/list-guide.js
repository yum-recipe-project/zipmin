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
 * 카테고리 버튼 클릭시 연결된 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 카테고리 버튼
	document.querySelectorAll('.btn_tab').forEach(btn => {
        btn.addEventListener('click', function (event) {
            event.preventDefault();
            document.querySelector('.btn_tab.active')?.classList.remove('active');
            btn.classList.add('active');

            category = btn.dataset.category;
            page = 0;
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
			
			sort = btn.dataset.sort;
			page = 0;
			guideList = [];
			
			fetchGuideList();
		});
	});
	

	// 검색
	const searchForm = document.querySelector('.search_form[data-type="kitchen"]');
	searchForm.addEventListener('submit', function(event) {
	    event.preventDefault();
	    keyword = this.querySelector('.search_word').value.trim();
	    page = 0;        
	    guideList = [];
	    fetchGuideList();
	});
	
	// 검색창 빈 경우 초기화
	searchForm.querySelector('.search_word')?.addEventListener('input', function () {
		if (this.value.trim() === '') {
			keyword = '';
			fetchGuideList();
		}
	});
	
	fetchGuideList();
});




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
			// 전역변수 설정
            totalPages = result.data.totalPages;
            page = result.data.number;
			guideList = result.data.content;
			
			// 랜더링
            renderGuideList(result.data.content);
            renderPagination(fetchGuideList);
			document.querySelector('.guide_util .total').innerText = `총 ${result.data.totalElements}개`;
		
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		// ****** 여기에 다른 에러 코드들 else if로 추가 ********
		// common/comment.js 참고 !!
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 키친가이드 목록을 화면에 렌더링하는 함수
 * 
 * @param {Array} guideList - 키친가이드 목록 배열
 */
function renderGuideList(guideList) {

	    
	const container = document.querySelector('.guide_list');
    container.innerHTML = '';

    guideList.forEach(guide => {
        const li = document.createElement('li');

        const a = document.createElement('a');
        a.href = `/kitchen/viewGuide.do?id=${guide.id}`;

        const guideItem = document.createElement('div');
        guideItem.className = 'guide_item';

        const guideDetails = document.createElement('div');
        guideDetails.className = 'guide_details';

        const guideTop = document.createElement('div');
        guideTop.className = 'guide_top';

        const subtitleSpan = document.createElement('span');
        subtitleSpan.textContent = guide.subtitle;

	    const favBtn = renderLikeButton(guide.id, guide.likecount, guide.likestatus);
	    guideTop.append(subtitleSpan, favBtn);
			
        const titleSpan = document.createElement('span');
        titleSpan.textContent = guide.title;

        const infoDiv = document.createElement('div');
        infoDiv.className = 'info';

        const scrapP = document.createElement('p');
        scrapP.textContent = `스크랩 ${guide.likecount}`;
		
        const dateP = document.createElement('p');
        dateP.textContent = formatDate(guide.postdate);

        infoDiv.append(scrapP, dateP);
		
		const writerDiv = document.createElement('div');
		writerDiv.className = 'writer';

		if (guide.avatar) {
		    const img = document.createElement('img');
		    img.src = guide.avatar;
		    writerDiv.appendChild(img);
		} else {
		    const profileSpan = document.createElement('span');
		    profileSpan.className = 'profile_img'; 
		    writerDiv.appendChild(profileSpan);
		}

		const nicknameP = document.createElement('p');
		nicknameP.textContent = '집밥의민족';
		writerDiv.appendChild(nicknameP);


		guideDetails.append(guideTop, titleSpan, infoDiv, writerDiv);
		guideItem.appendChild(guideDetails);
		a.appendChild(guideItem);
		li.appendChild(a);
		container.appendChild(li);
		
    });
}


/**
 * 키친가이드 좋아요(찜) 버튼을 생성하는 함수
 * 
 * @param {number} id - 가이드(게시글) ID
 * @param {number} likecount - 현재 좋아요 수
 * @param {boolean} isLiked - 사용자가 좋아요를 눌렀는지 여부
 */
function renderLikeButton(id, likecount, isLiked) {
	
	// 버튼 요소
	const button = document.createElement('button');
	button.className = 'favorite_btn';

	const img = document.createElement('img');
	img.src = isLiked ? '/images/common/star_full.png' : '/images/recipe/star_empty.png';

	button.append(img);

	// 클릭 이벤트
	button.addEventListener('click', async function (event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 스크랩 수 요소 
	    const likeCountElement = button.closest('.guide_item').querySelector('.info p:first-child');

	    // 현재 스크랩 수 계산 (likecount + 버튼 상태)
	    let currentCount = likecount;

		// 좋아요 취소
		if (isLiked) {
			try {
				const data = {
					tablename: 'guide',
					recodenum: id,
				};

				const response = await instance.delete(`/guides/${id}/likes`, {
					data: data,
					headers: getAuthHeaders(),
				});

				if (response.data.code === 'KITCHEN_UNLIKE_SUCCESS') {
					isLiked = false;
					img.src = '/images/recipe/star_empty.png';
					currentCount = Math.max(0, currentCount - 1); 
	                likeCountElement.textContent = `스크랩 ${currentCount}`;
	                likecount = currentCount;
				}
			} catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'KITCHEN_UNLIKE_FAIL') {
					alertDanger('찜 취소에 실패했습니다.');
				} else if (code === 'LIKE_DELETE_FAIL') {
					alertDanger('좋아요 삭제에 실패했습니다.');
				} else if (code === 'KITCHEN_INVALID_INPUT' || code === 'USER_INVALID_INPUT' || code === 'LIKE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				} else if (code === 'KITCHEN_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				} else if (code === 'LIKE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				} else if (code === 'LIKE_NOT_FOUND') {
					alertDanger('해당 찜을 찾을 수 없습니다.');
				} else if (code === 'KITCHEN_NOT_FOUND') {
					alertDanger('해당 가이드를 찾을 수 없습니다.');
				} else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				} else {
					console.log(error);
				}
			}
		}

		// 좋아요 추가
		else {
			try {
				const data = {
					tablename: 'guide',
					recodenum: id,
				};

				const response = await instance.post(`/guides/${id}/likes`, data, {
					headers: getAuthHeaders(),
				});

				if (response.data.code === 'KITCHEN_LIKE_SUCCESS') {
					isLiked = true;
					img.src = '/images/common/star_full.png';
					currentCount = currentCount + 1;
					likeCountElement.textContent = `스크랩 ${currentCount}`;
					likecount = currentCount; 
				}
			} catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'KITCHEN_LIKE_FAIL') {
					alertDanger('찜에 실패했습니다.');
				} else if (code === 'LIKE_CREATE_FAIL') {
					alertDanger('좋아요 생성에 실패했습니다.');
				} else if (code === 'KITCHEN_INVALID_INPUT' || code === 'USER_INVALID_INPUT' || code === 'LIKE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				} else if (code === 'KITCHEN_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				} else if (code === 'LIKE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				} else if (code === 'KITCHEN_NOT_FOUND') {
					alertDanger('해당 가이드를 찾을 수 없습니다.');
				} else if (code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				} else if (code === 'LIKE_DUPLICATE') {
					alertDanger('이미 찜한 가이드입니다.');
				} else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				} else {
					console.log(error);
				}
			}
		}
	});

	return button;
}

