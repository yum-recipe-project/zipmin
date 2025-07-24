/**
 * 댓글 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeCommentContent = document.getElementById('writeCommentContent');
	const commentContentBorder = document.querySelector('.comment_input');
	writeCommentContent.addEventListener('focus', function() {
		commentContentBorder.classList.add('focus');
	});
	writeCommentContent.addEventListener('blur', function() {
		commentContentBorder.classList.remove('focus');
	});
});





/**
 * 댓글 작성 폼값을 검증하고 버튼을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeCommentContent = document.getElementById('writeCommentContent');
	const writeCommentButton = document.querySelector('#writeCommentForm button[type="submit"]');
	writeCommentContent.addEventListener('input', function() {
		const isCommentContentEmpty = writeCommentContent.value.trim() === "";
		writeCommentButton.classList.toggle('disable', isCommentContentEmpty);
		writeCommentButton.disabled = isCommentContentEmpty;
	});
});





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
 * 댓글을 신고하는 모달창의 신고 사유를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reasonRadio = document.querySelectorAll('#reportCommentForm input[name="reason"]');
	const submitButton = document.querySelector('#reportCommentForm button[type="submit"]');

	reasonRadio.forEach(function(radio) {
        radio.addEventListener('change', function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle('disabled', !isChecked);
        });
    });
});





/**
 * 대댓글을 입력하는 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeSubcommentContent = document.getElementById('writeSubcommentContent');
	const writeSubcommentButton = document.querySelector('#writeSubcommentForm button[type="submit"]');
	writeSubcommentContent.addEventListener('input', function() {
		const isWriteSubcommentContentEmpty = writeSubcommentContent.value.trim() === '';
		writeSubcommentButton.classList.toggle('disabled', isWriteSubcommentContentEmpty);
    });
});





/**
 * 로그인 여부에 따라 댓글 작성폼을 다르게 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', async function () {
	
	if (isLoggedIn()) {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		document.getElementById('logout_state').style.display = 'none';
		document.getElementById('login_state').style.display = 'block';
		document.querySelector('.login_user span').innerText = payload.nickname;
	}
	else {
		document.getElementById('logout_state').style.display = 'block';
		document.getElementById('login_state').style.display = 'none';
	}

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		console.log(error);
	}
	
});





/**
 * 서버에서 댓글 데이터를 가져오는 함수
 */
let totalPages = 0;
let page = 0;
let commentList = [];

