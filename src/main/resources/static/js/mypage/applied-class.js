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
 * 전역 변수 선언
 */
let sort = '';
let totalPages = 0;
let page = 0;
let size = 10;
let classList = [];





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
			
			fetchClassList();
		});
	});
	
	fetchClassList();
});




/**
 * 서버에서 개설한 쿠킹클래스 목록 데이터를 가져오는 함수
 */
async function fetchClassList() {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const params = new URLSearchParams({
			sort: sort,
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/applied-classes?${params}`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		if (response.data.code === 'CLASS_READ_LIST_SUCCESS') {
			
			totalPages = response.data.data.totalPages;
			page = response.data.data.number;
			classList = response.data.data.content;
			
			renderClassList(classList);
			renderPagination();
			
			document.querySelector('.class_util .total').innerText = `총 ${response.data.data.totalElements}개`;
		}
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 쿠킹클래스 목록을 화면에 렌더링하는 함수
 */
function renderClassList(classList) {
	const container = document.querySelector('.class_list');
	container.innerHTML = '';

	classList.forEach(classs => {
		const li = document.createElement('li');
		li.dataset.applyId = classs.apply_id;

		// 상태 영역
		const statusDiv = document.createElement('div');
		statusDiv.className = 'status';

		if (classs.status === 'waiting') {
			statusDiv.textContent = '대기 중이에요';
		}
		else if (classs.status === 'applied') {
			statusDiv.textContent = '신청 완료';
		}
		else if (classs.status === 'rejected') {
			statusDiv.textContent = '선정 되지 않았어요';
		}

		// 클래스 정보 전체 wrapper
		const classDiv = document.createElement('div');
		classDiv.className = 'class';

		// 썸네일 링크 및 이미지
		const thumbnailLink = document.createElement('a');
		thumbnailLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		thumbnailLink.className = 'thumbnail';

		const img = document.createElement('img');
		img.src = classs.image;
		thumbnailLink.appendChild(img);

		// info 영역
		const infoDiv = document.createElement('div');
		infoDiv.className = 'info';

		const titleLink = document.createElement('a');
		titleLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		titleLink.textContent = classs.title;

		const dateP = document.createElement('p');

		// 수업일정
		const scheduleSpan = document.createElement('span');
		const scheduleEm = document.createElement('em');
		scheduleEm.textContent = '수업일정';
		scheduleSpan.appendChild(scheduleEm);
		scheduleSpan.append(`${formatDateWithDay(classs.eventdate)} ${formatTime(classs.starttime)}-${formatTime(classs.endtime)}`);
		dateP.appendChild(scheduleSpan);

		// 선정발표
		const deadlineSpan = document.createElement('span');
		const deadlineEm = document.createElement('em');
		deadlineEm.textContent = '선정발표';
		deadlineSpan.appendChild(deadlineEm);
		deadlineSpan.append(`${formatDateWithDay(classs.noticedate)} ${formatTime(classs.starttime)}`);
		dateP.appendChild(deadlineSpan);

		infoDiv.appendChild(dateP);
		infoDiv.append(titleLink, dateP);

		// 취소 버튼
		const cancelDiv = document.createElement('div');
		cancelDiv.className = 'cancel_btn';

		const cancelBtn = document.createElement('button');
		cancelBtn.className = 'btn_outline';
		cancelBtn.textContent = '신청 취소';
		cancelBtn.addEventListener('click', function(event) {
			event.preventDefault();
			if (confirm('정말 신청을 취소하시겠습니까?')) {
				deleteApply(classs.apply_id, classs.id);
			}
		});
		cancelDiv.appendChild(cancelBtn);

		// 조립
		classDiv.append(thumbnailLink, infoDiv, cancelDiv);
		li.append(statusDiv, classDiv);
		container.appendChild(li);
	});
}




async function deleteApply(id, classId) {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));

		const data = {
			id: id,
			class_id: classId
		}

		const response = await instance.delete(`/classes/${payload.id}/applies`, {
			data: data,
			headers: getAuthHeaders()
		});

		console.log(response);
		
		if (response.data.code === 'CLASS_APPLY_DELETE_SUCCESS') {
			alert('쿠킹클래스 신청이 성공적으로 취소되었습니다.');
			const applyElement = document.querySelector(`.class_list li[data-apply-id='${id}']`);
			if (applyElement) applyElement.remove();
		}
		
	}
	catch (error) {
		/******** 여기에 에러 코드 추가 !!!!!! ********/
		console.log(error);
	}
	
}




// 삭제 동작 -> 신청 취소는 선정 이후에는 못하게 하고
// 발표일이 지나서 보이도록 선정 여부 보이도록 하기





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
				fetchGuideList();
			}
		});
	});
}
