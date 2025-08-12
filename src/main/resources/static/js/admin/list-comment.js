/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let keyword = '';
let category = '';
let sortKey = 'postdate';
let sortOrder = 'desc';
let commentList = [];





/**
 * 댓글 카테고리 클릭시 연결된 내용을 표시하고 목록을 검색하는 함수
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
			sortKey = 'postdate';
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
async function fetchCommentList() {
	
	try {
		const params = new URLSearchParams({
			page : page,
			size : size,
			keyword : keyword,
			tablename: category,
			sort: sortKey + '-' + sortOrder
		}).toString();
		
		const headers = {
			'Content-Type': 'application/json'
		};
		
		const response = await fetch(`/comments?${params}`, {
			method: 'GET',
			headers: headers
		});
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'COMMENT_READ_LIST_SUCCESS') {
			totalPages = result.data.totalPages;
			page = result.data.number;
			commentList = result.data.content;
			
			renderCommentList(result.data.content);
			renderPagination();
			
			document.querySelector('.total').innerText = `총 ${result.data.totalElements}개`;
		}
		
		
		// 여기에 에러코드 추가 !!!!!
		
		
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
    noH6.textContent = index + 1;
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
			tableH6.textContent = '가이드'
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
		default:
			console.log('기타 게시판입니다.');
			break;
	}
    tableTd.appendChild(tableH6);

    // 내용
    const contentTd = document.createElement('td');
    const contentH6 = document.createElement('h6');
    contentH6.className = 'fw-semibold mb-0';
    // textContent로 XSS 방지
    contentH6.textContent = comment.content || '';
    contentTd.appendChild(contentH6);

    // 작성자
    const writerTd = document.createElement('td');
    const writerH6 = document.createElement('h6');
    writerH6.className = 'fw-semibold mb-0';
    writerH6.textContent = comment.nickname || '익명';
    writerTd.appendChild(writerH6);

    // 작성일
    const dateTd = document.createElement('td');
    const dateH6 = document.createElement('h6');
    dateH6.className = 'fw-semibold mb-0';
	dateH6.textContent = `${formatDate(comment.postdate)} ${formatTime(comment.postdate)}`;
    dateTd.appendChild(dateH6);

    // 좋아요수
    const likeTd = document.createElement('td');
    const likeH6 = document.createElement('h6');
    likeH6.className = 'fw-semibold mb-0';
    likeH6.textContent = (comment.likecount ?? 0).toString();
    likeTd.appendChild(likeH6);

    // 신고수
    const reportTd = document.createElement('td');
    const reportH6 = document.createElement('h6');
    reportH6.className = 'fw-semibold mb-0';
    reportH6.textContent = (comment.reportcount ?? 0).toString();
    reportTd.appendChild(reportH6);

	// 기능
	const actionTd = document.createElement('td');
	const btnWrap = document.createElement('div');
	btnWrap.className = 'd-flex justify-content-end gap-2';
	
	// 수정 버튼
	const editBtn = document.createElement('button');
	editBtn.type = 'button';
	editBtn.className = 'btn btn-sm btn-outline-info';
	editBtn.innerHTML = '수정';
	// editBtn.onclick = () => { location.href = `/admin/editMegazine.do?id=${megazine.id}`; };

	// 삭제 버튼
	const deleteBtn = document.createElement('button');
	deleteBtn.type = 'button';
	deleteBtn.className = 'btn btn-sm btn-outline-danger';
	deleteBtn.innerHTML = '삭제';
	// deleteBtn.onclick = () => { deleteMegazine(megazine.id); };

	btnWrap.append(editBtn, deleteBtn);
	actionTd.appendChild(btnWrap);

    tr.append(noTd, tableTd, contentTd, writerTd, dateTd, likeTd, reportTd, actionTd);
    container.appendChild(tr);
  });
}




/**
 * 페이지네이션을 화면에 렌더링하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchCommentList();
			}
		});
	});
}






















