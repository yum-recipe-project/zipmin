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
	document.querySelector('.search_form[data-type="kitchen"]').addEventListener('submit', function(e) {
	    e.preventDefault();
	    keyword = this.querySelector('.search_word').value.trim();
	    page = 0;        
	    guideList = [];
	    fetchGuideList();
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
			
            totalPages = result.data.totalPages;
            page = result.data.number;
			guideList = result.data.content;
			
            renderGuideList(result.data.content);
            renderPagination();
			
			document.querySelector('.guide_util .total').innerText = `총 ${result.data.totalElements}개`;
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

        if (guide.writerImage) {
            const img = document.createElement('img');
            img.src = guide.writerImage;
            writerDiv.appendChild(img);
        }
		else {
            const profileImg = document.createElement('span');
            profileImg.className = 'profile_img';
            writerDiv.appendChild(profileImg);
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






/**
 * 페이지네이션을 화면에 렌더링하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchGuideList();
			}
		});
	});
}

