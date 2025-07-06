/**
 * 댓글 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeCommentContent = document.getElementById("writeCommentContent");
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
	const writeCommentContent = document.getElementById("writeCommentContent");
	const writeCommentButton = document.querySelector("#writeCommentForm button[type='submit']");
	writeCommentContent.addEventListener("input", function() {
		const isCommentContentEmpty = writeCommentContent.value.trim() === "";
		writeCommentButton.classList.toggle("disable", isCommentContentEmpty);
		writeCommentButton.disabled = isCommentContentEmpty;
	});
});



/**
 * 댓글을 수정하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContent = document.getElementById("editCommentContent");
	const editCommentButton = document.querySelector("#editCommentForm button[type='submit']");
	editCommentContent.addEventListener("input", function() {
		const isCommentContentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle("disabled", isCommentContentEmpty);
	});
});



/**
 * 댓글을 신고하는 모달창의 신고 사유를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reasonRadio = document.querySelectorAll("#reportCommentForm input[name='reason']");
	const submitButton = document.querySelector("#reportCommentForm button[type='submit']");

	reasonRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
});



/**
 * 대댓글을 입력하는 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeSubcommentContent = document.getElementById("writeSubcommentContent");
	const writeSubcommentButton = document.querySelector("#writeSubcommentForm button[type='submit']");
	writeSubcommentContent.addEventListener("input", function() {
		const isWriteSubcommentContentEmpty = writeSubcommentContent.value.trim() === "";
		writeSubcommentButton.classList.toggle("disabled", isWriteSubcommentContentEmpty);
    });
});



/**
 * 로그인 여부에 따라 댓글 작성폼을 다르게 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		document.getElementById('logout_state').style.display = 'block';
		document.getElementById('login_state').style.display = 'none';
	}
	
	try {
		await instance.get('/dummy');
		
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		document.getElementById('logout_state').style.display = 'none';
		document.getElementById('login_state').style.display = 'block';
		
		document.querySelector('.login_user span').innerText = payload.nickname;
	}
	catch (error) {
		setLoginState(false);
	}
});







/**
 * 서버에서 댓글 데이터를 가져오는 함수
 */
let totalPages = 0;
let page = 0;
let commentList = [];

function loadCommentList({ tablename, sort, size }) {

	const params = new URLSearchParams(window.location.search);
	const id = params.get('id');
	
	const parameters = new URLSearchParams({
		tablename : tablename,
		recodenum : id,
		sort : sort,
		page : page,
		size : size
	}).toString();
	
	fetch(`/comments?${parameters}`, {
		method: 'GET'
	})
	.then(response => response.json())
	.then(result => {
		const data = result.data;
		if (!data) return;
		
		console.log(result);
		
		const newComments = data.content;

		commentList = [...commentList, ...newComments];
		renderCommentList(newComments);

		page = data.number + 1;
		totalPages = data.totalPages;
		
		document.querySelector('.comment_count span:last-of-type').innerText = data.totalElements;

		// 더보기 버튼 제어
		const btnMore = document.querySelector('.btn_more');
		if (page >= totalPages) {
			btnMore.style.display = 'none';
		}
		else {
			btnMore.style.display = 'block';
		}
		
	})
	.catch(error => console.log(error));
}



/**
 * 배열에 저장된 댓글 목록을 화면에 표시하는 함수
 */
function renderCommentList(comments) {
	comments.filter(comment => comment.comm_id === null)
		.forEach(comment => {
			document.querySelector('.comment_list').append(createComment(comment));
	});
}



/**
 * 댓글 데이터를 변환하는 함수
 *
 * @param {Object} comment - 댓글 객체
 * @returns {string} 댓글 및 대댓글의 HTML 문자열
 */
