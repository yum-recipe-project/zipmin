/**
 * 날짜를 포맷해주는 함수
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
 * 날짜를 요일과 함께 포맷해주는 함수
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
 * 
 */
function formatTime(timeStr) {
	
	const date = new Date(timeStr);
	return date.toTimeString().substring(0, 5);
	
}




/**
 * 
 * 2025.08.01 13:45
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




