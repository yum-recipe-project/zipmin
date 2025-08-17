/**
 * 관리자 페이지에서 페이지네이션을 화면에 렌더링하는 함수
 */
function renderAdminPagination(fetchFunction) {
	
	if (totalPages === 0) {
		return;
	}
	
	const window = 5;
	
	const ul = document.querySelector('.pagination ul');
	ul.innerHTML = '';
	
	let startPage = Math.floor(page / window) * window;
	let endPage = Math.min(startPage + window - 1, totalPages - 1);
	
	// 이전 버튼 (5개씩 이동)
	const prevLi = document.createElement('li');
	const prevA = document.createElement('a');
	prevA.href = '#';
	prevA.className = 'prev';
	prevA.dataset.page = Math.max(0, startPage - window);
	prevA.textContent = '<';
	if (startPage === 0) {
		prevA.style.pointerEvents = 'none';
		prevA.style.opacity = '0.3';
	}
	prevLi.appendChild(prevA);
	ul.appendChild(prevLi);
	
	// 번호 버튼
	for (let i = startPage; i <= endPage; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = 'page' + (i === page ? ' active' : '');
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		ul.appendChild(li);
	}
	
	// 다음 버튼 (5개씩 이동)
	const nextLi = document.createElement('li');
	const nextA = document.createElement('a');
	nextA.href = '#';
	nextA.className = 'next';
	nextA.dataset.page = Math.min(totalPages - 1, startPage + window);
	nextA.textContent = '>';
	if (endPage === totalPages - 1) {
		nextA.style.pointerEvents = 'none';
		nextA.style.opacity = '0.3';
	}
	nextLi.appendChild(nextA);
	ul.appendChild(nextLi);
	
	// 이벤트 바인딩
	ul.querySelectorAll('a').forEach(link => {
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
