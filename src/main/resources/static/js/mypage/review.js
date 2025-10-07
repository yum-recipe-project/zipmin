
document.addEventListener('DOMContentLoaded', function() {
	
	fetchReviewtList();
	
	document.querySelector('.btn_more').addEventListener('click', function() {
		fetchReviewtList();
	});
		
	
	
});

let totalPages = 0;
let page = 0;
let size = 10;
let reviewList = [];

/**
 * 서버에서 댓글 목록 데이터를 가져오는 함수
 */
async function fetchReviewtList() {
	
	try {
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		const params = new URLSearchParams({
			page: page,
			size: size
		}).toString();
		
		const response = await instance.get(`/users/${payload.id}/reviews?${params}`);
		
		console.log(response);
		
		renderReviewList(response.data.data.content);
		page = response.data.data.number + 1;
		totalPages = response.data.data.totalPages;
		document.querySelector('.myreview_count span').innerText = response.data.data.totalElements + '개';
		document.querySelector('.btn_more').style.display = page >= totalPages ? 'none' : 'block';
	}
	catch (error) {
		console.log(error);
	}
	
}






/**
 * 리뷰 목록을 화면에 렌더링하는 함수
 * @param {Array} reviewList - 서버에서 받은 리뷰 목록
 */
function renderReviewList(reviews) {

    const ul = document.querySelector('.myreview_list');

    reviews.forEach(review => {
        const li = document.createElement('li');

        // 리뷰 제목
        const titleDiv = document.createElement('div');
        titleDiv.className = 'myreview_title';
        const a = document.createElement('a');
        a.href = `/recipe/viewRecipe.do?id=${review.recipeId}`;
        a.textContent = review.title;
        titleDiv.appendChild(a);

        // 리뷰 내용
        const reviewDiv = document.createElement('div');
        reviewDiv.className = 'myreview';

        // 작성자 정보
        const avatar = document.createElement('img');
        avatar.className = 'review_avatar';
        avatar.src = review.image || '/images/common/test.png';

        const innerDiv = document.createElement('div');
        innerDiv.className = 'review_inner';

        const infoDiv = document.createElement('div');
        infoDiv.className = 'review_info';

        const writerDiv = document.createElement('div');
        writerDiv.className = 'review_writer';
        const nameSpan = document.createElement('span');
        nameSpan.textContent = review.nickname;
        const dateSpan = document.createElement('span');
        dateSpan.textContent = formatDate(review.postdate);

        writerDiv.appendChild(nameSpan);
        writerDiv.appendChild(dateSpan);

        const actionDiv = document.createElement('div');
        actionDiv.className = 'review_action';
        const editLink = document.createElement('a');
        editLink.href = 'javascript:void(0);';
        editLink.dataset.bsToggle = 'modal';
        editLink.dataset.bsTarget = '#editReviewModal';
        editLink.textContent = '수정';
        editLink.addEventListener('click', () => {
            document.getElementById('editReviewContent').value = review.content;
            document.getElementById('editReviewId').value = review.id;
        });

        const deleteLink = document.createElement('a');
        deleteLink.href = 'javascript:void(0);';
        deleteLink.textContent = '삭제';
        deleteLink.addEventListener('click', async () => {
            await deleteReview(review.id);
        });

        actionDiv.append(editLink, deleteLink);

        infoDiv.appendChild(writerDiv);
        infoDiv.appendChild(actionDiv);

        // 별점
        const scoreDiv = document.createElement('div');
        scoreDiv.className = 'review_score';
        const starDiv = document.createElement('div');
        starDiv.className = 'star';
        for (let i = 0; i < 5; i++) {
            const starImg = document.createElement('img');
            starImg.src = i < review.score ? '/images/recipe/star_full.png' : '/images/recipe/star_empty.png';
            starDiv.appendChild(starImg);
        }
        const scoreP = document.createElement('p');
        scoreP.textContent = review.score;

        scoreDiv.appendChild(starDiv);
        scoreDiv.appendChild(scoreP);

        // 리뷰 내용
        const contentP = document.createElement('p');
        contentP.className = 'review_content';
        contentP.textContent = review.content;

        innerDiv.appendChild(infoDiv);
        innerDiv.appendChild(scoreDiv);
        innerDiv.appendChild(contentP);

        reviewDiv.appendChild(avatar);
        reviewDiv.appendChild(innerDiv);

        li.appendChild(titleDiv);
        li.appendChild(reviewDiv);

        ul.appendChild(li);
    });
}
