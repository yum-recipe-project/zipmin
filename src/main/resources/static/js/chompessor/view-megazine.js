/**
 * 쩝쩝박사의 매거진 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	
	// 매거진 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/megazines/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'MEGAZINE_READ_SUCCESS') {
			document.querySelector('.megazine_title').innerText = result.data.title;
			document.querySelector('.megazine_content').innerHTML = result.data.content;
			document.querySelector('.megazine_postdate').innerText = `${formatDate(result.data.closedate)}`;
		}
		else if (result.code === 'MEGAZINE_NOT_FOUND') {
			alert('해당 매거진을 찾을 수 없습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else {
			console.log(error);
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
	
	const tablename = 'megazine';
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
		writeComment('megazine');
	});
	
});





/**
 * 대댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	document.getElementById('writeSubcommentForm').addEventListener('submit', function (event) {
		event.preventDefault();
		writeSubcomment('megazine');
	});
	
});


