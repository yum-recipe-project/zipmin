/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let keyword = '';
let eventList = [];





/**
 * 이벤트 목록을 검색하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.querySelector('form.search').addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = document.getElementById('text-srh').value.trim();
		page = 0;
		fetchEventList();
	});
	
	fetchEventList();
});




/**
 * 서버에서 투표 목록 데이터를 가져오는 함수
 */
async function fetchEventList() {
	
	try {
		const params = new URLSearchParams({
			page : page,
			size : size,
			keyword : keyword
		}).toString();
		
		const headers = {
			'Content-Type': 'application/json'
		};
		
		const response = await fetch(`/events?${params}`, {
			method: 'GET',
			headers: headers
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'EVENT_READ_LIST_SUCCESS') {

			totalPages = result.data.totalPages;
			page = result.data.number;
			eventList = result.data.content;
			
			renderEventList(result.data.content);
			renderAdminPagination(fetchEventList);
			
			document.querySelector('.total').innerText = `총 ${result.data.totalElements}개`;
		}
		
		// 여기에 에러코드 추가 !!!!******
		
	}
	catch (error) {
		console.log(error);
	}
	
}




/**
 * 이벤트 목록을 화면에 렌더링하는 함수
 */
function renderEventList(eventList) {
    const container = document.querySelector('.event_list');
    container.innerHTML = '';

    eventList.forEach((event, index) => {
        const tr = document.createElement('tr');
        tr.dataset.id = event.id;

        // 번호
        const noTd = document.createElement('td');
        const noH6 = document.createElement('h6');
        noH6.className = 'fw-semibold mb-0';
        noH6.textContent = index + 1;
        noTd.appendChild(noH6);

        // 제목
        const titleTd = document.createElement('td');
        const titleH6 = document.createElement('h6');
        titleH6.className = 'fw-semibold mb-0';
        titleH6.textContent = event.title;
        titleTd.appendChild(titleH6);
        titleTd.onclick = () => location.href = `/admin/viewEvent.do?id=${event.id}`;

        // 기간
        const periodTd = document.createElement('td');
        const periodH6 = document.createElement('h6');
        periodH6.className = 'fw-semibold mb-0';
        periodH6.textContent = `${formatDate(event.opendate)} - ${formatDate(event.closedate)}`;
        periodTd.appendChild(periodH6);
        periodTd.onclick = () => location.href = `/admin/viewEvent.do?id=${event.id}`;

        // 상태
        const statusTd = document.createElement('td');
        const statusSpan = document.createElement('span');
        const now = new Date();
        const openDate = new Date(event.opendate);
        const closeDate = new Date(event.closedate);
        const isOpen = now >= openDate && now <= closeDate;

        statusSpan.className = `badge ${isOpen ? 'bg-primary-subtle text-primary' : 'bg-danger-subtle text-danger'} d-inline-flex align-items-center gap-1`;
        statusSpan.innerHTML = `<i class="ti ${isOpen ? 'ti-check' : 'ti-x'} fs-4"></i>${isOpen ? '행사 진행중' : '행사 종료'}`;
        statusTd.appendChild(statusSpan);

        // 댓글수
        const commentTd = document.createElement('td');
        const commentH6 = document.createElement('h6');
        commentH6.className = 'fw-semibold mb-0';
        commentH6.textContent = `${event.commentcount || 0}개`;
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
        editBtn.onclick = () => { location.href = `/admin/editEvent.do?id=${event.id}`; };

        // 삭제 버튼
        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.className = 'btn btn-sm btn-outline-danger';
        deleteBtn.innerHTML = '삭제';
        deleteBtn.onclick = () => { deleteEvent(event.id); };

        btnWrap.append(editBtn, deleteBtn);
        actionTd.appendChild(btnWrap);

        tr.append(noTd, titleTd, periodTd, statusTd, commentTd, actionTd);
        container.appendChild(tr);
    });
}





/**
 * 이벤트를 삭제하는 함수
 */
async function deleteEvent(id) {
	
	if (confirm('이벤트를 삭제하시겠습니까?')) {
		try {
			const token = localStorage.getItem('accessToken');

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const data = {
				id: id
			};

			const response = await instance.delete(`/events/${id}`, {
				data: data,
				headers: headers
			});
			
			if (response.data.code === 'EVENT_DELETE_SUCCESS') {
				alert('이벤트가 성공적으로 삭제되었습니다.');
				const trElement = document.querySelector(`.event_list tr[data-id='${id}']`);
				if (trElement) trElement.remove();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'EVENT_DELETE_FAIL') {
				alert('이벤트 삭제에 실패했습니다');
			}
			if (code === 'CHOMP_DELETE_FAIL') {
				alert('쩝쩝박사 게시물 삭제에 실패했습니다');
			}
			else if (code === 'EVENT_INVALID_INPUT') {
				alert('입력값이 유효하지 않습니다.');
			}
			else if (code === 'EVENT_UNAUTHORIZED') {
				alert('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'EVENT_FORBIDDEN') {
				alert('접근 권한이 없습니다.');
			}
			else if (code === 'EVENT_NOT_FOUND') {
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







