document.addEventListener("DOMContentLoaded", function() {
	fetchRevenueList();
	
});


/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let revenueList = [];
let totalElements = 0;

/**
 * 서버에서 후원 목록 데이터를 가져오는 함수
 */
async function fetchRevenueList() {
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();
		
		const response = await instance.get(`/users/${id}/revenue?${params}`);
		
		page = response.data.data.number; 
		totalPages = response.data.data.totalPages;
		totalElements = response.data.data.totalElements;
		
		revenueList = response.data.data.content;
		renderRevenueList(revenueList);
		renderPagination();
		
		document.querySelector('.support_util .total').innerText = `총 ${response.data.data.totalElements}개`;
	}
	catch (error) {
		console.log(error);
	}
	
}


/**
 * 후원 내역 목록을 화면에 렌더링하는 함수
 */
function renderRevenueList(revenueList) {
    const container = document.querySelector('.support_list');
    container.innerHTML = ''; 

    if (!revenueList || revenueList.length === 0) {
        container.innerHTML = '<p class="empty">아직 후원받은 내역이 없습니다.</p>';
        return;
    }

    revenueList.forEach(revenue => {
        // li
        const li = document.createElement('li');

        const supportInfoDiv = document.createElement('div');
        supportInfoDiv.className = 'support_info';

        const sponsorDiv = document.createElement('div');
        sponsorDiv.className = 'sponsor';

        const sponsorP1 = document.createElement('p');
        sponsorP1.innerHTML = `<b>${revenue.funder_nickname}</b> 님에게 후원받았습니다`;

        const sponsorP2 = document.createElement('p');
        sponsorP2.textContent = formatDate(revenue.fund_date);

        sponsorDiv.append(sponsorP1, sponsorP2);

        const pointP = document.createElement('p');
        pointP.className = 'point';
        pointP.textContent = `${revenue.point.toLocaleString()}P`;

        supportInfoDiv.append(sponsorDiv, pointP);

        const supportDiv = document.createElement('div');
        supportDiv.className = 'support';

        const recipeLink = document.createElement('a');
        recipeLink.href = `/recipe/viewRecipe.do?id=${revenue.recipe_id}`;
        recipeLink.className = 'thumbnail';

        const recipeImg = document.createElement('img');
        recipeImg.src = revenue.recipe_thumbnail || '/images/common/test.png';
        recipeLink.appendChild(recipeImg);

        const infoDiv = document.createElement('div');
        infoDiv.className = 'info';

        const labelP = document.createElement('p');
        labelP.textContent = '레시피';

        const titleA = document.createElement('a');
        titleA.href = `/recipe/viewRecipe.do?id=${revenue.recipe_id}`;
        titleA.textContent = revenue.recipe_title || '제목 없음';

        const nicknameP = document.createElement('p');
        nicknameP.textContent = revenue.funder_nickname;

        infoDiv.append(labelP, titleA, nicknameP);
        supportDiv.append(recipeLink, infoDiv);

        li.append(supportInfoDiv, supportDiv);
        container.appendChild(li);
    });
}




/**
 * 페이지네이션 렌더링 함수
 */
function renderPagination() {
	const paginationWrap = document.querySelector('.pagination_wrap');
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';
	
	if (totalElements <= size) {
		paginationWrap.style.display = 'none';
		return;
	} else {
		paginationWrap.style.display = 'block';
	}

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

	// 클릭 이벤트 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchRevenueList();
			}
		});
	});
}

