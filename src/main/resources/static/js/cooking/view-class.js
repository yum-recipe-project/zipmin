/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 탭 클릭 이벤트 설정
	const tabItems = document.querySelectorAll('.tab a');
	tabItems.forEach((item) => {
	    item.addEventListener("click", function() {
	        tabItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	    });
	});
	tabItems.forEach(button => button.classList.remove('active'));
	tabItems[0].classList.add('active');
	
	fetchClass();
});





/**
 * 서버에서 쿠킹클래스 상세 정보를 가져오는 함수
 */
async function fetchClass() {
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/classes/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'CLASS_READ_SUCCESS') {
			document.getElementById('applyClassId').value = id;
			document.querySelector('.apply_header h2').innerText = result.data.title;
			document.querySelector('.thumbnail img').src = result.data.image;
			document.querySelector('.intro p').innerText = result.data.introduce;
			document.querySelector('.headcount p').innerText = `${result.data.headcount}명 선정`;
			document.querySelector('.need p').innerText = result.data.need;
			document.querySelector('.place span').innerText = result.data.place;
			document.querySelector('.date span').innerText = `${formatDateWithDay(result.data.eventdate)} ${formatTime(result.data.starttime)} - ${formatTime(result.data.endtime)}`;
			
			renderApplyButton(result.data);
			renderTargetList(result.data.target_list);
			renderScheduleList(result.data.schedule_list);
			renderTutorList(result.data.tutor_list);
		}
		else if (result.code === 'CLASS_NOT_FOUND') {
			alert('해당 쿠킹클래스를 찾을 수 없습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'CLASS_TARGET_READ_LIST_FAIL') {
			alert('교육대상 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'CLASS_SCHEDULE_READ_LIST_FAIL') {
			alert('교육일정 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'CLASS_TUTOR_READ_LIST_FAIL') {
			alert('강사 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else {
			// alert('서버 내부에서 오류가 발생했습니다.');
			// location.href = '/cooking/listClass.do';
		}
	}
	catch (error) {
		console.log(error);
	}
}





/**
 * 쿠킹클래스 교육대상 목록을 화면에 렌더링하는 함수
 */
function renderTargetList(list) {
	
	const container = document.querySelector('.target');
	if (!container) return;
	container.innerHTML = '';
	
	const title = document.createElement('h4');
	title.textContent = '이런 분께 추천해요';
	container.appendChild(title);
	
	list.forEach(target => {
		const p = document.createElement('p');
		p.textContent = target.content;
		container.appendChild(p);
	});
	
}





/**
 * 쿠킹클래스 교육일정 목록을 화면에 렌더링하는 함수
 */
function renderScheduleList(list) {
	
	const ul = document.querySelector('.curriculum_list');
	if (!ul) return;
	ul.innerHTML = '';

	list.forEach((schedule, index) => {
		const li = document.createElement('li');

		const metaDiv = document.createElement('div');
		metaDiv.className = 'meta';

		const badge = document.createElement('span');
		badge.className = 'badge';
		badge.textContent = `${index + 1}교시`;

		const timeSpan = document.createElement('span');
		timeSpan.textContent = `${formatTime(schedule.starttime)} - ${formatTime(schedule.endtime)}`;

		metaDiv.append(badge, timeSpan);

		const titleDiv = document.createElement('div');
		titleDiv.className = 'title';

		const strong = document.createElement('strong');
		strong.textContent = schedule.title;

		const subSpan = document.createElement('span');
		subSpan.textContent = '';

		titleDiv.append(strong, subSpan);

		li.append(metaDiv, titleDiv);
		ul.appendChild(li);
	});
	
}




/**
 * 쿠킹클래스 강사 목록을 화면에 렌더링하는 함수
 */
function renderTutorList(list) {
	
	const ul = document.querySelector('.tutor_list');
	if (!ul) return;
	ul.innerHTML = '';

	list.forEach(tutor => {
		const li = document.createElement('li');
		li.className = 'lecturer';

		const photoDiv = document.createElement('div');
		photoDiv.className = 'photo';

		const img = document.createElement('img');
		img.src = tutor.image;
		photoDiv.appendChild(img);

		const profileDiv = document.createElement('div');
		profileDiv.className = 'profile';

		const name = document.createElement('h5');
		name.textContent = tutor.name;
		profileDiv.appendChild(name);
		
		const careerLines = tutor.career.split(/[\n\r]+|,|·/);
		careerLines.forEach(line => {
			const p = document.createElement('p');
			p.textContent = line.trim();
			profileDiv.appendChild(p);
		});

		li.append(photoDiv, profileDiv);
		ul.appendChild(li);
	});
	
}




/**
 * 클래스 신청 상태에 따라 신청 버튼을 화면에 렌더링하는 함수
 */
function renderApplyButton(apply) {
	
	const wrap = document.getElementById('applyWrap');
	const container = wrap.querySelector('.apply_header');
	if (!container) return;
	
	// 기존 버튼 제거
	const oldButton = container.querySelector('button');
	if (oldButton) oldButton.remove();

	const button = document.createElement('button');
	button.type = 'button';
	
	if (apply.opened) {
		if (apply.applied === true) {
			button.disabled = true;
			button.className = 'btn_gray_wide';
			button.innerText = '신청 완료';
		}
		else {
			button.disabled = false;
			button.className = 'btn_primary_wide';
			button.innerText = '신청하기';
			button.setAttribute('data-bs-toggle', 'modal');
			button.setAttribute('data-bs-target', '#applyClassModal');
		}
	}
	else {
		button.disabled = true;
		button.className = 'btn_gray_wide';
		button.innerText = '신청 완료';
	}
	
	// 신청하기 버튼 클릭시 로그인 여부를 확인
	button.addEventListener('click', function() {
		if (!isLoggedIn()) {
			redirectToLogin();
			bootstrap.Modal.getInstance(document.getElementById('applyClassModal'))?.hide();
		}
	});

	container.appendChild(button);
}



