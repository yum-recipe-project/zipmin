/**
 * 댓글을 수정하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContent = document.getElementById('editCommentContent');
	const editCommentButton = document.querySelector('#editCommentForm button[type="submit"]');
	editCommentContent.addEventListener('input', function() {
		const isCommentContentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle('disabled', isCommentContentEmpty);
	});
});





/**
 * 더보기버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
let totalPages = 0;
let page = 0;
let size = 10;
let commentList = [];

document.addEventListener('DOMContentLoaded', function() {
	
	fetchCommentList();
	
	document.querySelector('.btn_more').addEventListener('click', function() {
		fetchCommentList();
	});
	
});





/**
 * 서버에서 댓글 목록 데이터를 가져오는 함수
 */
async function fetchCommentList() {
	
	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const params = new URLSearchParams({
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/comments?${params}`);
		
		console.log(response);
		
		renderCommentList(response.data.data.content);
		page = response.data.data.number + 1;
		totalPages = response.data.data.totalPages;
		document.querySelector('.mycomment_count span').innerText = response.data.data.totalElements + '개';
		document.querySelector('.btn_more').style.display = page >= totalPages ? 'none' : 'block';
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 댓글 목록을 화면에 렌더링하는 함수
 * 
 * @param {Object} comments - 댓글 목록 객체
 */
function renderCommentList(commentList) {
	
	const tablename = {
		vote: '투표',
		megazine: '매거진',
		event: '이벤트'
	};
	
	commentList.forEach(comment => {
		const li = document.createElement('li');
		li.className = 'mycomment_item';
		li.dataset.id = comment.id;
		li.dataset.commId = comment.comm_id;
		
		const titleDiv = document.createElement('div');
		titleDiv.className = 'mycomment_title';
		
		if (comment.title) {
			const a = document.createElement('a');
			a.href = `/${comment.tablename}/view.do?id=${comment.recodenum}`;
			a.textContent = comment.title;
			titleDiv.appendChild(a);
		}
		
		const boardP = document.createElement('p');
		boardP.className = 'board';
		boardP.textContent = tablename[comment.tablename] || comment.tablename;
		titleDiv.appendChild(boardP);
		
		const commentDiv = document.createElement('div');
		commentDiv.className = 'mycomment';
		
		const infoDiv = document.createElement('div');
		infoDiv.className = 'comment_info';
		
		const writerDiv = document.createElement('div');
		writerDiv.className = 'comment_writer';
		
		const img = document.createElement('img');
		img.src = '/images/common/test.png';
		writerDiv.appendChild(img);
		
		const nameSpan = document.createElement('span');
		nameSpan.textContent = comment.nickname;
		writerDiv.appendChild(nameSpan);
		
		const dateSpan = document.createElement('span');
		dateSpan.textContent = formatDate(comment.postdate);
		writerDiv.appendChild(dateSpan);
		
		const actionDiv = document.createElement('div');
		actionDiv.className = 'comment_action';
		
		const editLink = document.createElement('a');
		editLink.href = 'javascript:void(0);';
		editLink.dataset.bsToggle = 'modal';
		editLink.dataset.bsTarget = '#editCommentModal';
		editLink.textContent = '수정';
		editLink.addEventListener('click', () => {
			document.getElementById('editCommentContent').value = comment.content;
			document.getElementById('editCommentId').value = comment.id;
		});
		actionDiv.appendChild(editLink);

		const deleteLink = document.createElement('a');
		deleteLink.href = 'javascript:void(0);';
		deleteLink.textContent = '삭제';
		deleteLink.addEventListener('click', async () => {
			await deleteComment(comment.id);
		});
		actionDiv.append(editLink, deleteLink);
		
		infoDiv.appendChild(writerDiv);
		infoDiv.appendChild(actionDiv);
		
		const contentP = document.createElement('p');
		contentP.className = 'comment_content';
		contentP.textContent = comment.content;
		
		commentDiv.appendChild(infoDiv);
		commentDiv.appendChild(contentP);
		
		li.appendChild(titleDiv);
		li.appendChild(commentDiv);
		
		document.querySelector('.mycomment_list').appendChild(li);
	});
}






/**
 * 댓글을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const editForm = document.getElementById('editCommentForm');

	editForm.addEventListener('submit', async function(e) {
		e.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}

		const id = document.getElementById('editCommentId').value;
		const content = document.getElementById('editCommentContent').value.trim();

		try {
			const token = localStorage.getItem('accessToken');
			
			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
			
			const data = {
				id: id,
				content: content
			};
			
			const response = await instance.patch(`/comments/${id}`, data, {
				headers: headers
			});
			
			if (response.data.code === 'COMMENT_UPDATE_SUCCESS') {
				const commentEl = document.querySelector(`.mycomment_item[data-id='${id}'] .comment_content`);
				if (commentEl) commentEl.textContent = response.data.data.content;
				
				bootstrap.Modal.getInstance(document.getElementById('editCommentModal')).hide();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			const message = error?.response?.data?.message;
			
			if (code === 'COMMENT_UPDATE_FAIL') {
				alert('댓글 수정에 실패했습니다.');
			}	
			else if (code === 'COMMENT_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'COMMENT_UNAUTHORIZED_ACCESS') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alert('해당 댓글을 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부 오류가 발생했습니다.');
			}
			else {
				alert(message);
			}
		}
			
	});
});





/**
 * 댓글을 삭제하는 함수
 */
async function deleteComment(id) {

	if (confirm('작성하신 댓글을 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');
			
			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
			
			const data = {
				id: id
			};
			
			const response = await instance.delete(`/comments/${id}`, {
				data: data,
				headers: headers
			});
			
			if (response.data.code === 'COMMENT_DELETE_SUCCESS') {
				const commentEl = document.querySelector(`.mycomment_item[data-id='${id}']`);
				if (commentEl) commentEl.remove();
				
				const subcommentEl = document.querySelectorAll(`.mycomment_item[data-comm-id='${id}']`);
				if (subcommentEl) subcommentEl.forEach(el => el.remove());
				
				commentList = commentList.filter(c => c.id !== id && c.comm_id !== id);
			}
			
		}
		catch (error) {
			const code = error?.response?.data?.code;
			const message = error?.response?.data?.message;
			
			if (code === 'COMMENT_DELETE_FAIL') {
				alert('댓글 삭제에 실패했습니다');
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'COMMENT_UNAUTHORIZED') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alert('해당 댓글을 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부 오류가 발생했습니다.');
			}
			else {
				alert(message);
			}
		}
	}
	
}


