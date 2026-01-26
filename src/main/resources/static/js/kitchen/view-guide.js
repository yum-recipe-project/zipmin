/**
 * 초기 함수를 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	fetchGuide();
	
});






/**
 * 키친가이드 상세 게시글 fetch 함수
 */
async function fetchGuide() {
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/guides/${id}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});

		const result = await response.json();

		if (result.code === 'KITCHEN_READ_SUCCESS') {
			const guide = result.data;
			if (!guide) return;
			renderGuide(guide, guide.likecount);
		}
		else if (result.code === 'KITCHEN_READ_FAIL') {
			alertDanger('가이드 조회에 실패했습니다.');
		}
		else if (result.code === 'KITCHEN_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alertDanger('사용자를 찾을 수 없습니다.');
		}
		else if (result.code === 'INTERVAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log('알 수 없는 에러:', result);
		}
	} catch (error) {
		console.error(error);
		alertDanger('가이드 상세 조회 중 오류가 발생했습니다.');
	}
}





/**
 * 가이드 상세 데이터 UI 렌더링 함수
 * @param {Object} guide - 가이드 객체
 * @param {number} likeCount - 스크랩 수
 */
function renderGuide(guide, likeCount) {
	document.querySelector('.subtitle').textContent = guide.subtitle || '';
	document.querySelector('.title').textContent = guide.title || '';

	document.querySelector('.guide_content').textContent = guide.content || '';

	// 가이드 헤더 정보
	const guideWriterDiv = document.querySelector('.guide_writer');
	guideWriterDiv.innerHTML = '';

	// 작성자 프로필 이미지
	if (guide.avatar) {
		const img = document.createElement('img');
		img.src = guide.avatar;
		img.alt = '작성자 프로필';
		guideWriterDiv.appendChild(img);
	} else {
		const profileSpan = document.createElement('span');
		profileSpan.className = 'profile_img';
		guideWriterDiv.appendChild(profileSpan);
	}

	const nicknameSpan = document.createElement('span');
	nicknameSpan.innerHTML = `<b>${guide.username || '집밥의민족'}</b>`;
	guideWriterDiv.appendChild(nicknameSpan);

	guideWriterDiv.append(' ・ ');

	const scrapSpan = document.createElement('span');
	scrapSpan.textContent = `스크랩 ${likeCount != null ? likeCount : 0}`;
	guideWriterDiv.appendChild(scrapSpan);

	guideWriterDiv.append(' ・ ');

	const dateSpan = document.createElement('span');
	dateSpan.textContent = formatDate(guide.postdate);
	guideWriterDiv.appendChild(dateSpan);

	const btn_wrap = document.querySelector('.btn_wrap');
	btn_wrap.innerHTML = '';
	const favBtn = renderFavoriteButton(guide.id, guide.likecount, guide.likestatus);
	btn_wrap.append(favBtn);
}




/**
 * 키친가이드 좋아요(찜) 버튼을 생성하는 함수
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
