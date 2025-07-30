/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	if (!isLoggedIn()) {
		redirectToLogin('/');
		return;
	}

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		console.log(error);
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
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const params = new URLSearchParams({
			sort: sort,
			page: page,
			size: size
		}).toString();
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization' : `Bearer ${token}`
		}
		
		const response = await instance.get(`/users/${payload.id}/classes?${params}`, {
			headers: headers
		});
		
		if (response.data.code === 'USER_READ_LIST_SUCCESS') {
			
			console.log(response);
			
			totalPages = response.data.data.totalPages;
			page = response.data.data.number;
			classList = response.data.data.content;
			
			renderClassList(response.data.data.content);
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
	if (!container) return;
	container.innerHTML = '';

	classList.forEach(classs => {
		const li = document.createElement('li');

		const statusDiv = document.createElement('div');
		statusDiv.className = 'status';
		statusDiv.textContent = classs.approval === 1 ? '승인 완료' : '승인 대기중';
		li.appendChild(statusDiv);
		// 클래스 효과 있을수도 ..

		const classDiv = document.createElement('div');
		classDiv.className = 'class';
		
		const thumbnailLink = document.createElement('a');
		thumbnailLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		thumbnailLink.className = 'thumbnail';

		const img = document.createElement('img');
		// img.src = classs.image || '/images/common/test.png';
		img.src = '/images/common/test.png';
		thumbnailLink.appendChild(img);
		classDiv.appendChild(thumbnailLink);

		const infoDiv = document.createElement('div');
		infoDiv.className = 'info';

		// 제목
		const titleLink = document.createElement('a');
		titleLink.href = `/cooking/viewClass.do?id=${classs.id}`;
		titleLink.textContent = classs.title;
		infoDiv.appendChild(titleLink);

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

		classDiv.appendChild(infoDiv);

		const cancelDiv = document.createElement('div');
		cancelDiv.className = 'cancel_btn';

		const btn = document.createElement('button');
		btn.className = 'btn_outline';
		btn.textContent = '신청서 보기';
		btn.onclick = function () {
			location.href = `/mypage/class/application.do?classId=${classs.id}`;
		};

		cancelDiv.appendChild(btn);
		classDiv.appendChild(cancelDiv);

		li.appendChild(classDiv);
		container.appendChild(li);
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
				fetchGuideList();
			}
		});
	});
}




