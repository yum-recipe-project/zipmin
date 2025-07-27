/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.tab a');
	// 탭 클릭 이벤트 설정
	tabItems.forEach((item) => {
	    item.addEventListener("click", function() {
	        tabItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	    });
	});
	tabItems.forEach(button => button.classList.remove('active'));
	tabItems[0].classList.add('active');
});




/**
 * 서버에서 쿠킹클래스 상세 정보를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		const params = new URLSearchParams(window.location.search);
		const id = params.get('id');
		
		const response = await fetch(`/classes/${id}`);
		const result = await response.json();
		
		if (result.code === 'COOKING_READ_SUCCESS') {
			
			// 추후에 실제 이미지로 수정
			document.querySelector('.apply_header h2').innerText = result.data.title;
			document.querySelector('.thumbnail img').src = '/images/common/test.png';
			document.querySelector('.intro p').innerText = result.data.introduce;
			document.querySelector('.headcount p').innerText = `${result.data.headcount}명 선정`;
			document.querySelector('.need p').innerText = result.data.need;
			document.querySelector('.place span').innerText = result.data.place;
			document.querySelector('.date span').innerText = `${formatDateWithDay(result.data.eventdate)} ${formatTime(result.data.starttime)} - ${formatTime(result.data.endtime)}`;
			
			renderTargetList(result.data.target_list);
			renderScheduleList(result.data.schedule_list);
			renderTutorList(result.data.tutor_list);
		}
		else if (result.code === 'COOKING_NOT_FOUND') {
			alert('해당 쿠킹클래스를 찾을 수 없습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'COOKING_TARGET_READ_LIST_FAIL') {
			alert('교육대상 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'COOKING_SCHEDULE_READ_LIST_FAIL') {
			alert('교육일정 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else if (result.code === 'COOKING_TUTOR_READ_LIST_FAIL') {
			alert('강사 목록 조회에 실패했습니다.');
			location.href = '/cooking/listClass.do';
		}
		else {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/cooking/listClass.do';
		}
	}
	catch (error) {
		console.log(error);
	}

});





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
		subSpan.textContent = ''; // 필요 시 서브 제목

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
		// img.src = tutor.image || '/images/common/test.png';
		img.src = '/images/common/test.png';
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



