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
 * 전역 변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 15;
let commentList = [];





/**
 * 초기 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 더보기 버튼
	document.querySelector('.btn_more').addEventListener('click', function() {
		page = page + 1;
		fetchUserCommentList();
	});
	
	fetchUserCommentList();
});





/**
 * 서버에서 사용자 댓글 목록 데이터를 가져오는 함수
 */
async function fetchUserCommentList() {
	alert(page);
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const params = new URLSearchParams({
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/comments?${params}`);
		
		console.log(response);
		
		if (response.data.code === 'COMMENT_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			commentList = [...commentList, ...response.data.data.content];
			
			// 렌더링
			renderUserCommentList(commentList);
			document.querySelector('.mycomment_count span').innerText = totalElements + '개';
			document.querySelector('.btn_more').style.display = page >= totalPages - 1 ? 'none' : 'block';
		}
		
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		// TODO : 에러코드 추가
		console.log(error);
		
	}
	
}





/**
 * 댓글 목록을 화면에 렌더링하는 함수
 */
function renderUserCommentList(commentList) {
	
	const container = document.querySelector('.mycomment_list');
	container.innerHTML = '';
	
	const tablename = {
		vote: '투표',
		megazine: '매거진',
		event: '이벤트',
		recipe: '레시피',
		guide: '키친가이드'
	};
	
	// 사용자 댓글 목록이 존재하지 않는 경우
	if (commentList == null || commentList.length === 0) {
		container.style.display = 'none';
		document.querySelector('list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '작성한 댓글이 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 사용자 댓글 목록이 존재하는 경우
	commentList.forEach(comment => {
		container.style.disaply = 'block';
		document.querySelector('list_empty')?.remove();
		
		const li = document.createElement('li');
		li.className = 'mycomment_item';
		li.dataset.id = comment.id;
		li.dataset.commId = comment.comm_id;
		
		const titleDiv = document.createElement('div');
		titleDiv.className = 'mycomment_title';
		
		const a = document.createElement('a');
		switch (comment.tablename) {
			case 'vote':
				a.href = `/chompessor/viewVote.do?id=${comment.recodenum}`;
				break;
			case 'megazine':
				a.href = `/chompessor/viewMegazine.do?id=${comment.recodenum}`;
				break;
			case 'event':
				a.href = `/chompessor/viewEvent.do?id=${comment.recodenum}`;
				break;
			case 'recipe':
				a.href = `/recipe/viewRecipe.do?id=${comment.recodenum}`;
				break;
			case 'guide':
				a.href = `/kitchen/viewGuide.do?id=${comment.recodenum}`;
				break;
			default:
				break;
		}
		a.textContent = comment.title;
		titleDiv.appendChild(a);
		
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
		// TODO : 프로필로 이동
		
		const img = document.createElement('img');
		img.src = comment.avatar;
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
		
		container.appendChild(li);
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
				
				// TODO : fetch!!
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
			const data = {
				id: id
			};
			
			const response = await instance.delete(`/comments/${id}`, {
				data: data,
				headers: getAuthHeaders()
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
