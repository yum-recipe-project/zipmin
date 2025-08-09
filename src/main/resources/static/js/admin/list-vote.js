/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let keyword = '';
let voteList = [];





/**
 * 투표 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchVoteList();
	});
	
	fetchVoteList();
});





/**
 * 서버에서 투표 목록 데이터를 가져오는 함수
 */
async function fetchVoteList() {
	
	try {
		const params = new URLSearchParams({
			page : page,
			size : size,
			keyword : keyword
		}).toString();
		
		const headers = {
			'Content-Type': 'application/json'
		};
		
		const response = await fetch(`/votes?${params}`, {
			method: 'GET',
			headers: headers
		});
		
		const result = await response.json();
		
		if (result.code === 'VOTE_READ_LIST_SUCCESS') {

			totalPages = result.data.totalPages;
			page = result.data.number;
			voteList = result.data.content;
			
			renderVoteList(result.data.content);
			renderPagination();
			
			document.querySelector('.total').innerText = `총 ${result.data.totalElements}개`;
		}
		
		// 여기에 에러코드 추가 !!!!******
		
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 투표 목록을 화면에 렌더링하는 함수
 */
function renderVoteList(voteList) {
	const container = document.querySelector('.vote_list');
	container.innerHTML = '';

	voteList.forEach((vote, index) => {
		const tr = document.createElement('tr');
		tr.dataset.id = vote.id;
		
		// No
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		noH6.textContent = index + 1;
		noTd.appendChild(noH6);

		// 제목
		const titleTd = document.createElement('td');
		const titleWrap = document.createElement('div');
		titleWrap.className = 'align-items-center';

		const titleH6 = document.createElement('h6');
		titleH6.className = 'fs-4 fw-semibold mb-2';
		titleH6.textContent = vote.title;
		titleWrap.appendChild(titleH6);

		const maxRate = Math.max(...vote.choice_list.map(choice => choice.rate));

		// 선택지
		vote.choice_list.forEach(choice => {
			const choiceSpan = document.createElement('span');
			choiceSpan.className = 'fw-normal';
			
			if (choice.rate === maxRate && maxRate !== 0) {
				choiceSpan.innerHTML = `<b>${choice.choice} (${choice.rate}%)</b>`;
			} else {
				choiceSpan.textContent = `${choice.choice} (${choice.rate}%)`;
			}

			titleWrap.appendChild(choiceSpan);
			titleWrap.appendChild(document.createElement('br'));
		});

		titleTd.appendChild(titleWrap);
		titleTd.onclick = () => location.href = `/admin/viewVote.do?id=${vote.id}`;

		// 참여 기간
		const periodTd = document.createElement('td');
		const periodH6 = document.createElement('h6');
		periodH6.className = 'fw-semibold mb-0';
		periodH6.textContent = `${formatDate(vote.opendate)} - ${formatDate(vote.closedate)}`;
		periodTd.appendChild(periodH6);
		periodTd.onclick = () => location.href = `/admin/viewVote.do?id=${vote.id}`;

		// 상태
		const statusTd = document.createElement('td');
		const statusSpan = document.createElement('span');
		statusSpan.className = `badge ${vote.status === 'open' ? 'bg-primary-subtle text-primary' : 'bg-danger-subtle text-danger'} d-inline-flex align-items-center gap-1`;
		statusSpan.innerHTML = `<i class="ti ${vote.status === 'open' ? 'ti-check' : 'ti-x'} fs-4"></i>${vote.status === 'open' ? '투표중' : '투표 종료'}`;
		statusTd.appendChild(statusSpan);

		// 참여자수
		const totalTd = document.createElement('td');
		const totalH6 = document.createElement('h6');
		totalH6.className = 'fw-semibold mb-0';
		totalH6.textContent = `${vote.total}명`;
		totalTd.appendChild(totalH6);

		// 댓글수
		const commentTd = document.createElement('td');
		const commentH6 = document.createElement('h6');
		commentH6.className = 'fw-semibold mb-0';
		commentH6.textContent = `${vote.commentcount}개`;
		commentTd.appendChild(commentH6);

		// 기능
		const actionTd = document.createElement('td');

		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';

		// 수정 버튼
		const editBtn = document.createElement('button');
		editBtn.type = 'button';
		editBtn.className = 'btn btn-sm btn-outline-info';
		editBtn.innerHTML = '수정';
		editBtn.onclick = () => { location.href = `/admin/editVote.do?id=${vote.id}`; };

		// 삭제 버튼
		const deleteBtn = document.createElement('button');
		deleteBtn.type = 'button';
		deleteBtn.className = 'btn btn-sm btn-outline-danger';
		deleteBtn.innerHTML = '삭제';
		deleteBtn.onclick = () => { deleteVote(vote.id); };

		btnWrap.append(editBtn, deleteBtn);
		actionTd.appendChild(btnWrap);

		tr.append(noTd, titleTd, periodTd, statusTd, totalTd, commentTd, actionTd);
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
				fetchVoteList();
			}
		});
	});
}





/**
 * 투표를 삭제하는 함수
 */
async function deleteVote(id) {
	
	if (confirm('투표를 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const data = {
				id: id
			};

			const response = await instance.delete(`/votes/${id}`, {
				data: data,
				headers: headers
			});
			
			if (response.data.code === 'VOTE_DELETE_SUCCESS') {
				alert('투표가 성공적으로 삭제되었습니다.');
				const trElement = document.querySelector(`.vote_list tr[data-id='${id}']`);
				if (trElement) trElement.remove();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'VOTE_DELETE_FAIL') {
				alert('투표 삭제에 실패했습니다');
			}
			if (code === 'CHOMP_DELETE_FAIL') {
				alert('쩝쩝박사 게시물 삭제에 실패했습니다');
			}
			else if (code === 'VOTE_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'VOTE_UNAUTHORIZED') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'VOTE_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'VOTE_NOT_FOUND') {
				alert('해당 매거진을 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert('서버 내부 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	}

}




