function createComment(comment) {
	const subcommentList = commentList.filter(data => data.comm_id === comment.id);

	// 댓글 li
	const commentLi = document.createElement('li');
	commentLi.className = 'comment';
	commentLi.dataset.id = comment.id;

	// 댓글 info
	const infoDiv = document.createElement('div');
	infoDiv.className = 'comment_info';
	infoDiv.append(
		createWriterInfo({ nickname: comment.nickname, postdate: comment.postdate, isSub: false }),
		createActionLink({ id: comment.id, content: comment.content, isSub: false, userId: comment.user_id })
	);

	// 댓글 본문
	const contentP = document.createElement('p');
	contentP.className = 'comment_content';
	contentP.textContent = comment.content;

	// 툴바 (좋아요, 답글)
	const toolDiv = document.createElement('div');
	toolDiv.className = 'comment_tool';
	toolDiv.appendChild(createLikeButton(comment.likecount));

	const replyBtn = document.createElement('a');
	replyBtn.className = 'btn_outline_small write_subcomment_btn';
	replyBtn.href = 'javascript:void(0);';
	replyBtn.addEventListener('click', function (event) {
		event.preventDefault();
		if (!isLoggedIn()) {
			if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
				location.href = '/user/login.do';
			}
			replyBtn.dataset.bsToggle = 'modal';
			replyBtn.dataset.bsTarget = '#writeSubcommentModal';
		}
		document.getElementById('writeSubcommentCommId').value = comment.id;
	});


	const replySpan = document.createElement('span');
	replySpan.textContent = '답글 쓰기';
	replyBtn.appendChild(replySpan);

	toolDiv.appendChild(replyBtn);

	// 댓글 구성
	commentLi.append(infoDiv, contentP, toolDiv);

	// 대댓글 ul
	const subList = document.createElement('ul');
	subList.className = 'subcomment_list';

	subcommentList.forEach(subcomment => {
		const subLi = createSubcomment(subcomment);
		subList.appendChild(subLi);
	});

	// fragment 반환
	const fragment = document.createDocumentFragment();
	fragment.append(commentLi, subList);

	return fragment;
}



/**
 * 좋아요 버튼을 생성하는 함수
 */
function createLikeButton(likeCount) {
	const button = document.createElement('button');
	button.className = 'btn_tool write_subcomment_btn';

	const full = document.createElement('img');
	full.src = '/images/common/thumb_up_full.png';

	const empty = document.createElement('img');
	empty.src = '/images/common/thumb_up_empty.png';

	const count = document.createElement('p');
	count.textContent = likeCount;

	button.append(full, empty, count);
	return button;
}



/**
 * 수정과 신고와 삭제 동작의 링크를 생성하는 함수
 */
function createActionLink({ id, content, isSub, userId }) {

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
			await deleteComment({ id });
		});
		
		actionDiv.append(editLink, deleteLink);
	}
	
	const reportLink = document.createElement('a');
	reportLink.href = 'javascript:void(0);';
	reportLink.textContent = '신고';

	reportLink.addEventListener('click', (event) => {
		event.preventDefault();		
		if (!token) {
			if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
				location.href = '/user/login.do';
			}
			return;
		}
		reportLink.dataset.bsToggle = 'modal';
		reportLink.dataset.bsTarget = '#reportCommentModal';
	});
	
	actionDiv.append(reportLink);

	return actionDiv;
}



/**
 * 댓글과 대댓글의 작성자 정보 영역을 생성하는 함수
 */
function createWriterInfo({ nickname, postdate, isSub }) {
	
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
function createSubcomment(subcomment) {
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
		createWriterInfo({ nickname: subcomment.nickname, postdate: subcomment.postdate, isSub: true }),
		createActionLink({ id: subcomment.id, content: subcomment.content, isSub: true, userId: subcomment.user_id })
	);

	const contentP = document.createElement('p');
	contentP.className = 'subcomment_content';
	contentP.textContent = subcomment.content;

	const toolDiv = document.createElement('div');
	toolDiv.className = 'comment_tool';
	toolDiv.appendChild(createLikeButton(subcomment.likecount));

	innerDiv.append(infoDiv, contentP, toolDiv);
	subLi.append(arrowImg, innerDiv);

	return subLi;
}



/**
 * 댓글을 작성하는 함수
 */
async function writeComment({ tablename, content }) {
	
	if (!isLoggedIn()) {
		redirectToLogin();
	}
	
	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const params = new URLSearchParams(window.location.search);
		const id = params.get('id');
		
		const data = {
			content: content,
			tablename: tablename,
			recodenum: Number(id),
			user_id: payload.id
		};
		
		const response = await instance.post('/comments', data);
		
		if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
			const newComment = response.data.data;
			
			console.log(newComment);

			// 입력창 초기화
			document.getElementById("writeCommentContent").value = '';
			const submitBtn = document.querySelector("#writeCommentForm button[type='submit']");
			submitBtn.disabled = true;
			submitBtn.classList.add('disable');

			// 댓글 리스트에 추가 (상단에)
			commentList.unshift(newComment);

			// 새 댓글만 렌더링하여 상단에 추가
			const commentEl = createComment(newComment);
			document.querySelector('.comment_list').prepend(commentEl);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		const message = error?.response?.data?.message;
		
		if (code === 'COMMENT_CREATE_FAIL') {
			alert(message);
		}
		else if (code === 'COMMENT_INVALID_INPUT') {
			alert(message);
		}
		else if (code === 'COMMENT_UNAUTHORIZED') {
			alert(message);
		}
		else if (code === 'COMMENT_FORBIDDEN') {
			alert(message);
		}
		else if (code === 'COMMENT_NOT_FOUND') {
			alert(message);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alert(message);
		}
		else {
			console.log('서버 요청 중 오류 발생');
		}
	}
}



