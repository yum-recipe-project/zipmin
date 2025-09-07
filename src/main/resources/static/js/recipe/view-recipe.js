/**
 * 전역 변수
 */
let originPortion = 0;





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
	
	const basicForm = document.getElementById('viewRecipeBasicForm');
	const stockForm = document.getElementById('viewRecipeStockForm');
	
	// 레시피 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
				
		const response = await fetch(`/recipes/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'RECIPE_READ_SUCCESS') {
			
			// 기본 정보
			basicForm.querySelector('.recipe_title').innerText = result.data.title;
			basicForm.querySelector('.recipe_cooklevel').innerText = result.data.cooklevel;
			basicForm.querySelector('.recipe_cooktime').innerText = result.data.cooktime;
			basicForm.querySelector('.recipe_spicy').innerText = result.data.spicy;
			basicForm.querySelector('.recipe_writer img').src = result.data.avatar;
			basicForm.querySelector('.recipe_writer span').innerText = result.data.nickname;
			basicForm.querySelector('.recipe_introduce').innerText = result.data.introduce;
			renderCategoryList(result.data.category_list);
			
			// 재료
			originPortion = parseInt(result.data.portion, 10);
			stockForm.querySelector('.recipe_portion select').value = result.data.portion;
			renderStockList(result.data.stock_list);
			renderMemoList(result.data.stock_list);
			
			document.getElementById('tip').innerText = result.data.tip;
			document.querySelectorAll('.nickname[data-id]').forEach(nickname => nickname.innerText = result.data.nickname);
			
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
 * 레시피의 인분 변경시 레시피 재료 목록의 용량을 계산하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {

	const stockForm = document.getElementById('viewRecipeStockForm');
	stockForm.querySelector('.recipe_portion select').addEventListener('change', function() {
		const portion = parseInt(stockForm.querySelector('.recipe_portion select').value, 10);
		stockForm.querySelectorAll('.stock_list td.amount').forEach(td => {
			const originAmount = parseFloat(td.dataset.amount);
			const unit = td.dataset.unit;			
			const amount = Math.round((originAmount / originPortion) * portion * 100) / 100;
			td.textContent = `${amount}${unit}`;
		});
	});
	
});





/**
 * 
 */
function renderCategoryList(categoryList) {
	const basicForm = document.getElementById('viewRecipeBasicForm');
	const container = basicForm.querySelector('.recipe_category');
	container.innerHTML = '';
	
	categoryList.forEach(category => {
		const span  = document.createElement('span');
		span.textContent = `# ${category.tag}`;
		span.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/recipe/listRecipe.do?category=${category.tag}`;
		});
		
		container.appendChild(span);
	});
}





/**
 * 재료 목록 렌더링
 */
function renderStockList(stockList) {
	const stockForm = document.getElementById('viewRecipeStockForm');
	const container = stockForm.querySelector('.stock');
	container.innerHTML = '';
	
	stockList.forEach(stock => {
		const tr = document.createElement('tr');
		
		// 이름
		const tdName = document.createElement('td');
		tdName.textContent = stock.name;
		tr.appendChild(tdName);
		
		// 양
		const tdAmount = document.createElement('td');
		tdAmount.className = 'amount';
		tdAmount.dataset.amount = stock.amount;
		tdAmount.dataset.unit = stock.unit;
		tdAmount.textContent = `${stock.amount}${stock.unit}`;
		tr.appendChild(tdAmount);
		
		// 비고
		const tdNote = document.createElement('td');
		tdNote.textContent = stock.note;
		tr.appendChild(tdNote);
		
		container.appendChild(tr);
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










