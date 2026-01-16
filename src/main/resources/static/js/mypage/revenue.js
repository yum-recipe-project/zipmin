/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let totalElements = 0;
let fundList = [];






/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/');
	}
	
});





/**
 * 초기 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	fetchFundSum();
	fetchFundList();
	
});





/**
 * 사용자의 수익 합계를 조회하는 함수
 */
async function fetchFundSum() {
	
    try {
        const payload = parseJwt(localStorage.getItem('accessToken'));

        const response = await instance.get(`/users/${payload.id}/funds/sum`, {
            headers: getAuthHeaders()
        });
		
        if (response.data.code === 'FUND_READ_SUM_SUCCESS') {
            document.querySelector('.support_point .point span:last-child').textContent = `${response.data.data}원`;
        }
    }
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'FUND_READ_SUM_FAIL') {
	        console.log(error);
		}
		else if (code === 'FUND_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'FUND_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'FUND_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
    }
}





/**
 * 서버에서 사용자의 후원 목록 데이터를 가져오는 함수
 */
async function fetchFundList(scroll = true) {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();
		
		const response = await instance.get(`/users/${payload.id}/funds?${params}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FUND_READ_LIST_SUCCESS') {
			
			page = response.data.data.number; 
			totalPages = response.data.data.totalPages;
			totalElements = response.data.data.totalElements;
			fundList = response.data.data.content;
			
			renderFundList(fundList);
			renderPagination(fetchFundList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			if (scroll) {
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'FUND_READ_LIST_FAIL') {
			console.log(error);
		}
		else if (code === 'FUND_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'FUND_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'FUND_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
}





/**
 * 사용자 후원 목록을 화면에 렌더링하는 함수
 */
function renderFundList(fundList) {
	
    const container = document.querySelector('.support_list');
    container.innerHTML = ''; 
	
	// 후원 목록이 존재하지 않는 경우
    if (fundList == null || fundList.length === 0) {
		container.style.display = 'none';
		document.querySelector('.list_empty')?.remove();

		const wrapper = document.createElement('div');
		wrapper.className = 'list_empty';

		const span = document.createElement('span');
		span.textContent = '후원받은 내역이 없습니다';
		wrapper.appendChild(span);
		container.insertAdjacentElement('afterend', wrapper);

		return;
    }

	// 후원 목록이 존재하는 경우
	container.style.display = 'block';
	document.querySelector('.list_empty')?.remove();
	
    fundList.forEach(revenue => {
		
        const li = document.createElement('li');

        const supportInfoDiv = document.createElement('div');
        supportInfoDiv.className = 'support_info';

        const sponsorDiv = document.createElement('div');
        sponsorDiv.className = 'sponsor';

        const sponsorP1 = document.createElement('p');
        sponsorP1.innerHTML = `<b>${revenue.nickname}</b> 님에게 후원받았습니다`;

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
        recipeImg.src = revenue.image || '/images/common/test.png';
        recipeLink.appendChild(recipeImg);

        const infoDiv = document.createElement('div');
        infoDiv.className = 'info';

        const labelP = document.createElement('p');
        labelP.textContent = '레시피';

        const titleA = document.createElement('a');
        titleA.href = `/recipe/viewRecipe.do?id=${revenue.recipe_id}`;
        titleA.textContent = revenue.title || '제목 없음';

        infoDiv.append(labelP, titleA);
        supportDiv.append(recipeLink, infoDiv);

        li.append(supportInfoDiv, supportDiv);
        container.appendChild(li);
    });
}