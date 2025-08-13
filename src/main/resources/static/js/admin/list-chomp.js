/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let keyword = '';
let chompList = [];





/**
 * 쩝쩝박사 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchChompList();
	});
	
	fetchChompList();
});





/**
 * 서버에서 쩝쩝박사 목록 데이터를 가져오는 함수
 */
async function fetchChompList() {
	
	try {
		const params = new URLSearchParams({
			page : page,
			size : size,
			keyword : keyword,
			category : 'all'
		}).toString();

		const headers = {
			'Content-Type': 'application/json'
		};

		const response = await fetch(`/chomp?${params}`, {
			method: 'GET',
			headers: headers
		});

		const result = await response.json();
		
		console.log(result);

		if (result.code === 'CHOMP_READ_LIST_SUCCESS') {

			totalPages = result.data.totalPages;
			page = result.data.number;
			chompList = result.data.content;
			
			renderChompList(result.data.content);
			renderAdminPagination(fetchChompList);
			
			document.querySelector('.total').innerText = `총 ${result.data.totalElements}개`;
		}
		
		/*** 여기에 에러코드 더 추가해야함  */
		
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 쩝쩝박사 목록을 화면에 렌더링하는 함수
 */
function renderChompList(chompList) {
    const container = document.querySelector('.chomp_list');
    container.innerHTML = '';

    chompList.forEach((chomp, index) => {
        const tr = document.createElement('tr');
		tr.dataset.id = chomp.id;

        // 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		noH6.textContent = index + 1;
		noTd.appendChild(noH6);
		
		// 카테고리
		const categoryTd = document.createElement('td');
		const categoryH6 = document.createElement('h6');
		categoryH6.className = 'fw-semibold mb-0';
		if (chomp.category === 'event' && chomp.event_dto) {
		    categoryH6.textContent = '이벤트';
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
		    categoryH6.textContent = '매거진';
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    categoryH6.textContent = '투표';
		}
		categoryTd.appendChild(categoryH6);

        // 제목
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-0';
		if (chomp.category === 'event' && chomp.event_dto) {
		    titleH6.textContent = chomp.event_dto.title;
		    titleTd.onclick = () => location.href = `/admin/viewEvent.do?id=${chomp.event_dto.id}`;
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
		    titleH6.textContent = chomp.megazine_dto.title;
		    titleTd.onclick = () => location.href = `/admin/viewMegazine.do?id=${chomp.megazine_dto.id}`;
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    titleH6.textContent = chomp.vote_dto.title;
		    titleTd.onclick = () => location.href = `/admin/viewVote.do?id=${chomp.vote_dto.id}`;
		}
		titleTd.appendChild(titleH6);

        // 기간
		const periodTd = document.createElement('td');
		const periodH6 = document.createElement('h6');
		periodH6.className = 'fw-semibold mb-0';
		if (chomp.category === 'event' && chomp.event_dto) {
		    periodH6.textContent = `${formatDate(chomp.event_dto.opendate)} - ${formatDate(chomp.event_dto.closedate)}`;
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
		    periodH6.textContent = `${formatDate(chomp.megazine_dto.postdate)}`;
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    periodH6.textContent = `${formatDate(chomp.vote_dto.opendate)} - ${formatDate(chomp.vote_dto.closedate)}`;
		}
		periodTd.appendChild(periodH6);
		

        // 댓글수
		const commentTd = document.createElement('td');
		const commentH6 = document.createElement('h6');
		commentH6.className = 'fw-semibold mb-0';
		if (chomp.category === 'event' && chomp.event_dto) {
		    commentH6.textContent = `${chomp.event_dto.commentcount || 0}개`;
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
		    commentH6.textContent = `${chomp.megazine_dto.commentcount || 0}개`;
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    commentH6.textContent = `${chomp.vote_dto.commentcount || 0}개`;
		}
		commentTd.appendChild(commentH6);

        // 기능 버튼
		const actionTd = document.createElement('td');
		const btnWrap = document.createElement('div');
		btnWrap.className = 'd-flex justify-content-end gap-2';
		
        // 수정 버튼
		const editBtn = document.createElement('button');
		editBtn.type = 'button';
		editBtn.className = 'btn btn-sm btn-outline-info';
		editBtn.innerHTML = '수정';
		if (chomp.category === 'event' && chomp.event_dto) {
		    editBtn.onclick = () => { location.href = `/admin/editEvent.do?id=${chomp.event_dto.id}`; };
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
		    editBtn.onclick = () => { location.href = `/admin/editMegazine.do?id=${chomp.megazine_dto.id}`; };
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    editBtn.onclick = () => { location.href = `/admin/editVote.do?id=${chomp.vote_dto.id}`; };
		}
		
		// 삭제 버튼
		const deleteBtn = document.createElement('button');
		deleteBtn.type = 'button';
		deleteBtn.className = 'btn btn-sm btn-outline-danger';
		deleteBtn.innerHTML = '삭제';
		deleteBtn.onclick = () => { deleteMegazine(megazine.id); };
		if (chomp.category === 'event' && chomp.event_dto) {
		    deleteBtn.onclick = () => { deleteEvent(chomp.event_dto.id, chomp.id); };
		}
		else if (chomp.category === 'megazine' && chomp.megazine_dto) {
			deleteBtn.onclick = () => { deleteMegazine(chomp.megazine_dto.id, chomp.id); };
		}
		else if (chomp.category === 'vote' && chomp.vote_dto) {
		    deleteBtn.onclick = () => { deleteVote(chomp.vote_dto.id, chomp.id); };
		}
		
		btnWrap.append(editBtn, deleteBtn);
		actionTd.appendChild(btnWrap);
		
		tr.append(noTd, categoryTd, titleTd, periodTd, commentTd, actionTd);

        container.appendChild(tr);
    });
}






/**
 * 매거진을 삭제하는 함수
 */
async function deleteMegazine(id, chompId) {
	
	if (confirm('매거진을 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const data = {
				id: id
			};

			const response = await instance.delete(`/megazines/${id}`, {
				data: data,
				headers: headers
			});
			
			if (response.data.code === 'MEGAZINE_DELETE_SUCCESS') {
				alert('매거진이 성공적으로 삭제되었습니다.');
				const trElement = document.querySelector(`.chomp_list tr[data-id='${chompId}']`);
				if (trElement) trElement.remove();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'MEGAZINE_DELETE_FAIL') {
				alert('매거진 삭제에 실패했습니다');
			}
			if (code === 'CHOMP_DELETE_FAIL') {
				alert('쩝쩝박사 게시물 삭제에 실패했습니다');
			}
			else if (code === 'MEGAZINE_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'MEGAZINE_UNAUTHORIZED') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'MEGAZINE_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'MEGAZINE_NOT_FOUND') {
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




/**
 * 투표를 삭제하는 함수
 */
async function deleteVote(id, chompId) {
	
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
				const trElement = document.querySelector(`.chomp_list tr[data-id='${chompId}']`);
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







