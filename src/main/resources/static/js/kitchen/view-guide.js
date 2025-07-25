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
 * 날짜 포맷 함수 (yyyy.mm.dd)
 */
function formatDate(isoString) {
    const date = new Date(isoString);
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    return `${yyyy}.${mm}.${dd}`;
}
