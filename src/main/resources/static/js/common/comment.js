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
	
	const html = maincomment.map(comment => getCommentHTML(comment)).join("");
	document.querySelector(".comment_list").insertAdjacentHTML("beforeend", html);
}



/**
 * 댓글 데이터를 HTML 문자열로 변환하는 함수
 *
 * @param {Object} comment - 댓글 객체
 * @returns {string} 댓글 및 대댓글의 HTML 문자열
 */
function getCommentHTML(comment) {

	const subcommentList = commentList.filter(data => data.comm_id === comment.id && data.id !== data.comm_id);

	const postdate = new Date(comment.postdate);
	const formatPostdate = `${postdate.getFullYear()}년 ${String(postdate.getMonth() + 1).padStart(2, '0')}월 ${String(postdate.getDate()).padStart(2, '0')}일`;
	
	const subcommentListHTML = subcommentList.map(subcomment => {
		const subcommentPostdate = new Date(subcomment.postdate);
		const formatSubcommentPostdate = `${subcommentPostdate.getFullYear()}년 ${String(subcommentPostdate.getMonth() + 1).padStart(2, '0')}월 ${String(subcommentPostdate.getDate()).padStart(2, '0')}일`;
	
		return `
			<li class="subcomment">
				<img class="subcomment_arrow" src="/images/chompessor/arrow_right.png">
				<div class="subcomment_inner">
					<div class="subcomment_info">
						<div class="subcomment_writer">
							<img src="/images/common/test.png">
							<span>${subcomment.nickname}</span>
							<span>${formatSubcommentPostdate}</span>
						</div>
						<div class="subcomment_action">
							<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">신고</a>
							<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">수정</a>
							<a href="">삭제</a>
						</div>
					</div>
					<p class="subcomment_content">${subcomment.content}</p>
					<div class="comment_tool">
						<button class="btn_tool write_subcomment_btn">
							<img src="/images/common/thumb_up_full.png">
							<img src="/images/common/thumb_up_empty.png">
							<p>${subcomment.likecount}</p>
						</button>
					</div>
				</div>
			</li>`;
	}).join("");
	
	return `
		<li class="comment">
			<div class="comment_info">
				<div class="comment_writer">
					<img src="/images/common/test.png">
					<span>${comment.nickname}</span>
					<span>${formatPostdate}</span>
				</div>
				<div class="comment_action">
					<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">신고</a>
					<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">수정</a>
					<a href="">삭제</a>
				</div>
			</div>
			<p class="comment_content">${comment.content}</p>
			<div class="comment_tool">
				<button class="btn_tool write_subcomment_btn">
					<img src="/images/common/thumb_up_full.png">
					<img src="/images/common/thumb_up_empty.png">
					<p>${comment.likecount}</p>
				</button>
				<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeSubcommentModal">
					<span>답글 쓰기</span>
				</a>
			</div>
		</li>
		<ul class="subcomment_list">${subcommentListHTML}</ul>`;
}








/**
 * 댓글을 작성하는 함수
 */
/*
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
			const megazineId = params.get('megazineId');
			
			const data = {
				content: document.getElementById("writeCommentContent").value.trim(),
				tablename: 'chomp_megazine',
				recodenum: Number(megazineId),
				user_id: payload.id
			};
			
			const response = instance.post(`http://localhost:8586/comments`, data);
			
			console.log(response);
			
		}
		catch (error) {
			console.log(error);
		}
	});
});
*/

