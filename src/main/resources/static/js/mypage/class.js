/**
 * 전역 변수
 */
let sort = '';
let totalPages = 0;
let totalElements = 0;
let page = 0;
let size = 10;
let classList = [];





/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/');
	}
	
});





/**
 * 정렬 버튼 클릭시 연결된 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 정렬 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			classList = [];
			
			fetchUserClassList();
		});
	});
	
	fetchUserClassList();
});





/**
 * 서버에서 사용자가 개설한 쿠킹클래스 목록 데이터를 가져오는 함수
 */
async function fetchUserClassList() {
	
	// 클래스 목록 조회
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const params = new URLSearchParams({
			sort: sort,
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/classes?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			classList = response.data.data.content;
			
			// 렌더링
			renderUserClassList(classList);
			document.querySelector('.class_util .total').innerText = `총 ${totalElements}개`;
			renderPagination(fetchUserClassList);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'CLASS_READ_LIST_FAIL') {
			alertDanger('클래스 목록 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
}





/**
 * 사용자가 개설한 쿠킹클래스 목록을 화면에 렌더링하는 함수
 */
function renderUserClassList(classList) {
	
	const container = document.querySelector('.class_list');
	container.innerHTML = '';

	// 사용자가 개설한 쿠킹클래스 목록이 존재하지 않는 경우
	if (classList == null || classList.length === 0) {
		container.style.display = 'none';
		document.querySelector('.list_empty')?.remove();
		
		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';
		
		const span = document.createElement('span');
		span.textContent = '개설한 쿠킹클래스가 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 사용자가 개설한 쿠킹클래스가 존재하는 경우
	classList.forEach(classs => {
		container.style.display = 'block';
		document.querySelector('.list_empty')?.remove();
		
		const li = document.createElement('li');

		const statusDiv = document.createElement('div');
		switch (classs.approval) {
			case 1:
				statusDiv.className = classs.opened ? 'status primary' : 'status';
				statusDiv.textContent = '승인 완료';
				break;
			case 2:
				statusDiv.className = classs.opened ? 'status warning' : 'status';
				statusDiv.textContent = classs.opened ? '승인 대기중' : '대기 만료';
				break;
			case 0:
				statusDiv.className = classs.opened ? 'status danger' : 'status';
				statusDiv.textContent = '미승인';
				break;
		}
		li.appendChild(statusDiv);

		const classDiv = document.createElement('div');
		classDiv.className = 'class';
		
		const thumbnailLink = document.createElement('a');
		thumbnailLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		thumbnailLink.className = 'thumbnail';

		const img = document.createElement('img');
		img.src = classs.image;
		thumbnailLink.appendChild(img);
		classDiv.appendChild(thumbnailLink);

		const infoDiv = document.createElement('div');
		infoDiv.className = 'info';

		const titleLink = document.createElement('a');
		titleLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		titleLink.textContent = classs.title;
		infoDiv.appendChild(titleLink);

		const dateP = document.createElement('p');

		const scheduleSpan = document.createElement('span');
		const scheduleEm = document.createElement('em');
		scheduleEm.textContent = '수업일정';
		scheduleSpan.appendChild(scheduleEm);
		scheduleSpan.append(`${formatDateWithDay(classs.eventdate)} ${formatTime(classs.starttime)}-${formatTime(classs.endtime)}`);
		dateP.appendChild(scheduleSpan);

		const deadlineSpan = document.createElement('span');
		const deadlineEm = document.createElement('em');
		deadlineEm.textContent = '선정발표';
		deadlineSpan.appendChild(deadlineEm);
		deadlineSpan.append(`${formatDateWithDay(classs.noticedate)} ${formatTime(classs.starttime)}`);
		dateP.appendChild(deadlineSpan);

		infoDiv.appendChild(dateP);
		classDiv.appendChild(infoDiv);

		const cancelDiv = document.createElement('div');
		cancelDiv.className = 'cancel_btn';

		if (classs.approval === 1) {
			const btn = document.createElement('button');
			btn.className = 'btn_outline';
			btn.textContent = '신청서 보기';
			btn.onclick = function () {
				location.href = `/mypage/class/application.do?id=${classs.id}`;
			};
			cancelDiv.appendChild(btn);
		}
		
		classDiv.appendChild(cancelDiv);
		li.appendChild(classDiv);
		container.appendChild(li);
	});
}