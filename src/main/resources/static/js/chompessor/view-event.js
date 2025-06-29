/**
 * 쩝쩝박사의 이벤트 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	const params = new URLSearchParams(window.location.search);
	const id = params.get('id');
	
	// 투표 정보 조회
	try {
		const response = await fetch(`/events/${id}`);
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'EVENT_READ_SUCCESS') {
			document.querySelector('.event_title').innerText = result.data.title;
			document.querySelector('.event_content').innerText = result.data.content;
			const opendate = new Date(result.data.opendate);
			const closedate = new Date(result.data.closedate);
			const formatDate = `${opendate.getFullYear()}년 ${String(opendate.getMonth() + 1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일 - ${closedate.getFullYear()}년 ${String(closedate.getMonth() + 1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;
			document.querySelector('.event_postdate').innerText = formatDate;
		}
		
	}
	catch(error) {
		console.log(error);
	}
	
	
});








/**
 * 댓글 정렬 버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
	
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
			
			fetchCommentList('chomp_event');
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList('chomp_event');
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList('chomp_event');
	});
});
