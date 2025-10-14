/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 15;
let tablename = '';
let sort = '';
let commentList = [];





/**
 * 댓글 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 댓글을 작성하는 폼값 검증 및 포커스
	const writeCommentContent = document.getElementById('writeCommentContent');
	const commentContentBorder = document.querySelector('.comment_input');
	const writeCommentButton = document.querySelector('#writeCommentForm button[type="submit"]');
	writeCommentContent.addEventListener('focus', function() {
		commentContentBorder.classList.add('focus');
	});
	writeCommentContent.addEventListener('blur', function() {
		commentContentBorder.classList.remove('focus');
	});
	writeCommentContent.addEventListener('input', function() {
		const isCommentContentEmpty = writeCommentContent.value.trim() === '';
		writeCommentButton.classList.toggle('disable', isCommentContentEmpty);
		writeCommentButton.disabled = isCommentContentEmpty;
	});
	
	// 댓글을 수정하는 모달창의 폼값 검증
	const editCommentContent = document.getElementById('editCommentContent');
	const editCommentButton = document.querySelector('#editCommentForm button[type="submit"]');
	editCommentContent.addEventListener('input', function() {
		const isCommentContentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle('disabled', isCommentContentEmpty);
	});
	
	// 댓글을 신고하는 모달창의 폼값 검증
	const reasonRadio = document.querySelectorAll('#reportCommentForm input[name="reason"]');
	const submitButton = document.querySelector('#reportCommentForm button[type="submit"]');
	reasonRadio.forEach(function(radio) {
        radio.addEventListener('change', function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle('disabled', !isChecked);
        });
    });
	
	// 대댓글을 작성하는 모달창의 폼값 검증
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
		const payload = parseJwt(localStorage.getItem('accessToken'));
		document.getElementById('login_state').style.display = 'block';
		document.getElementById('logout_state').style.display = 'none';
		document.getElementById('writeCommentAvatar').src = payload.avatar;
		document.getElementById('writeCommentAvatar').addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${payload.id}`;
		});
		document.getElementById('writeCommentNickname').innerText = payload.nickname;
		document.getElementById('writeCommentNickname').addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${payload.id}`;
		});
	}
	else {
		document.getElementById('login_state').style.display = 'none';
		document.getElementById('logout_state').style.display = 'block';
	}

});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 댓글 수정 모달
	const editCommentModal = document.getElementById('editCommentModal');
	editCommentModal.addEventListener('hidden.bs.modal', function() {
	    const form = document.getElementById('editCommentForm');
		
	    if (form) {
	        form.reset();
	    }
		
	    const textarea = editCommentModal.querySelector('textarea');
	    if (textarea) {
	        textarea.value = '';
	    }
		
		const submitBtn = form?.querySelector('button[type="submit"]');
		if (submitBtn) {
			submitBtn.classList.add('disabled');
		}
	});
	
	// 대댓글 작성 모달
	const writeSubcommentModal = document.getElementById('writeSubcommentModal');
	writeSubcommentModal.addEventListener('hidden.bs.modal', function() {
	    const form = document.getElementById('writeSubcommentForm');
		
	    if (form) {
	        form.reset();
	    }
		
	    const textarea = writeSubcommentModal.querySelector('textarea');
	    if (textarea) {
	        textarea.value = '';
	    }
		
		const submitBtn = form?.querySelector('button[type="submit"]');
		if (submitBtn) {
			submitBtn.classList.add('disabled');
		}
	});
	
	// 댓글 신고 모달
	const reportCommentModal = document.getElementById('reportCommentModal');
	reportCommentModal.addEventListener('hidden.bs.modal', function() {
		const form = document.getElementById('reportCommentForm');
		
		if (form) {
			form.reset();
		}
		
		reportCommentModal.querySelectorAll('input[name="reason"]').forEach(radio => {
			radio.checked = false;
		});
		
		const submitBtn = reportCommentModal.querySelector('button[type="submit"]');
		if (submitBtn) {
			submitBtn.classList.add('disabled');
		}
	});

});






/**
 * 쩝쩝박사 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 요청명에 따라 tablename 설정
	const pathname = location.pathname;
	const match = pathname.match(/\/view([A-Za-z]+)\.do$/i);
	tablename = match ? match[1].toLowerCase() : null;
	
	// 정렬 버튼
	document.querySelectorAll('.comment_order .btn_sort_small').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			document.querySelector('.comment_order .btn_sort_small.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			commentList = [];
			
			fetchCommentList();
		});
	});
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		page = page + 1;
		fetchCommentList();
	});
	
	fetchCommentList();
});





/**
 * 서버에서 댓글 목록 데이터를 가져오는 함수
 */