async function fetchCommentList(tablename, sort, size) {

	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const params = new URLSearchParams({
			tablename : tablename,
			recodenum : id,
			sort : sort,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/comments?${params}`, {
			method: 'GET'
		});
		
		const result = await response.json();
		
		if (result.code === 'COMMENT_READ_LIST_SUCCESS') {
			const newCommentList = result.data.content;
			
			commentList = [...commentList, ...newCommentList];
			newCommentList.filter(comment => comment.comm_id === null)
				.forEach(comment => {
					document.querySelector('.comment_list').append(renderComment(comment));
			});
			
			page = result.data.number + 1;
			totalPages = result.data.totalPages;
					
			document.querySelector('.comment_count span:last-of-type').innerText = result.data.totalElements;
			
			// 더보기 버튼 제어
			document.querySelector('.btn_more').style.display = page >= totalPages ? 'none' : 'block';
		}
		else if (result.code === 'COMMENT_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'INTERVAL_SERVER_ERROR') {
			alert('서버 내부 오류가 발생했습니다.');
		}
		
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 댓글을 화면에 렌더링하는 함수
 *
 * @param {Object} comment - 댓글 객체
 * @returns {string} 댓글의 HTML 문자열
 */
function renderComment(comment) {
	const subcommentList = commentList.filter(data => data.comm_id === comment.id);

	const commentLi = document.createElement('li');
	commentLi.className = 'comment';
	commentLi.dataset.id = comment.id;

	const infoDiv = document.createElement('div');
	infoDiv.className = 'comment_info';
	infoDiv.append(
		renderWriterInfo(comment.nickname, comment.postdate, false),
		renderActionLink(comment.id, comment.content, comment.user_id, false)
	);

	const contentP = document.createElement('p');
	contentP.className = 'comment_content';
	contentP.textContent = comment.content;

	const toolDiv = document.createElement('div');
	toolDiv.className = 'comment_tool';
	toolDiv.appendChild(renderLikeButton(comment.id, comment.likecount, comment.likestatus));

	const replyBtn = document.createElement('a');
	replyBtn.className = 'btn_outline_small write_subcomment_btn';
	replyBtn.dataset.bsToggle = 'modal';
	replyBtn.dataset.bsTarget = '#writeSubcommentModal';
	replyBtn.href = 'javascript:void(0);';
	replyBtn.addEventListener('click', function () {
		if (!isLoggedIn()) {
			redirectToLogin();
			bootstrap.Modal.getInstance(document.getElementById('writeSubcommentModal')).hide();
			return;
		}
		document.getElementById('writeSubcommentCommId').value = comment.id;
	});

	const replySpan = document.createElement('span');
	replySpan.textContent = '답글 쓰기';
	replyBtn.appendChild(replySpan);

	toolDiv.appendChild(replyBtn);

	commentLi.append(infoDiv, contentP, toolDiv);

	const subList = document.createElement('ul');
	subList.className = 'subcomment_list';

	subcommentList.forEach(subcomment => {
		const subLi = renderSubcomment(subcomment);
		subList.appendChild(subLi);
	});

	const fragment = document.createDocumentFragment();
	fragment.append(commentLi, subList);

	return fragment;
}





/**
 * 좋아요 버튼을 생성하는 함수
 */
function renderLikeButton(id, likecount, likestatus) {
	
	const button = document.createElement('button');
	button.className = 'btn_tool like_btn';

	const img = document.createElement('img');
	img.src = likestatus ? '/images/common/thumb_up_full.png' : '/images/common/thumb_up_empty.png';

	const count = document.createElement('p');
	count.textContent = likecount;

	button.append(img, count);

	// 좋아요 버튼 동작
	button.addEventListener('click', async function () {
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 좋아요 취소
		if (likestatus) {
			try {
				const token = localStorage.getItem('accessToken');
				
				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				};
				
				const data = {
					tablename: 'comments',
					recodenum: id,
				}
				
				const response = await instance.delete(`/comments/${id}/likes`, {
					data: data,
					headers: headers
				});
				
				if (response.data.code === 'COMMENT_UNLIKE_SUCCESS') {
					likestatus = false;
					img.src = '/images/common/thumb_up_empty.png';
					count.textContent = Number(count.textContent) - 1;
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;
				
				if (code === 'COMMENT_UNLIKE_FAIL') {
					alert('댓글 좋아요 취소에 실패했습니다.');
				}
				else if (code === 'LIKE_DELETE_FAIL') {
					alert('좋아요 삭제에 실패했습니다');
				}
				else if (code === 'LIKE_INVALID_INPUT') {
					alert('입력값이 유효하지 않습니다.');
				}
				else if (code === 'LIKE_NOT_FOUND') {
					alert('해당 좋아요를 찾을 수 없습니다');
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
					alert('서버 내부에서 오류가 발생했습니다.');
				}
				else {
					alert(message);
				}
			}
		}
		// 좋아요
		else {
			try {
				const token = localStorage.getItem('accessToken');
				
				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}
				
				const data = {
					tablename: 'comments',
					recodenum: id
				}
				
				const response = await instance.post(`/comments/${id}/likes`, data, {
					headers: headers
				});
				
				if (response.data.code === 'COMMENT_LIKE_SUCCESS') {
					likestatus = true;
					img.src = '/images/common/thumb_up_full.png';
					count.textContent = Number(count.textContent) + 1;
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				const message = error?.response?.data?.message;
				
				if (code === 'COMMENT_LIKE_FAIL') {
					alert('댓글 좋아요에 실패했습니다.');
				}
				else if (code === 'LIKE_CREATE_FAIL') {
					alert('댓글 생성에 실패했습니다.');
				}
				else if (code === 'LIKE_INVALID_INPUT') {
					alert('입력값이 유효하지 않습니다.');
				}
				else if (code === 'LIKE_DUPLICATE') {
					alert('이미 중복된 내용입니다.');
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
	});

	return button;
}





/**
 * 수정과 신고와 삭제 동작의 링크를 생성하는 함수
 */
function renderActionLink(id, content, userId, isSub) {

	const token = localStorage.getItem('accessToken');
	
	const actionDiv = document.createElement('div');
	actionDiv.className = isSub ? 'subcomment_action' : 'comment_action';

	if (token && parseJwt(token).id === userId) {
		const editLink = document.createElement('a');
		editLink.href = 'javascript:void(0);';
		editLink.dataset.bsToggle = 'modal';
		editLink.dataset.bsTarget = '#editCommentModal';
		editLink.textContent = '수정';
		editLink.addEventListener('click', () => {
			if (!isLoggedIn()) {
				redirectToLogin();
				bootstrap.Modal.getInstance(document.getElementById('reportCommentModal')).hide();
				return;
			}
			document.getElementById('editCommentContent').value = content;
			document.getElementById('editCommentId').value = id;
		});
	
		const deleteLink = document.createElement('a');
		deleteLink.href = 'javascript:void(0);';
		deleteLink.textContent = '삭제';
		deleteLink.addEventListener('click', async () => {
			if (!isLoggedIn()) {
				redirectToLogin();
			}
			await deleteComment(id);
		});
		
		actionDiv.append(editLink, deleteLink);
	}
	
	const reportLink = document.createElement('a');
	reportLink.href = 'javascript:void(0);';
	reportLink.textContent = '신고';
	reportLink.dataset.bsToggle = 'modal';
	reportLink.dataset.bsTarget = '#reportCommentModal';
	
	reportLink.addEventListener('click', () => {

		if (!isLoggedIn()) {
			redirectToLogin();
			bootstrap.Modal.getInstance(document.getElementById('reportCommentModal')).hide();
			return;
		}
		document.getElementById('reportCommentId').value = id;
	});
	
	actionDiv.append(reportLink);

	return actionDiv;
}





/**
 * 댓글과 대댓글의 작성자 정보 영역을 생성하는 함수
 */
function renderWriterInfo(nickname, postdate, isSub) {
	
	const date = new Date(postdate);
	const formatDate = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
	
	const wrapperDiv = document.createElement('div');
	wrapperDiv.className = isSub ? 'subcomment_writer' : 'comment_writer';

	const img = document.createElement('img');
	img.src = '/images/common/test.png';

	const nameSpan = document.createElement('span');
	nameSpan.textContent = nickname;

	const dateSpan = document.createElement('span');
	dateSpan.textContent = formatDate;

	wrapperDiv.append(img, nameSpan, dateSpan);
	return wrapperDiv;
}





/**
 * 대댓글을 생성하는 함수
 */
function renderSubcomment(subcomment) {
	const subLi = document.createElement('li');
	subLi.className = 'subcomment';
	subLi.dataset.id = subcomment.id;

	const arrowImg = document.createElement('img');
	arrowImg.className = 'subcomment_arrow';
	arrowImg.src = '/images/chompessor/arrow_right.png';

	const innerDiv = document.createElement('div');
	innerDiv.className = 'subcomment_inner';

	const infoDiv = document.createElement('div');
	infoDiv.className = 'subcomment_info';
	infoDiv.append(
		renderWriterInfo(subcomment.nickname, subcomment.postdate, true),
		renderActionLink(subcomment.id, subcomment.content, subcomment.user_id, true)
	);

	const contentP = document.createElement('p');
	contentP.className = 'subcomment_content';
	contentP.textContent = subcomment.content;

	const toolDiv = document.createElement('div');
	toolDiv.className = 'comment_tool';
	toolDiv.appendChild(renderLikeButton(subcomment.id, subcomment.likecount, subcomment.likestatus));

	innerDiv.append(infoDiv, contentP, toolDiv);
	subLi.append(arrowImg, innerDiv);

	return subLi;
}





/**
 * 댓글을 작성하는 함수
 */
async function writeComment(tablename) {
	
	if (!isLoggedIn()) {
		redirectToLogin();
	}
	
	try {
		const token = localStorage.getItem('accessToken');
		
		const params = new URLSearchParams(window.location.search);
		const id = params.get('id');
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		}
		
		const data = {
			content: document.getElementById('writeCommentContent').value.trim(),
			tablename: tablename,
			recodenum: Number(id)
		};
		
		const response = await instance.post('/comments', data, {
			headers: headers
		});
		
		if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
			const newComment = response.data.data;
			
			document.getElementById("writeCommentContent").value = '';
			const submitBtn = document.querySelector("#writeCommentForm button[type='submit']");
			submitBtn.disabled = true;
			submitBtn.classList.add('disable');

			commentList.unshift(newComment);

			const commentEl = renderComment(newComment);
			document.querySelector('.comment_list').prepend(commentEl);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		const message = error?.response?.data?.message;
		
		if (code === 'COMMENT_CREATE_FAIL') {
			alert('댓글 생성에 실패했습니다.');
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




/**
 * 댓글을 신고하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const reportForm = document.getElementById('reportCommentForm');
	
	reportForm.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}
		
		const commId = document.getElementById('reportCommentId').value;
		const reason = document.querySelector('input[name="reason"]:checked')?.value;
		
		try {
			const token = localStorage.getItem('accessToken');
			
			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const data = {
				tablename: 'comments',
				recodenum: commId,
				reason: reason
			};
			
			const response = await instance.post(`/comments/${commId}/reports`, data, {
				headers: headers
			});
			
			if (response.data.code === 'COMMENT_REPORT_SUCCESS') {
				alert('신고 처리되었습니다.');
			}
			
		}
		catch (error) {
			const code = error?.response?.data?.code;
			const message = error?.response?.data?.message;

			if (code === 'COMMENT_REPORT_FAIL') {
				alert('댓글 신고에 실패했습니다.');
			}
			else if (code === 'REPORT_CREATE_FAIL') {
				alert('신고 생성에 실패했습니다.');
			}
			else if (code === 'REPORT_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'REPORT_DUPLICATE') {
				alert('이미 신고한 댓글입니다.');
			}
			else if (code === 'COMMENT_UNAUTHORIZED_ACCESS') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alert('댓글을 찾을 수 없습니다.');
			}
			else {
				alert(message);
			}
		}
		
		bootstrap.Modal.getInstance(document.getElementById('reportCommentModal')).hide();
	});
});





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
				const contentEl = document.querySelector(`.comment[data-id='${id}'] .comment_content, .subcomment[data-id='${id}'] .subcomment_content`);
				if (contentEl) {
				  contentEl.textContent = response.data.data.content;
				}
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
				const commentEl = document.querySelector(`.comment[data-id='${id}']`);
				const subcommentListEl = commentEl?.nextElementSibling;

				if (commentEl) {
					commentEl.remove();
				}

				if (subcommentListEl && subcommentListEl.classList.contains('subcomment_list')) {
					subcommentListEl.remove();
				}

				const subcommentEl = document.querySelector(`.subcomment[data-id='${id}']`);
				if (subcommentEl) {
					subcommentEl.remove();
				}
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





/**
 * 대댓글을 작성하는 함수
 */
async function writeSubcomment(tablename) {

	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);

		const params = new URLSearchParams(window.location.search);
		const id = params.get('id');
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		}

		const data = {
			content: document.getElementById('writeSubcommentContent').value.trim(),
			tablename: tablename,
			recodenum: Number(id),
			comm_id: document.getElementById('writeSubcommentCommId').value
		};

		const response = await instance.post('/comments', data, {
			headers: headers
		});

		if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
			const newSubcomment = response.data.data;
			const parentCommentId = newSubcomment.comm_id;

			bootstrap.Modal.getInstance(document.getElementById('writeSubcommentModal')).hide();

			document.getElementById("writeSubcommentContent").value = '';

			commentList.push(newSubcomment);

			const subList = document.querySelector(`.comment[data-id='${parentCommentId}']`).nextElementSibling;
			subList.appendChild(renderSubcomment(newSubcomment));
		}
	}
	catch (error) {
		console.log(error);
		const code = error?.response?.data?.code;
		const message = error?.response?.data?.message;

		if (code === 'COMMENT_CREATE_FAIL') {
			alert('댓글 생성에 실패했습니다.');
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












