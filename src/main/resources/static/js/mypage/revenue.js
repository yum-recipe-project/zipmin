document.addEventListener("DOMContentLoaded", function() {
	fetchRevenueList();
	fetchRevenueTotal();
});


/**
 * 전역 변수
 */
let page = 0;
let size = 10;
let totalPages = 0;
let revenueList = [];
let totalElements = 0;
let totalRevenue = 0;  


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
		renderPagination(fetchRevenueList);
		
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

//        const nicknameP = document.createElement('p');
//        nicknameP.textContent = revenue.funder_nickname;

//        infoDiv.append(labelP, titleA, nicknameP);
        infoDiv.append(labelP, titleA);
        supportDiv.append(recipeLink, infoDiv);

        li.append(supportInfoDiv, supportDiv);
        container.appendChild(li);
    });
}





/**
 * 사용자 총 수익 금액을 가져오는 함수
 */
async function fetchRevenueTotal() {
    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;

        const response = await fetch(`/users/${id}/revenue/total`, {
            method: 'GET',
            headers: getAuthHeaders()
        });

        const result = await response.json();

        if (response.ok) {
			totalRevenue = result || 0;

            const pointDisplay = document.querySelector('.support_point .point span:last-child');
            if (pointDisplay) {
                pointDisplay.textContent = `${totalRevenue.toLocaleString()}원`;
            }

        } else if (result.code === 'USER_INVALID_INPUT') {
            alertDanger('입력값이 유효하지 않습니다.');
        } else if (result.code === 'USER_NOT_FOUND') {
            alertDanger('해당 사용자를 찾을 수 없습니다.');
        } else {
            alertDanger('서버 내부에서 오류가 발생했습니다.');
        }

    } catch (error) {
        console.log(error);
        alertDanger('알 수 없는 오류가 발생했습니다.');
    }
}

