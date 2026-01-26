/**
 * 날짜를 포맷해주는 함수 (2025년 8월 15일)
 */
function formatDate(dateString) {
	
	const date = new Date(dateString);
	if (isNaN(date)) return '';
	
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');
	
	return `${year}년 ${month}월 ${day}일`;
	
}




/**
 * 날짜를 포맷해주는 함수 (2025.08.15)
 */
function formatDateDot(dateInput) {
    const parsedDate = new Date(dateInput);

    const year = parsedDate.getFullYear();
    const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
    const day = String(parsedDate.getDate()).padStart(2, '0');

    return `${year}.${month}.${day}`;
}




/**
 * 날짜를 포맷해주는 함수 (2025.08.15(금))
 */
function formatDateWithDay(isoString) {
	
	const date = new Date(isoString);

	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');

	const weekdays = ['일', '월', '화', '수', '목', '금', '토'];
	const dayOfWeek = weekdays[date.getDay()];

	return `${year}.${month}.${day}(${dayOfWeek})`;
	
}




/**
 * 날짜 기간을 포맷해주는 함수 (2025.08.15(금))
 */
function formatDatePeriod(opendateString, closedateString) {
	
	const opendate = new Date(opendateString);
	const closedate = new Date(closedateString);
	if (isNaN(opendate)) return '';
	if (isNaN(closedate)) return '';

	const openyear = opendate.getFullYear();
	const openmonth = String(opendate.getMonth() + 1).padStart(2, '0');
	const openday = String(opendate.getDate()).padStart(2, '0');

	const closeyear = closedate.getFullYear();
	const closemonth = String(closedate.getMonth() + 1).padStart(2, '0');
	const closeday = String(closedate.getDate()).padStart(2, '0');

	return `${openyear}년 ${openmonth}월 ${openday}일 - ${closeyear}년 ${closemonth}월 ${closeday}일`;

}




/**
 * 시간을 포맷해주는 함수 (13:45)
 */
function formatTime(timeStr) {
	
	const date = new Date(timeStr);
	return date.toTimeString().substring(0, 5);
	
}




/**
 * 날짜와 시간을 포맷해주는 함수 (2025.08.15 13:45)
 */
function formatDateTime(dateStr) {
	
	// 날짜
	const date = new Date(dateStr);
	
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');

	// 시간
	const time = new Date(dateStr);
	
	return `${year}.${month}.${day} ${time.toTimeString().substring(0, 5)}`;
	
}










/**
 * 첫 문자만 대문자로 변경하는 함수
 */
function capitalizeFirst(str) {
	
  if (!str) return '';
  return str.charAt(0).toUpperCase() + str.slice(1);
  
}





/**
 * 검색 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderSearchEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'search_empty';

    const img = document.createElement('img');
    img.src = '/images/common/search_empty.png';
    wrapper.appendChild(img);

    const h2 = document.createElement('h2');
    h2.innerHTML = keyword ? `'${keyword}' 에 대한<br/>검색 결과가 없습니다` : '결과가 없습니다';
    wrapper.appendChild(h2);

    const span = document.createElement('span');
    span.textContent = keyword ? '단어의 철자가 정확한지 확인해보세요' : '조건을 변경하거나 초기화해 보세요';
    wrapper.appendChild(span);

    return wrapper;
}





/**
 * 
 */
function alertPrimary(message) {
	area = document.createElement('div');
	area.id = 'alert-area';
	area.style.position = 'fixed';
	area.style.bottom = '40px';
	area.style.left = '50%';
	area.style.transform = 'translateX(-50%)';
	area.style.zIndex = '9999';
	document.body.appendChild(area);
	
	const alertDiv = document.createElement('div');
	alertDiv.className = 'alert customize-alert text-primary rounded-pill alert-light-primary bg-primary-subtle fade show';
	alertDiv.setAttribute('role', 'alert');
	alertDiv.style.fontSize = '1rem';
	alertDiv.style.padding = '0.75rem 1rem';
	
	const flexDiv = document.createElement('div');
	flexDiv.className = 'd-flex align-items-center me-3 me-md-0';
	
	const icon = document.createElement('i');
	icon.className = 'ti ti-info-circle fs-5 me-2 flex-shrink-0 text-primary';

	const textNode = document.createTextNode(message);
	
	flexDiv.appendChild(icon);
	flexDiv.appendChild(textNode);
	alertDiv.appendChild(flexDiv);
	area.appendChild(alertDiv);
	
	// 일정시간 후 페이드아웃 후 제거
	setTimeout(() => {
		alertDiv.classList.remove('show');
		setTimeout(() => alertDiv.remove(), 300);
	}, 2000);
}




/**
 * 
 */
function alertDanger(message) {
	area = document.createElement('div');
	area.id = 'alert-area';
	area.style.position = 'fixed';
	area.style.bottom = '40px';
	area.style.left = '50%';
	area.style.transform = 'translateX(-50%)';
	area.style.zIndex = '9999';
	document.body.appendChild(area);
	
	const alertDiv = document.createElement('div');
	alertDiv.className = 'alert customize-alert rounded-pill alert-light-danger bg-danger-subtle text-danger fade show';
	alertDiv.setAttribute('role', 'alert');
	alertDiv.style.fontSize = '1rem';
	alertDiv.style.padding = '0.75rem 1rem';
	
	const flexDiv = document.createElement('div');
	flexDiv.className = 'd-flex align-items-center me-3 me-md-0';
	
	const icon = document.createElement('i');
	icon.className = 'ti ti-info-circle fs-5 me-2 text-danger';
	
	const textNode = document.createTextNode(message);
	
	flexDiv.appendChild(icon);
	flexDiv.appendChild(textNode);
	alertDiv.appendChild(flexDiv);
	area.appendChild(alertDiv);
	
	// 일정시간 후 페이드아웃 후 제거
	setTimeout(() => {
		alertDiv.classList.remove('show');
		setTimeout(() => alertDiv.remove(), 300);
	}, 2000);
}














/**
 * p 엘리먼트를 생성한다
 */
function createP(text, className) {
	const p = document.createElement('p');
	p.textContent = text;

	if (className) {
		p.className = className;
	}

	return p;
}




/**
 * span 엘리먼트를 생성한다
 */
function createSpan(text, className) {
	const span = document.createElement('span');
	span.textContent = text;

	if (className) {
		span.className = className;
	}

	return span;
}






/**
 * Div 생성
 */
function createDiv(className) {
	const div = document.createElement('div');

	if (className) {
		div.className = className;
	}
	
	return div;
}





/**
 * img 엘리먼트를 생성한다
 */
function createImg(src, className) {
	const img = document.createElement('img');
	img.src = src;

	if (className) {
		img.className = className;
	}

	return img;
}





/**
 * a 엘리먼트를 생성한다
 */
function createLink(href, className) {
	const a = document.createElement('a');
	a.href = href;

	if (className) {
		a.className = className;
	}

	return a;
}





/**
 * button 엘리먼트를 생성한다
 */
function createButton(className) {
	const button = document.createElement('button');

	if (className) {
		button.className = className;
	}

	return button;
}

