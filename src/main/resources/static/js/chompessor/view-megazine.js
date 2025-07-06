/**
 * 쩝쩝박사의 매거진 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	const params = new URLSearchParams(window.location.search);
	const id = params.get('id');
	
	// 매거진 정보 조회
	try {
		const response = await fetch(`/megazines/${id}`);
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'MEGAZINE_READ_SUCCESS') {
			document.querySelector('.megazine_title').innerText = result.data.title;
			document.querySelector('.megazine_content').innerText = result.data.content;
			const date = new Date(result.data.postdate);
			const formatDate = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
			document.querySelector('.megazine_postdate').innerText = formatDate;
		}
		else if (result.code === 'MEGAZINE_NOT_FOUND') {
			alert(result.message);
			location.href = '/chompessor/listChomp.do';
		}
		else {
			alert(result.message);
			location.href = '/chompessor/listChomp.do';
		}
	}
	catch(error) {
		console.log('서버 요청 중 오류 발생');
	}
});



/**
 * 댓글 정렬 버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const tablename = 'megazine';
	let sort = 'new';
	let size = 10;

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
			
			loadCommentList({ tablename, sort, size });
		});
	});
	
	// 최초 댓글 목록 조회
	loadCommentList({ tablename, sort, size });
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		loadCommentList({ tablename, sort, size });
	});
});



/**
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	document.getElementById('writeCommentForm').addEventListener("submit", function (event) {
		event.preventDefault();
		
		alert('댓글 작성');
		
		const tablename = 'megazine';
		const content = document.getElementById("writeCommentContent").value.trim();
		
		writeComment({ tablename, content });
	});
});





document.addEventListener('DOMContentLoaded', function () {
	const editForm = document.getElementById('editCommentForm');

	editForm.addEventListener('submit', async function (e) {
		e.preventDefault();

		const id = document.getElementById('editCommentId').value;
		const content = document.getElementById('editCommentContent').value.trim();

		if (!content) {
			alert('수정할 내용을 입력해주세요.');
			return;
		}

		// 댓글 수정 요청
		const result = await updateComment({ id, content });

		// 모달 닫기
		bootstrap.Modal.getInstance(document.getElementById('editCommentModal')).hide();

		// 수정 내용 반영
		const contentEl = document.querySelector(`.comment[data-id='${id}'] .comment_content, .subcomment[data-id='${id}'] .subcomment_content`);
		if (contentEl) {
		  contentEl.textContent = result.data.content;
		}
			
	});
});



