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



// 정렬 방식
let sort = 'new';
// 전체 페이지
let totalPages = 0;
// 현재 페이지
let page = 0;
// 가져올 개수
const size = 10;
// 댓글 목록
let commentList = [];



/**
 * 서버에서 댓글 데이터를 가져오는 함수
 */
function fetchCommentList(tablename) {

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
function renderCommentList() {
	const maincomment = commentList.filter(comment => comment.id === comment.comm_id);
	maincomment.forEach(comment => {
		document.querySelector('.comment_list').appendChild(createComment(comment));
	});
}



/**
 * 댓글 데이터를 변환하는 함수
 *
 * @param {Object} comment - 댓글 객체
 * @returns {string} 댓글 및 대댓글의 HTML 문자열
 */
function createComment(comment) {
	const subcommentList = commentList.filter(data => data.comm_id === comment.id && data.id !== data.comm_id);

	const postdate = new Date(comment.postdate);
	const formatPostdate = `${postdate.getFullYear()}년 ${String(postdate.getMonth() + 1).padStart(2, '0')}월 ${String(postdate.getDate()).padStart(2, '0')}일`;

	// 댓글 li
	const commentLi = document.createElement('li');
	commentLi.className = 'comment';

	// 댓글 정보
	const infoDiv = document.createElement('div');
	infoDiv.className = 'comment_info';

	const writerDiv = document.createElement('div');
	writerDiv.className = 'comment_writer';

	const writerImg = document.createElement('img');
	writerImg.src = '/images/common/test.png';

	const writerName = document.createElement('span');
	writerName.textContent = comment.nickname;

	const dateSpan = document.createElement('span');
	dateSpan.textContent = formatPostdate;

	writerDiv.append(writerImg, writerName, dateSpan);

	const actionDiv = document.createElement('div');
	actionDiv.className = 'comment_action';

	const reportLink = document.createElement('a');
	reportLink.href = 'javascript:void(0);';
	reportLink.dataset.bsToggle = 'modal';
	reportLink.dataset.bsTarget = '#reportCommentModal';
	reportLink.textContent = '신고';

	const editLink = document.createElement('a');
	editLink.href = 'javascript:void(0);';
	editLink.dataset.bsToggle = 'modal';
	editLink.dataset.bsTarget = '#editCommentModal';
	editLink.textContent = '수정';

	const deleteLink = document.createElement('a');
	deleteLink.href = '';
	deleteLink.textContent = '삭제';

	actionDiv.append(reportLink, editLink, deleteLink);
	infoDiv.append(writerDiv, actionDiv);

	// 댓글 본문
	const contentP = document.createElement('p');
	contentP.className = 'comment_content';
	contentP.textContent = comment.content;

	// 툴
	const toolDiv = document.createElement('div');
	toolDiv.className = 'comment_tool';

	const likeButton = document.createElement('button');
	likeButton.className = 'btn_tool write_subcomment_btn';

	const thumbFull = document.createElement('img');
	thumbFull.src = '/images/common/thumb_up_full.png';

	const thumbEmpty = document.createElement('img');
	thumbEmpty.src = '/images/common/thumb_up_empty.png';

	const likeCount = document.createElement('p');
	likeCount.textContent = comment.likecount;

	likeButton.append(thumbFull, thumbEmpty, likeCount);

	const replyBtn = document.createElement('a');
	replyBtn.className = 'btn_outline_small write_subcomment_btn';
	replyBtn.href = 'javascript:void(0);';
	replyBtn.dataset.bsToggle = 'modal';
	replyBtn.dataset.bsTarget = '#writeSubcommentModal';

	const replySpan = document.createElement('span');
	replySpan.textContent = '답글 쓰기';

	replyBtn.appendChild(replySpan);
	toolDiv.append(likeButton, replyBtn);

	// comment li 최종 구성
	commentLi.append(infoDiv, contentP, toolDiv);

	// 답글 ul
	const subList = document.createElement('ul');
	subList.className = 'subcomment_list';

	subcommentList.forEach(subcomment => {
		const subLi = document.createElement('li');
		subLi.className = 'subcomment';

		const arrowImg = document.createElement('img');
		arrowImg.className = 'subcomment_arrow';
		arrowImg.src = '/images/chompessor/arrow_right.png';

		const innerDiv = document.createElement('div');
		innerDiv.className = 'subcomment_inner';

		const infoDiv = document.createElement('div');
		infoDiv.className = 'subcomment_info';

		const writerDiv = document.createElement('div');
		writerDiv.className = 'subcomment_writer';

		const img = document.createElement('img');
		img.src = '/images/common/test.png';

		const nameSpan = document.createElement('span');
		nameSpan.textContent = subcomment.nickname;

		const date = new Date(subcomment.postdate);
		const dateSpan = document.createElement('span');
		dateSpan.textContent = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;

		writerDiv.append(img, nameSpan, dateSpan);

		const actionDiv = document.createElement('div');
		actionDiv.className = 'subcomment_action';

		['#reportCommentModal', '#editCommentModal'].forEach(modalId => {
			const link = document.createElement('a');
			link.href = 'javascript:void(0);';
			link.dataset.bsToggle = 'modal';
			link.dataset.bsTarget = modalId;
			link.textContent = modalId === '#reportCommentModal' ? '신고' : '수정';
			actionDiv.appendChild(link);
		});

		const deleteLink = document.createElement('a');
		deleteLink.href = '';
		deleteLink.textContent = '삭제';
		actionDiv.appendChild(deleteLink);

		infoDiv.append(writerDiv, actionDiv);

		const contentP = document.createElement('p');
		contentP.className = 'subcomment_content';
		contentP.textContent = subcomment.content;

		const toolDiv = document.createElement('div');
		toolDiv.className = 'comment_tool';

		const likeBtn = document.createElement('button');
		likeBtn.className = 'btn_tool write_subcomment_btn';

		const fullImg = document.createElement('img');
		fullImg.src = '/images/common/thumb_up_full.png';

		const emptyImg = document.createElement('img');
		emptyImg.src = '/images/common/thumb_up_empty.png';

		const count = document.createElement('p');
		count.textContent = subcomment.likecount;

		likeBtn.append(fullImg, emptyImg, count);
		toolDiv.appendChild(likeBtn);

		innerDiv.append(infoDiv, contentP, toolDiv);
		subLi.append(arrowImg, innerDiv);
		subList.appendChild(subLi);
	});

	const fragment = document.createDocumentFragment();
	fragment.append(commentLi, subList);
	return fragment;
}



/**
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	document.getElementById('writeCommentForm').addEventListener("submit", function (event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}
		
		try {
			const token = localStorage.getItem('accessToken');
			const payload = parseJwt(token);
			
			console.log(payload.id);
			
			const params = new URLSearchParams(window.location.search);
			const id = params.get('id');
			
			const data = {
				content: document.getElementById("writeCommentContent").value.trim(),
				tablename: 'chomp_megazine',
				recodenum: Number(id),
				user_id: payload.id
			};
			
			const response = instance.post('/comments', data);
			
			console.log(response);
			
		}
		catch (error) {
			console.log(error);
		}
	});
});