async function fetchCommentList() {
	
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
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'COMMENT_READ_LIST_SUCCESS') {
			
			// 전역변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			commentList = [...commentList, ...result.data.content];
			
			// 렌더링
			renderCommentList(commentList);
			document.querySelector('.comment_count span:last-of-type').innerText = totalElements;
			
			// 더보기 버튼 제어
			document.querySelector('.btn_more').style.display = page >= totalPages - 1 ? 'none' : 'block';
			
			// TODO : render 함수로 옮기기
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.comment_list').style.display = 'none';
				document.querySelector('.list_empty')?.remove();
				const content = document.querySelector('.comment_write');
				content.insertAdjacentElement('afterend', renderListEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.list_empty')?.remove();
				document.querySelector('.comment_list').style.display = '';
			}
		}
		else if (result.code === 'COMMENT_READ_LIST_FAIL') {
			alertDanger('댓글 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'LIKE_COUNT_FAIL') {
			alertDanger('댓글 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'REPORT_COUNT_FAIL') {
			alertDanger('댓글 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'LIKE_EXIST_FAIL') {
			alertDanger('댓글 목록 조회에 실패했습니다.');
		}
		else if (result.code === 'COMMENT_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'LIKE_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'REPORT_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alert('해당 사용자를 찾을 수 없습니다.');
		}
		else if (result.code === 'INTERVAL_SERVER_ERROR') {
			alert('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 댓글 목록을 화면에 렌더링하는 함수
 */
function renderCommentList(commentList) {
	
	const container = document.querySelector('.comment_list');
	container.innerHTML = '';
	
	commentList.forEach(comment => {
		const commentLi = document.createElement('li');
		commentLi.className = 'comment';
		commentLi.dataset.id = comment.id;
		
		// 상단 정보
		const infoDiv = document.createElement('div');
		infoDiv.className = 'comment_info';
		
		// 작성자 정보
		const writerDiv = document.createElement('div');
		writerDiv.className = 'comment_writer';
		const avatarImg = document.createElement('img');
		avatarImg.src = comment.avatar;
		avatarImg.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${comment.user_id}`;
		});
		const nameSpan = document.createElement('span');
		nameSpan.textContent = comment.nickname;
		nameSpan.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${comment.user_id}`;
		});
		const dateSpan = document.createElement('span');
		dateSpan.textContent = formatDate(comment.postdate);
		writerDiv.append(avatarImg, nameSpan, dateSpan);
		
		// 기능 버튼
		const actionDiv = document.createElement('div');
		actionDiv.className = 'comment_action';
		
		const payload = isLoggedIn() ? getPayload() : null;
		const canAction =
			payload?.role === 'ROLE_SUPER_ADMIN' ||
			(payload?.role === 'ROLE_ADMIN' && comment.role === 'ROLE_USER') ||
			(payload?.id === comment.user_id);
		
		if (canAction) {
			// 수정 버튼
			const editLink = document.createElement('a');
			editLink.href = 'javascript:void(0);';
			editLink.dataset.bsToggle = 'modal';
			editLink.dataset.bsTarget = '#editCommentModal';
			editLink.textContent = '수정';
			editLink.addEventListener('click', function(event) {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					bootstrap.Modal.getInstance(document.getElementById('editCommentModal'))?.hide();
					return;
				}
				document.getElementById('editCommentContent').value = comment.content;
				document.getElementById('editCommentId').value = comment.id;
			});

			// 삭제 버튼
			const deleteLink = document.createElement('a');
			deleteLink.href = 'javascript:void(0);';
			deleteLink.textContent = '삭제';
			deleteLink.addEventListener('click', function(event) {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					return;
				}
				deleteComment(comment.id);
			});
			
			actionDiv.append(editLink, deleteLink);
		}

		// 신고 버튼
		const reportLink = document.createElement('a');
		reportLink.href = 'javascript:void(0);';
		reportLink.textContent = '신고';
		reportLink.dataset.bsToggle = 'modal';
		reportLink.dataset.bsTarget = '#reportCommentModal';
		reportLink.addEventListener('click', function(event) {
			if (!isLoggedIn()) {
				event.preventDefault();
				redirectToLogin();
				bootstrap.Modal.getInstance(document.getElementById('reportCommentModal'))?.hide();
				return;
			}
			document.getElementById('reportCommentId').value = comment.id;
		});
		actionDiv.append(reportLink);
		
		infoDiv.append(writerDiv, actionDiv);
		
		// 본문
		const contentP = document.createElement('p');
		contentP.className = 'comment_content';
		contentP.textContent = comment.content;
		
		// 툴바
		const toolDiv = document.createElement('div');
		toolDiv.className = 'comment_tool';
		toolDiv.appendChild(renderLikeButton(comment.id, comment.likecount, comment.liked));
		
		// 대댓글 작성 버튼
		const replyBtn = document.createElement('a');
		replyBtn.className = 'btn_outline_small write_subcomment_btn';
		replyBtn.dataset.bsToggle = 'modal';
		replyBtn.dataset.bsTarget = '#writeSubcommentModal';
		replyBtn.addEventListener('click', function (event) {
			if (!isLoggedIn()) {
				event.preventDefault();
				redirectToLogin();
				bootstrap.Modal.getInstance(document.getElementById('writeSubcommentModal'))?.hide();
				return;
			}
			document.getElementById('writeSubcommentCommId').value = comment.id;
		});
		const replySpan = document.createElement('span');
		replySpan.textContent = '답글 쓰기';
		replyBtn.appendChild(replySpan);
		toolDiv.appendChild(replyBtn);
	
		commentLi.append(infoDiv, contentP, toolDiv);
	
		container.appendChild(commentLi);
		container.appendChild(renderSubcommentList(comment.subcomment_list));
	});
}





/**
 * 대댓글을 생성하는 함수
 */
function renderSubcommentList(subcommentList) {
	
	const subcommentUl = document.createElement('ul');
	subcommentUl.className = 'subcomment_list';
	
	subcommentList.forEach(subcomment => {
		const subcommentLi = document.createElement('li');
		subcommentLi.className = 'subcomment';
		subcommentLi.dataset.id = subcomment.id;
		subcommentLi.dataset.commId = subcomment.comm_id;
	
		const arrowImg = document.createElement('img');
		arrowImg.className = 'subcomment_arrow';
		arrowImg.src = '/images/chompessor/arrow_right.png';
	
		const innerDiv = document.createElement('div');
		innerDiv.className = 'subcomment_inner';
	
		// 상단 정보
		const infoDiv = document.createElement('div');
		infoDiv.className = 'subcomment_info';
		
		// 작성자 정보
		const writerDiv = document.createElement('div');
		writerDiv.className = 'subcomment_writer';
		const avatarImg = document.createElement('img');
		avatarImg.src = subcomment.avatar;
		avatarImg.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${subcomment.user_id}`;
		});
		const nameSpan = document.createElement('span');
		nameSpan.textContent = subcomment.nickname;
		nameSpan.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${subcomment.user_id}`;
		});
		const dateSpan = document.createElement('span');
		dateSpan.textContent = formatDate(subcomment.postdate);
		writerDiv.append(avatarImg, nameSpan, dateSpan);
		
		// 기능 버튼
		const actionDiv = document.createElement('div');
		actionDiv.className = 'subcomment_action';

		const payload = isLoggedIn() ? getPayload() : null;
		const canAction =
			payload?.role === 'ROLE_SUPER_ADMIN' ||
			(payload?.role === 'ROLE_ADMIN' && subcomment.role === 'ROLE_USER') ||
			(payload?.id === subcomment.user_id);

		if (canAction) {
			// 수정 버튼
			const editLink = document.createElement('a');
			editLink.href = 'javascript:void(0);';
			editLink.dataset.bsToggle = 'modal';
			editLink.dataset.bsTarget = '#editCommentModal';
			editLink.textContent = '수정';
			editLink.addEventListener('click', function(event) {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					bootstrap.Modal.getInstance(document.getElementById('editCommentModal'))?.hide();
					return;
				}
				document.getElementById('editCommentContent').value = subcomment.content;
				document.getElementById('editCommentId').value = subcomment.id;
			});

			// 삭제 버튼
			const deleteLink = document.createElement('a');
			deleteLink.href = 'javascript:void(0);';
			deleteLink.textContent = '삭제';
			deleteLink.addEventListener('click', function(event) {
				if (!isLoggedIn()) {
					event.preventDefault();
					redirectToLogin();
					return;
				}
				deleteComment(subcomment.id);
			});
			
			actionDiv.append(editLink, deleteLink);
		}

		// 신고 버튼
		const reportLink = document.createElement('a');
		reportLink.href = 'javascript:void(0);';
		reportLink.textContent = '신고';
		reportLink.dataset.bsToggle = 'modal';
		reportLink.dataset.bsTarget = '#reportCommentModal';
		reportLink.addEventListener('click', function(event) {
			if (!isLoggedIn()) {
				event.preventDefault();
				redirectToLogin();
				bootstrap.Modal.getInstance(document.getElementById('reportCommentModal'))?.hide();
				return;
			}
			document.getElementById('reportCommentId').value = subcomment.id;
		});
		actionDiv.append(reportLink);

		infoDiv.append(writerDiv, actionDiv);
	
		// 본문
		const contentP = document.createElement('p');
		contentP.className = 'subcomment_content';
		contentP.textContent = subcomment.content;
	
		// 툴바
		const toolDiv = document.createElement('div');
		toolDiv.className = 'comment_tool';
		toolDiv.appendChild(renderLikeButton(subcomment.id, subcomment.likecount, subcomment.liked));
	
		innerDiv.append(infoDiv, contentP, toolDiv);
		subcommentLi.append(arrowImg, innerDiv);
	
		subcommentUl.appendChild(subcommentLi);
		
	});
	
	return subcommentUl;
}





