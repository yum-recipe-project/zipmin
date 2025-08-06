/**
 * 전역변수
 */
let totalPages = 0;
let page = 0;
const size = 10;
let megazineList = [];





/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	fetchMegazineList();
});




/**
 * 서버에서 매거진 목록 데이터를 가져오는 함수
 */
async function fetchMegazineList() {
	
	try {
		const params = new URLSearchParams({
			page : page,
			size : size
		}).toString();

		const headers = {
			'Content-Type': 'application/json'
		};

		const response = await fetch(`/megazines?${params}`, {
			method: 'GET',
			headers: headers
		});

		const result = await response.json();
		
		console.log(result);

		if (result.code === 'MEGAZINE_READ_LIST_SUCCESS') {

			totalPages = result.data.totalPages;
			page = result.data.number;
			megazineList = result.data.content;
			
			renderMegainzeList(result.data.content);
			renderPagination();
			
			document.querySelector('.total').innerText = `총 ${result.data.totalElements}개`;
		}
		
		// ***** 여기에 에러코드 더 추가해야함 ***
		
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 매거진 목록을 화면에 렌더링하는 함수
 */
function renderMegainzeList(megazineList) {
	const container = document.querySelector('.megazine_list');
	container.innerHTML = '';

	megazineList.forEach((megazine, index) => {
		const tr = document.createElement('tr');

		// No
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		noH6.textContent = index + 1;
		noTd.appendChild(noH6);

		// 제목
		const titleTd = document.createElement('td');
		const titleH6 = document.createElement('h6');
		titleH6.className = 'fw-semibold mb-0';
		titleH6.textContent = megazine.title;
		titleTd.appendChild(titleH6);

		// 참여 기간
		const periodTd = document.createElement('td');
		const periodH6 = document.createElement('h6');
		periodH6.className = 'fw-semibold mb-0';
		periodH6.textContent = `${formatDate(megazine.postdate)}`;
		periodTd.appendChild(periodH6);

		// 댓글 수
		const commentTd = document.createElement('td');
		const commentH6 = document.createElement('h6');
		commentH6.className = 'fw-semibold mb-0';
		commentH6.textContent = `${megazine.commentcount || 0}개`;
		commentTd.appendChild(commentH6);

		// 기능 (드롭다운)
		const actionTd = document.createElement('td');
		const dropdownDiv = document.createElement('div');
		dropdownDiv.className = 'dropdown dropstart';

		const dropdownBtn = document.createElement('a');
		dropdownBtn.href = 'javascript:void(0)';
		dropdownBtn.className = 'text-muted';
		dropdownBtn.setAttribute('id', `dropdownMenuButton${index}`);
		dropdownBtn.setAttribute('data-bs-toggle', 'dropdown');
		dropdownBtn.setAttribute('aria-expanded', 'false');
		dropdownBtn.innerHTML = `<i class="ti ti-dots-vertical fs-6"></i>`;

		const ul = document.createElement('ul');
		ul.className = 'dropdown-menu';
		ul.setAttribute('aria-labelledby', `dropdownMenuButton${index}`);

		const editLi = document.createElement('li');
		editLi.innerHTML = `
			<a class="dropdown-item d-flex align-items-center gap-3" href="#"><i class="fs-4 ti ti-edit"></i>Edit</a>`;

		const deleteLi = document.createElement('li');
		deleteLi.innerHTML = `<a class="dropdown-item d-flex align-items-center gap-3" href="#"><i class="fs-4 ti ti-trash"></i>Delete</a>`;

		ul.append(editLi, deleteLi);
		dropdownDiv.append(dropdownBtn, ul);
		actionTd.appendChild(dropdownDiv);

		// 조립
		tr.append(noTd, titleTd, periodTd, commentTd, actionTd);
		container.appendChild(tr);
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
				fetchMegazineList();
			}
		});
	});
}




