/**
 * 댓글을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	const editForm = document.getElementById('editCommentForm');

	editForm.addEventListener('submit', async function (e) {
		e.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}

		const id = document.getElementById('editCommentId').value;
		const content = document.getElementById('editCommentContent').value.trim();

		if (!content) {
			alert('수정할 내용을 입력해주세요.');
			return;
		}

		try {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				id: id,
				content: content,
				user_id: payload.id
			};
			
			const response = await instance.patch(`/comments/${id}`, data);
			
			// 성공시 수정 내용 반영
			if (response.data.code === 'COMMENT_UPDATE_SUCCESS') {
				const contentEl = document.querySelector(`.comment[data-id='${id}'] .comment_content, .subcomment[data-id='${id}'] .subcomment_content`);
				if (contentEl) {
				  contentEl.textContent = response.data.data.content;
				}
				
				// 모달 닫기
				bootstrap.Modal.getInstance(document.getElementById('editCommentModal')).hide();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			const message = error?.response?.data?.message;
			
			if (code === 'COMMENT_UPDATE_FAIL') {
				alert(message);
			}	
			else if (code === 'INVALID_INPUT_RESPONSE') {
				alert(message);
			}
			else if (code === 'COMMENT_UNAUTHORIZED_ACCESS') {
				alert(message);
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alert(message);
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alert(message);
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert(message);
			}
			else {
				console.log('서버 요청 중 오류 발생');
			}
		}
			
	});
});



/**
 * 댓글을 삭제하는 함수
 */
async function deleteComment({ id }) {

	if (confirm('작성하신 댓글을 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			const data = {
				id: id,
				user_id: payload.id
			};
			
			const response = await instance.delete(`/comments/${id}`, {data});
			
			if (response.data.code === 'COMMENT_DELETE_SUCCESS') {
				const commentEl = document.querySelector(`.comment[data-id='${id}']`);
				const subcommentListEl = commentEl?.nextElementSibling;

				// 일반 댓글 삭제
				if (commentEl) {
					commentEl.remove();
				}

				// 대댓글 리스트도 함께 삭제
				if (subcommentListEl && subcommentListEl.classList.contains('subcomment_list')) {
					subcommentListEl.remove();
				}

				// 혹시 대댓글 단독 삭제일 경우도 처리
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
				alert(message);
			}
			else if (code === 'COMMENT_INVALID_INPUT') {
				alert(message);
			}
			else if (code === 'COMMENT_UNAUTHORIZED') {
				alert(message);
			}
			else if (code === 'COMMENT_FORBIDDEN') {
				alert(message);
			}
			else if (code === 'COMMENT_NOT_FOUND') {
				alert(message);
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert(message);
			}
			else {
				console.log('서버 요청 중 오류 발생');
			}
		}
	}
	
}



/**
 * 대댓글을 작성하는 함수
 */
async function writeSubcomment({ tablename, content, commId }) {

	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);

		const params = new URLSearchParams(window.location.search);
		const id = params.get('id');

		const data = {
			content: content,
			tablename: tablename,
			recodenum: Number(id),
			user_id: payload.id,
			comm_id: commId
		};

		const response = await instance.post('/comments', data);

		if (response.data.code === 'COMMENT_CREATE_SUCCESS') {
			const newSubcomment = response.data.data;
				const parentCommentId = newSubcomment.comm_id; // 부모 ID 확인

				// 모달 닫기
				bootstrap.Modal.getInstance(document.getElementById('writeSubcommentModal')).hide();

				// 입력창 초기화
				document.getElementById("writeSubcommentContent").value = '';
				// document.querySelector("#writeSubcommentForm button[type='submit']").disabled = true;

				// commentList에 추가
				commentList.push(newSubcomment);

				// 대댓글 DOM에 추가
				const subList = document.querySelector(`.comment[data-id='${parentCommentId}']`).nextElementSibling;
				subList.appendChild(createSubcomment(newSubcomment));
		}
	}
	catch (error) {
		console.log('대댓글 작성 중 오류 발생', error);
		alert('대댓글 작성 실패');
	}
}