/**
 * 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'list_empty';
	
    const span = document.createElement('span');
    span.textContent = '작성된 댓글이 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}





/**
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	document.getElementById('writeCommentForm').addEventListener('submit', async function (event) {
		event.preventDefault();
		
		// 댓글 작성
		try {
			const id = new URLSearchParams(window.location.search).get('id');
			
			const data = {
				content: document.getElementById('writeCommentContent').value.trim(),
				tablename: tablename,
				recodenum: Number(id)
			}; 
			
			const response = await instance.post('/comments', data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
				alertPrimary('댓글 작성에 성공했습니다.');
				
				document.getElementById("writeCommentContent").value = '';
				const submitBtn = document.querySelector("#writeCommentForm button[type='submit']");
				submitBtn.disabled = true;
				submitBtn.classList.add('disable');
				
				page = 0;
				commentList = [];
				fetchCommentList();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'COMMENT_CREATE_FAIL') {
				alertDanger('댓글 작성에 실패했습니다.');
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
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
			else if (code === 'USER_NOT_FOUND') {
				alert('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	});
	
});





/**
 * 대댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	document.getElementById('writeSubcommentForm').addEventListener('submit', async function (event) {
		event.preventDefault();
		
		// 대댓글 작성
		try {
			const id = new URLSearchParams(window.location.search).get('id');

			const data = {
				content: document.getElementById('writeSubcommentContent').value.trim(),
				tablename: tablename,
				recodenum: Number(id),
				comm_id: document.getElementById('writeSubcommentCommId').value
			};
	
			const response = await instance.post('/comments', data, {
				headers: getAuthHeaders()
			});
	
			if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
				alertPrimary('댓글이 성공적으로 작성되었습니다.');
				bootstrap.Modal.getInstance(document.getElementById('writeSubcommentModal'))?.hide();
				
				const lastVisiblePage = page;
				let maxPages;
				
				page = 0;
				commentList = [];
				await fetchCommentList();
				maxPages = totalPages;
				
				const end = Math.min(lastVisiblePage, maxPages - 1);
				for (let p = 1; p <= end; p++) {
					page = p;
					await fetchCommentList();
				}
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'COMMENT_CREATE_FAIL') {
				alertDanger('댓글 작성에 실패했습니다.');
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
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
			else if (code === 'USER_NOT_FOUND') {
				alert('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	});
	
});





/**
 * 댓글을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const editForm = document.getElementById('editCommentForm');
	editForm.addEventListener('submit', async function(event) {
		event.preventDefault();

		const id = document.getElementById('editCommentId').value;
		const content = document.getElementById('editCommentContent').value.trim();

		try {
			const data = {
				id: id,
				content: content
			};
			
			const response = await instance.patch(`/comments/${id}`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'COMMENT_UPDATE_SUCCESS') {
				alertPrimary('댓글이 성공적으로 수정되었습니다.');
				
				if (document.querySelector(`.comment[data-id='${id}'] .comment_content`)) {
					document.querySelector(`.comment[data-id='${id}'] .comment_content`).textContent = response.data.data.content;
				}
				if (document.querySelector(`.subcomment[data-id='${id}'] .subcomment_content`)) {
					document.querySelector(`.subcomment[data-id='${id}'] .subcomment_content`).textContent = response.data.data.content;
				}
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'COMMENT_UPDATE_FAIL') {
				alertDanger('댓글 수정에 실패했습니다.');
			}	
			else if (code === 'COMMENT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
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
			else if (code === 'USER_NOT_FOUND') {
				alert('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
		
		bootstrap.Modal.getInstance(document.getElementById('editCommentModal'))?.hide();
	});
});





/**
 * 댓글을 삭제하는 함수
 */
