/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 15;
let keyword = '';
let category = '';
let sortKey = 'id';
let sortOrder = 'desc';
let commentList = [];





/**
 * 댓글 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchCommentList();
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab a').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab a.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			keyword = '';
			document.getElementById('text-srh').value = '';
			sortKey = 'id';
			sortOrder = 'desc';
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			document.querySelector(`.sort_btn[data-key="${sortKey}"]`).classList.add(sortOrder);
			
			commentList = [];
			fetchCommentList();
		});
	});
	
	// 정렬 버튼
	document.querySelectorAll('.sort_btn').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			const key = btn.dataset.key;

		    if (sortKey === key) {
		      sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
		    }
			else {
		      sortKey = key;
		      sortOrder = 'desc';
		    }
			
			document.querySelectorAll('.sort_btn').forEach(el => el.classList.remove('asc', 'desc'));
			this.classList.add(sortOrder);
			
			page = 0;
			fetchCommentList();
		});
	});
	
	fetchCommentList();
});





/**
 * 서버에서 댓글 목록 데이터를 가져오는 함수
 */
async function fetchCommentList(scrollTop = true) {
	
	try {
		const params = new URLSearchParams({
			tablename: category,
			sort: sortKey + '-' + sortOrder,
			keyword : keyword,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/admin/comments?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'COMMENT_READ_LIST_SUCCESS') {
			
			// 전역 변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			commentList = result.data.content;
			
			// 렌더링
			renderCommentList(commentList);
			renderAdminPagination(fetchCommentList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.table_th').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const table = document.querySelector('.fixed-table');
				table.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('.table_th').style.display = '';
			}
			
			// 스크롤 최상단 이동
			if (scrollTop) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
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
		else if (result.code === 'COMMENT_INVALID_FAIL') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_INVALID_FAIL') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'LIKE_INVALID_FAIL') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'REPORT_INVALID_FAIL') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alertDanger('서버 내부 오류가 발생했습니다.');
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

  if (!Array.isArray(commentList)) return;

  commentList.forEach((comment, index) => {
    const tr = document.createElement('tr');
    tr.dataset.id = comment.id;

    // 번호
    const noTd = document.createElement('td');
    const noH6 = document.createElement('h6');
    noH6.className = 'fw-semibold mb-0';
	const offset = page * size + index;
	if (sortKey === 'id' && sortOrder === 'asc') {
		noH6.textContent = offset + 1;
	}
	else {
		noH6.textContent = totalElements - offset;
	}
	noTd.appendChild(noH6);

    // 게시판명
    const tableTd = document.createElement('td');
    const tableH6 = document.createElement('h6');
    tableH6.className = 'fw-semibold mb-0';
	switch (comment.tablename) {
		case 'recipe' :
			tableH6.textContent = '레시피'
			break;
		case 'guide':
			tableH6.textContent = '키친가이드'
			break;
		case 'vote':
			tableH6.textContent = '투표'
			break;
		case 'megazine':
			tableH6.textContent = '매거진'
			break;
		case 'event':
			tableH6.textContent = '이벤트'
			break;
	}
    tableTd.appendChild(tableH6);

    // 내용
    const contentTd = document.createElement('td');
    const contentH6 = document.createElement('h6');
    contentH6.className = 'fw-semibold mb-0 truncate text-start';
    contentH6.textContent = comment.content || '';
    contentTd.appendChild(contentH6);
	contentH6.addEventListener('click', function () {
	    this.classList.toggle('expanded');
	});

    // 작성자
    const writerTd = document.createElement('td');
    const writerH6 = document.createElement('h6');
    writerH6.className = 'fw-semibold mb-0';
    writerH6.textContent = comment.nickname;
    writerTd.appendChild(writerH6);

    // 작성일
    const dateTd = document.createElement('td');
    const dateH6 = document.createElement('h6');
    dateH6.className = 'fw-semibold mb-0';
	dateH6.textContent = formatDateTime(comment.postdate);
    dateTd.appendChild(dateH6);

    // 좋아요수
    const likeTd = document.createElement('td');
    const likeH6 = document.createElement('h6');
    likeH6.className = 'fw-semibold mb-0';
    likeH6.textContent = comment.likecount;
    likeTd.appendChild(likeH6);

	// 신고수
	const reportTd = document.createElement('td');
	reportTd.classList.add('report-td');
	const reportWrap = document.createElement('div');
	reportWrap.className = 'report-cell d-inline-block position-relative';
	const reportCount = document.createElement('h6');
	reportCount.className = 'fw-semibold mb-0';
	reportCount.textContent = comment.reportcount;
	
	// 신고 보기
	if ((comment.reportcount ?? 0) > 0) {
		reportCount.className = 'fw-semibold mb-0 view';
		reportCount.dataset.bsToggle = 'modal';
		reportCount.dataset.bsTarget = '#listReportModal';
		reportCount.dataset.recodenum = comment.id;
		reportCount.addEventListener('click', (event) => {
			if (!isLoggedIn()) {
				event.preventDefault();
				redirectToLogin();
				return;
			}
		});
	}
	reportWrap.appendChild(reportCount);
	reportTd.appendChild(reportWrap);

	// 기능
	const actionTd = document.createElement('td');
	const btnWrap = document.createElement('div');
	btnWrap.className = 'd-flex justify-content-end gap-2';
	
	// 기능 버튼 조건
	const token = localStorage.getItem('accessToken');
	const payload = parseJwt(token);
	const canAction =
	    payload.role === 'ROLE_SUPER_ADMIN' ||
	    (payload.role === 'ROLE_ADMIN' && comment.role === 'ROLE_USER') ||
	    (payload.id === comment.user_id);
	
	if (canAction) {
		// 수정 버튼
		const editBtn = document.createElement('button');
		editBtn.type = 'button';
		editBtn.className = 'btn btn-sm btn-outline-info';
		editBtn.dataset.bsToggle = 'modal';
		editBtn.dataset.bsTarget = '#editCommentModal';
		editBtn.innerHTML = '수정';
		editBtn.addEventListener('click', (event) => {
			if (!isLoggedIn()) {
				event.preventDefault();
				redirectToLogin();
				return;
			}
			document.getElementById('editCommentId').value = comment.id;
			document.getElementById('editCommentContentInput').value = comment.content;
		});
		
		// 삭제 버튼
		const deleteBtn = document.createElement('button');
		deleteBtn.type = 'button';
		deleteBtn.className = 'btn btn-sm btn-outline-danger';
		deleteBtn.innerHTML = '삭제';
		deleteBtn.onclick = () => deleteComment(comment.id);
		
		btnWrap.append(editBtn, deleteBtn);
	}
	
	actionTd.appendChild(btnWrap);

    tr.append(noTd, tableTd, contentTd, writerTd, dateTd, likeTd, reportTd, actionTd);
    container.appendChild(tr);
  });
}





/**
 * 댓글을 삭제하는 함수
 */
async function deleteComment(id) {
	
	try {
		const response = await instance.delete(`/comments/${id}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'COMMENT_DELETE_SUCCESS') {
			alertPrimary('댓글이 성공적으로 삭제되었습니다.');
			fetchCommentList(false);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'COMMENT_DELETE_FAIL') {
			alertDanger('댓글 삭제에 실패했습니다.');
		}
		else if (code === 'COMMENT_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'COMMENT_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'COMMENT_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'COMMENT_NOT_FOUND') {
			alertDanger('해당 댓글을 찾을 수 없습니다.');
		}
		else if (code == 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
}
