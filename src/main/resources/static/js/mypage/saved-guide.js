/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let guideList = [];

document.addEventListener('DOMContentLoaded', function() {
    fetchSavedGuideList();

	document.querySelector('.btn_more').addEventListener('click', function() {
		fetchSavedGuideList();
	});
});

/**
 * 사용자가 저장한 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchSavedGuideList() {
    try {
        const token = localStorage.getItem('accessToken');
        const payload = parseJwt(token);

        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/users/${payload.id}/guides?${params}`);
		
		console.log(response);
		
		renderSavedGuideList(response.data.data.content);
		page = response.data.data.number + 1;
		totalPages = response.data.data.totalPages;
		document.getElementById('guideCount').innerText = response.data.data.totalElements + '개';
		document.querySelector('.btn_more').style.display = page >= totalPages ? 'none' : 'block';
    } catch (error) {
        console.error(error);
    }
}



/**
 * 저장한 키친가이드 목록을 화면에 렌더링하는 함수
 */
function renderSavedGuideList(guideList) {
	
	
    const container = document.getElementById('savedGuideList');

    guideList.forEach(guide => {
        const li = document.createElement('li');
        li.className = 'guide_item';
        li.dataset.id = guide.id;

        const guideDetails = document.createElement('div');
        guideDetails.className = 'guide_details';

        // 상단: 부제 + 찜 버튼
        const guideTop = document.createElement('div');
        guideTop.className = 'guide_top';

        const subtitleSpan = document.createElement('span');
        subtitleSpan.textContent = guide.subtitle;

        const favBtn = renderLikeButton(guide.id, guide.likecount, true);
        guideTop.append(subtitleSpan, favBtn);

        // 제목
        const titleSpan = document.createElement('span');
        titleSpan.textContent = guide.title;

        // 정보: 스크랩 수 + 작성일
        const infoDiv = document.createElement('div');
        infoDiv.className = 'info';

        const scrapP = document.createElement('p');
        scrapP.textContent = `스크랩 ${guide.likecount}`;
        const dateP = document.createElement('p');
        dateP.textContent = formatDate(guide.postdate);

        infoDiv.append(scrapP, dateP);

        // 작성자
        const writerDiv = document.createElement('div');
        writerDiv.className = 'writer';
        const profileSpan = document.createElement('span');
        profileSpan.className = 'profile_img';
        writerDiv.appendChild(profileSpan);

        const nicknameP = document.createElement('p');
        nicknameP.textContent = guide.nickname || '집밥의민족';
        writerDiv.appendChild(nicknameP);

        guideDetails.append(guideTop, titleSpan, infoDiv, writerDiv);
        li.appendChild(guideDetails);
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
					
					// 좋아요 취소한 가이드 제거
					const guideItem = button.closest('.guide_item');
					if (guideItem) {
						guideItem.remove();
					}
					
				    const guideCountEl = document.getElementById('guideCount');
				    if (guideCountEl) {
				        const currentTotal = parseInt(guideCountEl.innerText) || 0;
				        guideCountEl.innerText = `${Math.max(0, currentTotal - 1)}개`;
				    }

				    if (page >= totalPages) {
				        const loadMoreBtn = document.querySelector('.btn_more');
				        if (loadMoreBtn) loadMoreBtn.style.display = 'none';
				    }
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
