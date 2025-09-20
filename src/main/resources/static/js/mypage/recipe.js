let page = 0;
let size = 10;
let totalPages = 0;

document.addEventListener('DOMContentLoaded', function() {
    fetchMyRecipeList();

    document.querySelector('.btn_more').addEventListener('click', function() {
        fetchMyRecipeList();
    });
});

/**
 * 내가 작성한 레시피 목록 가져오기
 */
async function fetchMyRecipeList() {
    try {
        const token = localStorage.getItem('accessToken');
        const payload = parseJwt(token);

        const params = new URLSearchParams({
            page: page,
            size: size
        }).toString();

        const response = await instance.get(`/users/${payload.id}/recipes?${params}`);
        
        renderMyRecipeList(response.data.data.content);

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
function renderMyRecipeList(recipeList) {
    const container = document.getElementById('myRecipeList');

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

        // 상단: 제목 + 관리 버튼
        const topDiv = document.createElement('div');
        topDiv.className = 'recipe_top';

        const titleP = document.createElement('p');
        titleP.textContent = recipe.title;

        const managementDiv = document.createElement('div');
        managementDiv.className = 'recipe_management';

        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = '삭제';
        deleteBtn.addEventListener('click', async () => {
            if (confirm('정말 삭제하시겠습니까?')) {
                await deleteRecipe(recipe.id, li);
            }
        });

        managementDiv.append(deleteBtn);
        topDiv.append(titleP, managementDiv);

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
 * 레시피 삭제
 */
async function deleteRecipe(id, liElement) {
    try {
        const response = await instance.delete(`/recipes/${id}`, {
            headers: getAuthHeaders()
        });

        if (response.data.code === 'RECIPE_DELETE_SUCCESS') {
            liElement.remove();
            const recipeCountEl = document.getElementById('recipeCount');
            const currentTotal = parseInt(recipeCountEl.innerText) || 0;
            recipeCountEl.innerText = `${Math.max(0, currentTotal - 1)}개`;
        }
    } catch (error) {
        console.error(error);
    }
}
