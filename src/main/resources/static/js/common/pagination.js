/**
 * 페이지네이션을 생성하는 함수
 */
function renderPagination(fetchFunction) {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';
	
	if (totalPages === 0) {
		return;
	}
	
	let startPage = Math.floor(page / 5) * 5;
	let endPage = Math.min(startPage + 5 - 1, totalPages - 1);
	
	// 이전 버튼 (5개씩 이동)
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = Math.max(0, startPage - 5);
	
	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	
	if (startPage === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0.3';
	}
	
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = startPage; i <= endPage; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = 'page' + (i === page ? ' active' : '');
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼 (5개씩 이동)
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = Math.min(totalPages - 1, startPage + 5);

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	
	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0.3';
	}

	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 데이터 패치
	pagination.querySelectorAll('a').forEach(link => {
		link.addEventListener('click', function (event) {
			event.preventDefault();
			const newPage = Number(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages) {
				page = newPage;
				if (typeof fetchFunction === 'function') {
					fetchFunction();
				}
			}
		});
	});
}





/**
 * 관리자 페이지에서 페이지네이션을 화면에 렌더링하는 함수
 */
function renderAdminPagination(fetchFunction) {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';
	
	if (totalPages === 0) {
		return;
	}
	
	let startPage = Math.floor(page / 5) * 5;
	let endPage = Math.min(startPage + 5 - 1, totalPages - 1);
	
	// 이전 버튼 (5개씩 이동)
	const prevLi = document.createElement('li');
	const prevA = document.createElement('a');
	prevA.href = '#';
	prevA.className = 'prev';
	prevA.dataset.page = Math.max(0, startPage - 5);
	prevA.textContent = '<';
	if (startPage === 0) {
		prevA.style.pointerEvents = 'none';
		prevA.style.opacity = '0.3';
	}
	prevLi.appendChild(prevA);
	pagination.appendChild(prevLi);
	
	// 페이지 번호
	for (let i = startPage; i <= endPage; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = 'page' + (i === page ? ' active' : '');
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}
	
	// 다음 버튼 (5개씩 이동)
	const nextLi = document.createElement('li');
	const nextA = document.createElement('a');
	nextA.href = '#';
	nextA.className = 'next';
	nextA.dataset.page = Math.min(totalPages - 1, startPage + 5);
	nextA.textContent = '>';
	if (endPage === totalPages - 1) {
		nextA.style.pointerEvents = 'none';
		nextA.style.opacity = '0.3';
	}
	nextLi.appendChild(nextA);
	pagination.appendChild(nextLi);
	
	// 데이터 패치
	pagination.querySelectorAll('a').forEach(link => {
		link.addEventListener('click', function (event) {
			event.preventDefault();
			const newPage = Number(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages) {
				page = newPage;
				if (typeof fetchFunction === 'function') {
					fetchFunction();
				}
			}
		});
	});
}