async function deleteComment(id) {

	if (confirm('작성하신 댓글을 삭제하시겠습니까?')) {
		try {
			const response = await instance.delete(`/comments/${id}`, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'COMMENT_DELETE_SUCCESS') {
				alertPrimary('댓글을 성공적으로 삭제했습니다.');

				// 댓글 제거
				if (document.querySelector(`.comment[data-id='${id}']`)) {
					document.querySelector(`.comment[data-id='${id}']`).remove();
					totalElements--;
					document.querySelector('.comment_count span:last-of-type').innerText = totalElements;
				}
				document.querySelectorAll(`.subcomment[data-comm-id='${id}']`)
					.forEach(subcomment => subcomment.remove());

				// 대댓글 제거
				document.querySelector(`.subcomment[data-id='${id}']`)?.remove();
				
				commentList = commentList.filter(comment => comment.id !== id && comment.comm_id !== id);
			}
			
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'COMMENT_DELETE_FAIL') {
				alertDanger('댓글 삭제에 실패했습니다');
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'COMMENT_UNAUTHORIZED') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alertDanger('해당 댓글을 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}
	
}






/**
 * 좋아요 버튼을 생성하는 함수
 */
function renderLikeButton(id, likecount, isLiked) {
	
	// 좋아요 버튼
	const button = document.createElement('button');
	button.className = 'btn_tool like_btn';
	const img = document.createElement('img');
	img.src = isLiked ? '/images/common/thumb_up_full.png' : '/images/common/thumb_up_empty.png';
	const count = document.createElement('p');
	count.textContent = likecount;
	button.append(img, count);

	// 좋아요 버튼 동작
	button.addEventListener('click', async function() {
		
		if (!isLoggedIn()) {
			redirectToLogin();
			return;
		}
		
		// 좋아요 취소
		if (isLiked) {
			try {
				const data = {
					tablename: 'comments',
					recodenum: id,
				}
				
				const response = await instance.delete(`/comments/${id}/likes`, {
					data: data,
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'COMMENT_UNLIKE_SUCCESS') {
					isLiked = false;
					img.src = '/images/common/thumb_up_empty.png';
					count.textContent = Number(count.textContent) - 1;
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				if (code === 'COMMENT_UNLIKE_FAIL') {
					alertDanger('댓글 좋아요 취소에 실패했습니다.');
				}
				else if (code === 'LIKE_DELETE_FAIL') {
					alertDanger('좋아요 삭제에 실패했습니다');
				}
				else if (code === 'COMMENT_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'LIKE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'COMMENT_UNAUTHORIZED') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'LIKE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				}
				else if (code === 'LIKE_NOT_FOUND') {
					alertDanger('해당 좋아요를 찾을 수 없습니다');
				}
				else if (code === 'COMMENT_NOT_FOUND') {
					alertDanger('해당 댓글을 찾을 수 없습니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
		
		// 좋아요
		else {
			try {
				const data = {
					tablename: 'comments',
					recodenum: id
				}
				
				const response = await instance.post(`/comments/${id}/likes`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'COMMENT_LIKE_SUCCESS') {
					isLiked = true;
					img.src = '/images/common/thumb_up_full.png';
					count.textContent = Number(count.textContent) + 1;
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				if (code === 'COMMENT_LIKE_FAIL') {
					alertDanger('댓글 좋아요에 실패했습니다.');
				}
				else if (code === 'LIKE_CREATE_FAIL') {
					alertDanger('좋아요 작성에 실패했습니다.');
				}
				else if (code === 'COMMENT_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'LIKE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'COMMENT_UNAUTHORIZED') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'LIKE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				}
				else if (code === 'COMMENT_NOT_FOUND') {
					alertDanger('해당 댓글을 찾을 수 없습니다.');
				}
				else if (code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (code === 'LIKE_DUPLICATE') {
					alertDanger('이미 좋아요한 댓글입니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
	});

	return button;
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
			const data = {
				tablename: 'comments',
				recodenum: commId,
				reason: reason
			};
			
			const response = await instance.post(`/comments/${commId}/reports`, data, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'COMMENT_REPORT_SUCCESS') {
				alertPrimary('신고 처리되었습니다.');
			}
			
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'COMMENT_REPORT_FAIL') {
				alertDanger('댓글 신고에 실패했습니다.');
			}
			else if (code === 'REPORT_CREATE_FAIL') {
				alertDanger('신고 작성에 실패했습니다.');
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'REPORT_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'COMMENT_UNAUTHORIZED') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'REPORT_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alertDanger('해당 댓글을 찾을 수 없습니다.');
			}
			else if (code === 'USER_NOT_FOUND') {
				alertDanger('해당 사용자를 찾을 수 없습니다.');
			}
			else if (code === 'REPORT_DUPLICATE') {
				alertDanger('이미 신고한 댓글입니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부에서 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
		
		bootstrap.Modal.getInstance(document.getElementById('reportCommentModal'))?.hide();
	});
});