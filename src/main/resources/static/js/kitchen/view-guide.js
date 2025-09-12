document.addEventListener('DOMContentLoaded', () => {
    const guideId = getGuideIdFromQuery(); 
    if (guideId) {
        fetchGuideDetail(guideId);
    }
});

/**
 * 요청 URL에서 guideId 추출
 */
function getGuideIdFromQuery() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}



/**
 * 키친가이드 상세 게시글 fetch 함수
 * @param {number} guideId - 요청할 가이드 ID
 */
async function fetchGuideDetail(guideId) {
    console.log(`[fetchGuideDetail] Fetching URL: /guides/${guideId}`);

    try {
        const response = await fetch(`/guides/${guideId}`, {
            method: 'GET',
            headers: getAuthHeaders() 
        });

        if (!response.ok) {
            console.error('Failed to fetch guide detail:', response.status, response.statusText);
            return;
        }

        const result = await response.json();
		console.log(result); // <- 여기서 실제 구조 확인
        const guide = result.data;

		if (!guide) return;

		renderGuide(guide, guide.likecount);

    } catch (error) {
        console.error(error);
    }
}






/**
 * 가이드 상세 데이터 UI 렌더링 함수
 * @param {Object} guide - 가이드 객체
 */
function renderGuide(guide, likeCount) {
	
	console.log("likestatus:", guide.likestatus)
	
	document.querySelector('.subtitle').textContent = guide.subtitle || '';
	document.querySelector('.title').textContent = guide.title || '';
	document.querySelector('.post_date').textContent = formatDate(guide.postdate);
	document.querySelector('.guide_content').textContent = guide.content || '';
	document.querySelector('.scrap').textContent = (likeCount != null) ? likeCount : '0'; // 데이터가 없을 경우 스크랩 수가 0
	document.querySelector('.writer').textContent = '집밥의민족';
	
	const btn_wrap = document.querySelector('.btn_wrap');
	btn_wrap.innerHTML = '';
	
	const favBtn = renderFavoriteButton(guide.id, guide.likecount, guide.likestatus);
    btn_wrap.append(favBtn);
}



/**
 * 키친가이드 좋아요(찜) 버튼을 생성하는 함수
 * 
 * @param {number} id - 가이드(게시글) ID
 * @param {number} likecount - 현재 좋아요 수
 * @param {boolean} isLiked - 사용자가 좋아요를 눌렀는지 여부
 */
function renderFavoriteButton(id, likecount, isLiked) {
	
	// 버튼 요소
	const button = document.createElement('button');
	button.className = 'btn_tool';

	const img = document.createElement('img');
	img.src = isLiked ? '/images/common/star_full.png' : '/images/recipe/star_empty.png';
	button.append(img);
	
	// 스크랩 수 표시
    const countSpan = document.createElement('span');
    countSpan.className = 'btn_likecount';
    countSpan.textContent = ` ${likecount}`;
    button.append(countSpan);
	

	// 클릭 이벤트
	button.addEventListener('click', async function (event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 버튼 옆 스크랩 수 요소 
		const likeCountElement = document.querySelector('.btn_likecount');
		// 게시자 옆 스크랩 수 요소
		const scrapElement = document.querySelector('.scrap');

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
	                likeCountElement.textContent = `${currentCount}`;
	                scrapElement.textContent = `${currentCount}`;
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
					likeCountElement.textContent = `${currentCount}`;
					scrapElement.textContent = `${currentCount}`;
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
 * 댓글 정렬 버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const tablename = 'guide';
	let sort = 'postdate-desc';
	let size = 15;

	// 정렬 버튼 클릭 시 초기화 후 댓글 목록 조회
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			
			sort = this.getAttribute('data-sort');
			page = 0;
			
			document.querySelector('.comment_list').innerHTML = '';
			commentList = [];
			
			fetchCommentList(tablename, sort, size);
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList(tablename, sort, size);
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList(tablename, sort, size);
	});
});



/**
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	document.getElementById('writeCommentForm').addEventListener("submit", function (event) {
		event.preventDefault();
		writeComment('guide');
	});
	
});



/**
 * 대댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	document.getElementById('writeSubcommentForm').addEventListener('submit', function (event) {
		event.preventDefault();
		writeSubcomment('guide');
	});
	
});
