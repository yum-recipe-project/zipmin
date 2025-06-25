/**
 * 쩝쩝박사의 매거진 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const params = new URLSearchParams(window.location.search);
	const id = params.get('id');
	
	// 매거진 정보 조회
	fetch(`/megazines/${id}`, {
		method: 'GET'
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector('.megazine_title').innerText = data.chomp_dto.title;
		document.querySelector('.megazine_content').innerText = data.content;
		const date = new Date(data.postdate);
		const formatDate = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
		document.querySelector('.megazine_postdate').innerText = formatDate;
	})
	.catch(error => console.log(error));
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
			
			fetchCommentList('chomp_megazine');
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList('chomp_megazine');
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList('chomp_megazine');
	});
});
