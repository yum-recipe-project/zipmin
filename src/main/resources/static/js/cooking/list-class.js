/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let keyword = '';
let category = '';
let status = '';
let classList = [];







/**
 * 탭 메뉴 클릭시 탭 메뉴를 활성화하고 해당 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 검색
	const searchForm = document.querySelector('.search_form[data-type="cooking"]');
	searchForm.addEventListener('submit', function(event) {
		event.preventDefault();
		keyword = searchForm.querySelector('.search_word').value.trim();
		page = 0;
		fetchClassList();
	});
	
	// 카테고리
	document.querySelectorAll('.btn_tab').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelector('.btn_tab.active')?.classList.remove('active');
			this.classList.add('active');
			
			category = this.getAttribute('data-tab');
			page = 0;
			classList = [];
			
			fetchClassList();
		});
	});
	
	// 상태 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			status = btn.dataset.status;
			page = 0;
			classList = [];
			
			fetchClassList();
		});
	});

	fetchClassList();
});





/**
 * 쿠킹클래스 목록 데이터를 가져오는 함수
 */
async function fetchClassList() {
	
	try {
		const params = new URLSearchParams({
			category: category,
			approval: 'APPROVED',
			status: status,
			keyword : keyword,
			page : page,
			size : size
		}).toString();
		
		const response = await fetch(`/classes?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'CLASS_READ_LIST_SUCCESS') {
			
			// 전역변수 설정
			page = result.data.number;
			totalPages = result.data.totalPages;
			classList = result.data.content;
			
			// 렌더링
			renderClassList(classList);
			renderPagination(fetchClassList);
			document.querySelector('.class_util .total').innerText = `총 ${result.data.totalElements}개`;
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.class_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const content = document.querySelector('.class_content');
				content.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('.class_list').style.display = '';
			}
			
			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		
		/***** 에러 코드 추가 *****/
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 
 */
function renderClassList(classList) {
	const container = document.querySelector('.class_list');
	container.innerHTML = '';
	
	classList.forEach(classs => {
		// li
		const li = document.createElement('li');
		li.className = 'class';
		
		// a
		const link = document.createElement('a');
		link.href = `/cooking/viewClass.do?id=${classs.id}`;
		
		// 썸네일 div
		const thumbnailDiv = document.createElement('div');
		thumbnailDiv.className = 'class_thumbnail';
		
		const img = document.createElement('img');
		img.src = classs.image;
		thumbnailDiv.appendChild(img);
		
		// 정보 div
		const infoDiv = document.createElement('div');
		infoDiv.className = 'class_info';
		
		const title = document.createElement('h5');
		title.textContent = classs.title;
		
		const flag = document.createElement('p');
		flag.className = classs.opened ? 'flag open' : 'flag';
		flag.textContent = classs.opened ? '모집중' : '마감';
		
		const date = document.createElement('p');
		date.className = 'date';
		date.textContent = `${formatDate(classs.eventdate)}`;

	    infoDiv.append(title, flag, date);
		link.append(thumbnailDiv, infoDiv);
		li.appendChild(link);
		
		container.appendChild(li);
	});
}























