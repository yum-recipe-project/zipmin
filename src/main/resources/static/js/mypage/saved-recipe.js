let page = 0;
let size = 10;
let totalPages = 0;
let recipeList = [];

document.addEventListener('DOMContentLoaded', function() {
    fetchSavedRecipeList();

    document.querySelector('.btn_more').addEventListener('click', function() {
        fetchSavedRecipeList();
    });
});

/**
 * 저장한 레시피 목록 가져오기
 */
async function fetchSavedRecipeList() {
    try {
        const token = localStorage.getItem('accessToken');
        const payload = parseJwt(token);

        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/users/${payload.id}/likes/recipe?${params}`);
		
        renderSavedRecipeList(response.data.data.content);

        page = response.data.data.number + 1;
        totalPages = response.data.data.totalPages;

        document.getElementById('recipeCount').innerText = response.data.data.totalElements + '개';
        document.querySelector('.btn_more').style.display = page >= totalPages ? 'none' : 'block';

    } catch (error) {
        console.error(error);
    }
}

/**
 * 레시피 목록 화면에 렌더링
 */
function renderSavedRecipeList(recipeList) {
    const container = document.getElementById('savedRecipeList');

    recipeList.forEach(recipe => {
        const li = document.createElement('li');
        li.className = 'recipe_item';
        li.dataset.id = recipe.id;

        const recipeBox = document.createElement('div');
        recipeBox.className = 'recipe_box';

   
		// 이미지
		const img = document.createElement('img');
		img.src = recipe.image || '/images/common/default_recipe.jpg';
		img.className = 'recipe_thumb';
		recipeBox.appendChild(img);


        // 레시피 디테일
        const details = document.createElement('div');
        details.className = 'recipe_details';

        // 상단: 제목 + 찜 버튼
        const topDiv = document.createElement('div');
        topDiv.className = 'recipe_top';

        const titleP = document.createElement('p');
        titleP.textContent = recipe.title;

        const favBtn = renderLikeButton(recipe.id, recipe.likecount, true);

        topDiv.append(titleP, favBtn);

        // 정보: 난이도, 시간, 매운 정도
        const infoDiv = document.createElement('div');
        infoDiv.className = 'recipe_info';

        const levelDiv = document.createElement('div');
        levelDiv.className = 'recipe_info_item';
        levelDiv.innerHTML = `<img src="/images/recipe/level.png"><p>${recipe.cooklevel}</p>`;

        const timeDiv = document.createElement('div');
        timeDiv.className = 'recipe_info_item';
        timeDiv.innerHTML = `<img src="/images/recipe/time.png"><p>${recipe.cooktime}</p>`;

        const spicyDiv = document.createElement('div');
        spicyDiv.className = 'recipe_info_item';
        spicyDiv.innerHTML = `<img src="/images/recipe/spicy.png"><p>${recipe.spicy}</p>`;

        infoDiv.append(levelDiv, timeDiv, spicyDiv);

        // 별점 + 리뷰 개수
        const scoreDiv = document.createElement('div');
        scoreDiv.className = 'recipe_score';

        const starDiv = document.createElement('div');
        starDiv.className = 'star';

        const fullStars = Math.floor(recipe.reviewscore || 0);
        const emptyStars = 5 - fullStars;

        for (let i = 0; i < fullStars; i++) {
            const starImg = document.createElement('img');
            starImg.src = '/images/recipe/star_full.png';
            starDiv.appendChild(starImg);
        }
        for (let i = 0; i < emptyStars; i++) {
            const starImg = document.createElement('img');
            starImg.src = '/images/recipe/star_empty.png';
            starDiv.appendChild(starImg);
        }

        const reviewP = document.createElement('p');
        reviewP.textContent = `${recipe.reviewscore || 0} (${recipe.reviewcount || 0})`;

        scoreDiv.append(starDiv, reviewP);

        details.append(topDiv, infoDiv, scoreDiv);
        recipeBox.appendChild(details);
        li.appendChild(recipeBox);
        container.appendChild(li);
    });
}


/**
 * 레시피 좋아요 버튼 생성
 */
function renderLikeButton(id, likecount, isLiked) {
    const button = document.createElement('button');
    button.className = 'favorite_btn';

    const img = document.createElement('img');
    img.src = isLiked ? '/images/common/star_full.png' : '/images/recipe/star_empty.png';
    button.append(img);

    button.addEventListener('click', async function(event) {
        event.preventDefault();

        if (!isLoggedIn()) {
            redirectToLogin();
            return;
        }

        const likeCountElement = button.closest('.recipe_details').querySelector('.recipe_score p');
        let currentCount = likecount;

        // 좋아요 취소
        if (isLiked) {
            try {
                const data = { tablename: 'recipe', recodenum: id };
                const response = await instance.delete(`/recipes/${id}/likes`, {
                    data: data,
                    headers: getAuthHeaders(),
                });

                if (response.data.code === 'RECIPE_UNLIKE_SUCCESS') {
                    isLiked = false;
                    img.src = '/images/recipe/star_empty.png';
                    currentCount = Math.max(0, currentCount - 1);
                    likeCountElement.textContent = `${currentCount}개`;
                    likecount = currentCount;

                    const recipeItem = button.closest('.recipe_item');
                    if (recipeItem) recipeItem.remove();

                    const recipeCountEl = document.getElementById('recipeCount');
                    if (recipeCountEl) {
                        const currentTotal = parseInt(recipeCountEl.innerText) || 0;
                        recipeCountEl.innerText = `${Math.max(0, currentTotal - 1)}개`;
                    }

                    if (page >= totalPages) {
                        const loadMoreBtn = document.querySelector('.btn_more');
                        if (loadMoreBtn) loadMoreBtn.style.display = 'none';
                    }
                }
            } catch (error) {
                console.error(error);
            }
        }
        // 좋아요 추가
        else {
            try {
                const data = { tablename: 'recipe', recodenum: id };
                const response = await instance.post(`/recipes/${id}/likes`, data, {
                    headers: getAuthHeaders(),
                });

                if (response.data.code === 'RECIPE_LIKE_SUCCESS') {
                    isLiked = true;
                    img.src = '/images/common/star_full.png';
                    currentCount += 1;
                    likeCountElement.textContent = `${currentCount}개`;
                    likecount = currentCount;
                }
            } catch (error) {
                console.error(error);
            }
        }
    });

    return button;
}
