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
function fetchGuideDetail(guideId) {
    console.log(`[fetchGuideDetail] Fetching URL: /guides/${guideId}`);

    fetch(`/guides/${guideId}`)
        .then(res => res.json())
        .then(result => {
            const response = result.data;
			if (!response || !response.guide) return;

            renderGuide(response.guide, response.likecount);
        })
        .catch(console.error);
}

/**
 * 가이드 상세 데이터 UI 렌더링 함수
 * @param {Object} guide - 가이드 객체
 */
function renderGuide(guide, likeCount) {
	document.querySelector('.subtitle').textContent = guide.subtitle || '';
	document.querySelector('.title').textContent = guide.title || '';
	document.querySelector('.post_date').textContent = formatDate(guide.postdate);
	document.querySelector('.guide_content').textContent = guide.content || '';
	document.querySelector('.scrap').textContent = (likeCount != null) ? likeCount : '0'; // 데이터가 없을 경우 스크랩 수가 0

	
	console.log("likeCount: " + likeCount);
	
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
