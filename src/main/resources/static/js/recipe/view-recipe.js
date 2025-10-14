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
	
	const basicWrap = document.getElementById('viewRecipeBasicWrap');
	const stockWrap = document.getElementById('viewRecipeStockWrap');
	const tipWrap = document.getElementById('viewRecipeTipWrap');
	const supportWrap = document.getElementById('viewRecipeSupportWrap');
	const reviewCommentWrap = document.getElementById('viewRecipeReviewCommentWrap');
	
	// 레시피 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
				
		const response = await fetch(`/recipes/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'RECIPE_READ_SUCCESS') {
			
			// 기본 정보
			basicWrap.querySelector('.recipe_title').innerText = result.data.title;
			basicWrap.querySelector('.recipe_cooklevel').innerText = result.data.cooklevel;
			basicWrap.querySelector('.recipe_cooktime').innerText = result.data.cooktime;
			basicWrap.querySelector('.recipe_spicy').innerText = result.data.spicy;
			basicWrap.querySelector('.recipe_writer img').src = result.data.avatar;
			basicWrap.querySelector('.recipe_writer span').innerText = result.data.nickname;
			basicWrap.querySelector('.btn_nickname').addEventListener('click', function(event) {
				event.preventDefault();
				location.href = `/mypage/profile.do?id=${result.data.user_id}`;
			});
			basicWrap.querySelector('.recipe_introduce').innerText = result.data.introduce;
			if (result.data.liked) {
				basicWrap.querySelector('.btn_icon.like img').src = '/images/recipe/star_full_1a7ce2.png'
			 	basicWrap.querySelector('.btn_icon.like').classList.add('active');
			}
			basicWrap.querySelector('.btn_report_modal').addEventListener('click', function(event) {
				event.preventDefault();
				if (!isLoggedIn()) {
					bootstrap.Modal.getInstance(document.getElementById('reportRecipeModal'))?.hide();
					redirectToLogin();
				}
			})
			renderCategoryList(result.data.category_list);
			
			// 재료
			originPortion = parseInt(result.data.portion, 10);
			stockWrap.querySelector('.recipe_portion select').value = result.data.portion;
			stockWrap.querySelector('.btn_stock_modal').addEventListener('click', function(event) {
				event.preventDefault();
				if (!isLoggedIn()) {
					bootstrap.Modal.getInstance(document.getElementById('viewRecipeStockModal'))?.hide();
					redirectToLogin();
				}
			});
			renderStockList(result.data.stock_list);
			renderMemoList(result.data.stock_list);
			
			// 조리 순서
			renderStepList(result.data.step_list);
			
			// 레시피 팁
			tipWrap.querySelector('.recipe_tip p').innerText = result.data.tip;
			
			// 구독 및 후원
			supportWrap.querySelector('.recipe_writer img').src = result.data.avatar;
			supportWrap.querySelector('.recipe_writer h5').innerText = result.data.nickname;
			supportWrap.querySelector('.recipe_writer p').innerText = `구독자 ${result.data.follower}명`;
			supportWrap.querySelector('.recipe_writer').addEventListener('click', function(event) {
				event.preventDefault();
				location.href = `/mypage/profile.do?id=${result.data.user_id}`;
			});
			supportWrap.querySelector('button').addEventListener('click', function(event) {
				event.preventDefault();
				if (!isLoggedIn()) {
					bootstrap.Modal.getInstance(document.getElementById('supportRecipeModal'))?.hide();
					redirectToLogin();
				}
			});
			
			// 레시피 작성자 ID를 hidden input에 저장
			document.getElementById('fundeeIdInput').value = result.data.user_id;
			
			// 리뷰 수
			reviewCommentWrap.querySelector('.review_count').innerText = result.data.reviewcount;
			// reviewCommentWrap.querySelector('.comment_count').innerText = result.data.commentcount;
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

	const stockWrap = document.getElementById('viewRecipeStockWrap');
	stockWrap.querySelector('.recipe_portion select').addEventListener('change', function() {
		const portion = parseInt(stockWrap.querySelector('.recipe_portion select').value, 10);
		stockWrap.querySelectorAll('.stock_list td.amount').forEach(td => {
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
	const basicWrap = document.getElementById('viewRecipeBasicWrap');
	const container = basicWrap.querySelector('.recipe_category');
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
	const stockWrap = document.getElementById('viewRecipeStockWrap');
	const container = stockWrap.querySelector('.stock');
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
	
	const modal = document.getElementById('viewRecipeStockModal');
	const container = modal.querySelector('.stock_list');
	container.innerHTML = '';
	
	stockList.forEach((ingredient, index) => {
		const tr = document.createElement('tr');
		
		// 이름
		const tdName = document.createElement('td');
		tdName.textContent = ingredient.name;
		tr.appendChild(tdName);
		
		// 수량
		const tdAmount = document.createElement('td');
		tdAmount.textContent = `${ingredient.amount}${ingredient.unit}`;
		
		tdAmount.dataset.amount = ingredient.amount;
		tdAmount.dataset.unit = ingredient.unit;
		
		
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
		
		container.appendChild(tr);
	});
}





/**
 * 레시피 조리 과정을 화면에 렌더링하는 함수
 */
function renderStepList(stepList) {
	const stepWrap = document.getElementById('viewRecipeStepWrap');
	const container = stepWrap.querySelector('.step_list');
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
	const stepWrap = document.getElementById('viewRecipeStepWrap');
	
	document.getElementById('togglePhotoButton').addEventListener('click', function(event) {
		event.preventDefault();
		photoMode = !photoMode;
		
		if (photoMode) {
			document.getElementById('togglePhotoButton').innerHTML = '<img src="/images/recipe/photo_999.png">사진 숨기기';
			stepWrap.querySelectorAll('.step_list .step_image').forEach(step => {
				step.style.display = 'block';
			});
		}
		else {
			document.getElementById('togglePhotoButton').innerHTML = '<img src="/images/recipe/photo_999.png">사진 보기';
			stepWrap.querySelectorAll('.step_list .step_image').forEach(step => {
				step.style.display = 'none';
			});
		}
	});
});






/**
 * 레시피 좋아요(저장) 버튼 기능
 */
document.addEventListener('DOMContentLoaded', function() {
    const likeButton = document.querySelector('.btn_icon.like');
    const recipeId = new URLSearchParams(window.location.search).get('id');
    const img = likeButton.querySelector('img');

    const likeCountElement = document.querySelector('.btn_likecount');
    let likecount = parseInt(likeCountElement?.textContent || '0', 10);

    likeButton.addEventListener('click', async function(event) {
        event.preventDefault();
        if (!isLoggedIn()) {
            redirectToLogin();
            return;
        }

        let isLiked = likeButton.classList.contains('active');

        // 좋아요 취소
        if (isLiked) {
            try {
                const data = {
                    tablename: 'recipe',
                    recodenum: recipeId,
                };

                const response = await instance.delete(`/recipes/${recipeId}/likes`, {
                    data: data,
                    headers: getAuthHeaders(),
                });

                if (response.data.code === 'RECIPE_UNLIKE_SUCCESS') {
                    img.src = '/images/recipe/star_empty_181a1c.png';
                    likeButton.classList.remove('active');
                    likecount = Math.max(0, likecount - 1);
                    if (likeCountElement) likeCountElement.textContent = `${likecount}`;
                }
            } catch (error) {
                console.error(error);
                alert('찜 취소 중 오류가 발생했습니다.');
            }
        }

        // 좋아요 추가
        else {
            try {
                const data = {
                    tablename: 'recipe',
                    recodenum: recipeId,
                };

                const response = await instance.post(`/recipes/${recipeId}/likes`, data, {
                    headers: getAuthHeaders(),
                });

                if (response.data.code === 'RECIPE_LIKE_SUCCESS') {
                    img.src = '/images/recipe/star_full_1a7ce2.png';
                    likeButton.classList.add('active');
                    likecount = likecount + 1;
                    if (likeCountElement) likeCountElement.textContent = `${likecount}`;
                }
            } catch (error) {
                console.error(error);
                alert('찜 추가 중 오류가 발생했습니다.');
            }
        }
    });
});






