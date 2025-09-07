/**
 * 전역 변수
 */
let originPortion = 0;
let photoMode = true;





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
	const tipForm = document.getElementById('viewRecipeTipForm');
	const supportForm = document.getElementById('viewRecipeSupportForm');
	const reviewCommentForm = document.getElementById('viewRecipeReviewCommentForm');
	
	
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
			if (result.data.liked) {
				basicForm.querySelector('.btn_icon.like img').src = '/images/recipe/star_full_1a7ce2.png'
			 	basicForm.querySelector('.btn_icon.like').classList.add('active');
			}
			renderCategoryList(result.data.category_list);
			
			// 재료
			originPortion = parseInt(result.data.portion, 10);
			stockForm.querySelector('.recipe_portion select').value = result.data.portion;
			renderStockList(result.data.stock_list);
			renderMemoList(result.data.stock_list);
			
			// 조리 순서
			renderStepList(result.data.step_list);
			
			// 레시피 팁
			tipForm.querySelector('.recipe_tip p').innerText = result.data.tip;
			
			// 구독 및 후원
			supportForm.querySelector('.recipe_writer img').src = result.data.avatar;
			supportForm.querySelector('.recipe_writer h5').innerText = result.data.nickname;
			supportForm.querySelector('.recipe_writer p').innerText = `구독자 ${result.data.follower}명`;
			
			// 리뷰 수
			reviewCommentForm.querySelector('.review_count').innerText = result.data.reviewcount;
			reviewCommentForm.querySelector('.comment_count').innerText = result.data.commentcount;
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
 * 레시피 카테고리 목록을 화면에 렌더링하는 함수
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
 * 레시피 재료 목록을 화면에 렌더링하는 함수
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
 * 레시피 조리 과정을 화면에 렌더링하는 함수
 */
function renderStepList(stepList) {
	const stepForm = document.getElementById('viewRecipeStepForm');
	const container = stepForm.querySelector('.step_list');
	container.innerHTML = '';

    stepList.forEach((step, index) => {
		const li = document.createElement('li');
		
		const contentDiv = document.createElement('div');
		contentDiv.className = 'step_content';
		
		const h5 = document.createElement('h5');
		h5.textContent = `STEP${index + 1}`;
		contentDiv.appendChild(h5);
		
		const p = document.createElement('p');
		p.textContent = step.content;
		contentDiv.appendChild(p);
		li.appendChild(contentDiv);
		
		const imageDiv = document.createElement('div');
		imageDiv.className = 'step_image';
		
		const img = document.createElement('img');
		img.src = step.image;
		imageDiv.appendChild(img);
		
		if (step.image) {
			li.appendChild(imageDiv);
		}
		
		container.appendChild(li);
	});
}





/**
 * 레시피 재료 목록의 사진 토글하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const stepForm = document.getElementById('viewRecipeStepForm');
	
	document.getElementById('togglePhotoButton').addEventListener('click', function(event) {
		event.preventDefault();
		photoMode = !photoMode;
		
		if (photoMode) {
			document.getElementById('togglePhotoButton').innerHTML = '<img src="/images/recipe/photo_999.png">사진 숨기기';
			stepForm.querySelectorAll('.step_list .step_image').forEach(step => {
				step.style.display = 'block';
			});
		}
		else {
			document.getElementById('togglePhotoButton').innerHTML = '<img src="/images/recipe/photo_999.png">사진 보기';
			stepForm.querySelectorAll('.step_list .step_image').forEach(step => {
				step.style.display = 'none';
			});
		}
	});
});




