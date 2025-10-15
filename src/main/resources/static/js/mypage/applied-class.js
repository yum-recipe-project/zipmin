/**
 * 전역 변수 선언
 */
let status = '';
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
			
			status = btn.dataset.status;
			page = 0;
			classList = [];
			
			fetchClassList();
		});
	});
	
	fetchClassAttend();
	fetchClassList();
});





/**
 * 사용자의 클래스 결석 횟수를 조회하는 함수
 */
async function fetchClassAttend() {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const response = await instance.get(`/users/${payload.id}/attend-classes/count`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_COUNT_ATTEND_SUCCESS') {
			const wrap = document.getElementById('AppliedClassWrap');
			wrap.querySelector('.class_absent span').innerText = `최근 60일 ${response.data.data}회 결석`
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
								
		if (code === 'CLASS_READ_LIST_FAIL') {
			alertDanger('사용자의 쿠킹클래스 결석 횟수 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_APPLY_READ_LIST_FAIL') {
			alertDanger('사용자의 쿠킹클래스 결석 횟수 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'CLASS_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'CLASS_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
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
 * 서버에서 사용자가 신청한 쿠킹클래스 목록 데이터를 가져오는 함수
 */
async function fetchClassList() {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const params = new URLSearchParams({
			status: status,
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/applied-classes?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'CLASS_READ_LIST_SUCCESS') {
			// 전역변수 설정
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			page = response.data.data.number;
			classList = response.data.data.content;
			
			// 랜더링
			renderClassList(classList);
			renderPagination(fetchClassList);
			document.querySelector('.class_util .total').innerText = `총 ${totalElements}개`;
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
										
		if (code === 'CLASS_READ_LIST_FAIL') {
			alertDanger('쿠킹클래스 목록 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_APPLY_READ_LIST_FAIL') {
			alertDanger('쿠킹클래스 목록 조회에 실패했습니다.');
		}
		else if (code === 'CLASS_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'CLASS_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'CLASS_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
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
 * 쿠킹클래스 목록을 화면에 렌더링하는 함수
 */
function renderClassList(classList) {
	
	const wrap = document.getElementById('AppliedClassWrap');
	const container = wrap.querySelector('.class_list');
	container.innerHTML = '';

	// 쿠킹클래스의 신청 목록이 존재하지 않는 경우
	if (classList == null || classList.length === 0) {
		container.style.display = 'none';
		wrap.querySelector('.list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '신청한 쿠킹클래스가 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
	}
	
	// 쿠킹클래스의 신청 목록이 존재하는 경우
	container.style.display = 'block';
	document.querySelector('.list_empty')?.remove();
	
	classList.forEach(classs => {
		
		const li = document.createElement('li');

		// 상태 영역
		const statusDiv = document.createElement('div');
		switch (classs.selected) {
			case 1:
				statusDiv.className = (classs.opened || classs.planned) ? 'status primary' : 'status';
				statusDiv.textContent = '선정되었어요';
				break;
			case 2:
				statusDiv.className = classs.opened ? 'status warning' : 'status';
				statusDiv.textContent = classs.opened ? '대기 중이에요' : '선정 되지 않았어요';
				break;
			case 0:
				statusDiv.className = classs.opened ? 'status danger' : 'status';
				statusDiv.textContent = '선정 되지 않았어요';
				break;
		}

		const classDiv = document.createElement('div');
		classDiv.className = 'class';

		const thumbnailLink = document.createElement('a');
		thumbnailLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		thumbnailLink.className = 'thumbnail';

		const img = document.createElement('img');
		img.src = classs.image;
		thumbnailLink.appendChild(img);

		const infoDiv = document.createElement('div');
		infoDiv.className = 'info';

		const titleLink = document.createElement('a');
		titleLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		titleLink.textContent = classs.title;

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
		infoDiv.append(titleLink, dateP);

		const cancelDiv = document.createElement('div');
		cancelDiv.className = 'cancel_btn';
		
		if (classs.opened) {
			const cancelBtn = document.createElement('button');
			cancelBtn.className = 'btn_outline';
			cancelBtn.textContent = '신청 취소';
			cancelBtn.addEventListener('click', () => deleteApply(classs.apply_id, classs.id));
			cancelDiv.appendChild(cancelBtn);
		}

		classDiv.append(thumbnailLink, infoDiv, cancelDiv);
		li.append(statusDiv, classDiv);
		container.appendChild(li);
	});
}





/**
 * 쿠킹클래스 신청을 삭제하는 함수
 */
async function deleteApply(applyId, classId) {
	
	if (confirm('정말 신청을 취소하시겠습니까?')) {
		try {
			const response = await instance.delete(`/classes/${classId}/applies/${applyId}`, {
				headers: getAuthHeaders()
			});
	
			if (response.data.code === 'CLASS_APPLY_DELETE_SUCCESS') {
				alert('쿠킹클래스 신청이 성공적으로 취소되었습니다.');
				fetchClassList();
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;

			if (code === 'CLASS_APPLY_DELETE_FAIL') {
				alertDanger('쿠킹클래스 신청 취소에 실패했습니다.');
			}
			else if (code === 'CLASS_APPLY_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'USER_INVALID_INPUT') {
				alertDanger('입력값이 유효하지 않습니다.');
			}
			else if (code === 'CLASS_UNAUTHORIZED_ACCESS') {
				alertDanger('로그인되지 않은 사용자입니다.');
			}
			else if (code === 'CLASS_FORBIDDEN') {
				alertDanger('접근 권한이 없습니다.');
			}
			else if (code === 'CLASS_ALREADY_ENDED') {
				alertDanger('쿠킹클래스가 이미 종료되었습니다.');
			}
			else if (code === 'CLASS_APPLY_NOT_FOUND') {
				alertDanger('해당 쿠킹클래스 신청을 찾을 수 없습니다.');
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
}

