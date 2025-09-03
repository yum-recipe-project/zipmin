/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const tabItems = document.querySelectorAll('.tab_button button');
    const contentItems = document.querySelectorAll('.tab_content');

    // 탭 클릭 이벤트 설정
    tabItems.forEach((item, index) => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            
            tabItems.forEach(button => button.classList.remove('active'));
            this.classList.add('active');
            
            contentItems.forEach(content => content.style.display = 'none');
            contentItems[index].style.display = 'block';
        });
    });

    // 선택된 탭 활성화
    tabItems.forEach(button => button.classList.remove('active'));
    contentItems.forEach(content => content.style.display = 'none');

    tabItems[0].classList.add('active');
    contentItems[0].style.display = 'block';
});





/**
 * 레시피의 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 레시피 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
				
		const response = await fetch(`/recipes/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'RECIPE_READ_SUCCESS') {
			
			// 레시피 정보
			document.getElementById('title').innerText = result.data.title;
			document.getElementById('level').innerText = result.data.cooklevel;
			document.getElementById('time').innerText = result.data.cooktime;
			document.getElementById('spicy').innerText = result.data.spicy;
			document.getElementById('introduce').innerText = result.data.introduce;
			document.getElementById('tip').innerText = result.data.tip;
			document.querySelectorAll('.nickname[data-id]').forEach(nickname => { nickname.innerText = result.data.nickname; });
			
			// 재료
			document.getElementById('servingInput').value = result.data.portion;
			renderStockList(result.data.stock_list);
			renderMemoList(result.data.stock_list);
			
			// 조리 순서
			renderStepList(result.data.step_list);
			
			// 리뷰 수
			document.querySelectorAll('.review_count[data-id]').forEach(reviewcount => { reviewcount.innerText = result.data.reviewcount; });
			document.querySelectorAll('.comment_count[data-id]').forEach(commentcount => { commentcount.innerText = result.data.commentcount; });
			
			// ***** 구독자 정보 렌더링 *****
		}
		
		if (result.code === 'RECIPE_READ_FAIL') {
			alert('레시피 조회에 실패했습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'RECIPE_CATEGORY_READ_LIST_FAIL') {
			alert('레시피 조회에 실패했습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'RECIPE_STOCK_READ_LIST_FAIL') {
			alert('레시피 조회에 실패했습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'RECIPE_STEP_READ_LIST_FAIL') {
			alert('레시피 조회에 실패했습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'USER_INVALID_INPUT') {
			alert('입력값이 유효하지 않습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'RECIPE_NOT_FOUND') {
			alert('해당 레시피를 찾을 수 없습니다.');
			location.href = '/recipe/listRecipe.do';
		}
		else if (result.code === 'USER_NOT_FOUND') {
			alert('해당 사용자를 찾을 수 없습니다.');
			location.href = '/recipe/listRecipe.do';
		}
 		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/recipe/listRecipe.do';
		}
	}
	catch(error) {
		console.log(error);
	}
	
});





/**
 * 재료 목록 렌더링
 */
function renderStockList(stockList) {
	const ingredientElement = document.getElementById('ingredient');
	ingredientElement.innerHTML = '';
	
	stockList.forEach(ingredient => {
		const tr = document.createElement('tr');
		
		// 이름
		const tdName = document.createElement('td');
		tdName.textContent = ingredient.name;
		tr.appendChild(tdName);
		
		// 양
		const tdAmount = document.createElement('td');
		tdAmount.textContent = `${ingredient.amount}${ingredient.unit}`;
		tr.appendChild(tdAmount);
		
		// 비고
		const tdNote = document.createElement('td');
		tdNote.textContent = ingredient.note || '';
		tr.appendChild(tdNote);
		
		ingredientElement.appendChild(tr);
	});
}





/**
 * 장보기 메모 렌더링 (모달창)
 */
function renderMemoList(stockList) {
	const memoElement = document.getElementById('memo');
	memoElement.innerHTML = '';
	
	stockList.forEach((ingredient, index) => {
		const tr = document.createElement('tr');
		
		// 이름
		const tdName = document.createElement('td');
		tdName.textContent = ingredient.name;
		tr.appendChild(tdName);
		
		// 수량
		const tdAmount = document.createElement('td');
		tdAmount.textContent = `${ingredient.amount}${ingredient.unit}`;
		tr.appendChild(tdAmount);
		
		// 체크박스
		const tdCheckbox = document.createElement('td');
		
		const checkbox = document.createElement('input');
		checkbox.type = 'checkbox';
		checkbox.id = `addMemo_${index}`;
		checkbox.name = 'ingredient';
		checkbox.checked = true;
		
		const label = document.createElement('label');
		label.setAttribute('for', `addMemo_${index}`);
		
		tdCheckbox.appendChild(checkbox);
		tdCheckbox.appendChild(label);
		tr.appendChild(tdCheckbox);
		
		memoElement.appendChild(tr);
	});
}





/**
 * 조리 순서 렌더링
 */
function renderStepList(stepList) {
	const stepElement = document.getElementById('step');
	stepElement.innerHTML = '';

    stepList.forEach((step, index) => {
		const li = document.createElement('li');
		
		// 설명 div
		const descDiv = document.createElement('div');
		descDiv.className = 'description';
		
		const h5 = document.createElement('h5');
		h5.textContent = `STEP${index + 1}`;
		descDiv.appendChild(h5);
		
		const p = document.createElement('p');
		
		const span = document.createElement('span');
		span.className = 'hidden';
		span.textContent = `${index + 1}. `;
		p.appendChild(span);
		
		const descText = document.createTextNode(step.content);
		p.appendChild(descText);
		
		descDiv.appendChild(p);
		li.appendChild(descDiv);
		
		// 이미지 div
		const imgDiv = document.createElement('div');
		imgDiv.className = 'image';
		
		const img = document.createElement('img');
		img.src = step.image ? step.image : '/images/common/test.png';
		imgDiv.appendChild(img);
		
		li.appendChild(imgDiv);
		
		stepElement.appendChild(li);
	});
}





/**
 * 구독자 정보 렌더링
 */
/*
function renderFollowSection(followerCount, isFollow) {
    // 구독자 수
    const followerElement = document.getElementById("follower");
    if (followerElement) {
        followerElement.innerText = followerCount;
    }

    // 구독 버튼
    const followButton = document.getElementById("followButton");
    if (followButton) {
        // 버튼 클래스 토글
        followButton.classList.remove("btn_outline", "btn_dark");
        if (isFollow) {
            followButton.classList.add("btn_outline");
            followButton.innerText = "구독 중";
        } else {
            followButton.classList.add("btn_dark");
            followButton.innerText = "구독";
        }
    }
}
*/










