/**
 * 레시피 상세 페이지에 데이터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const title = document.getElementById("title");
	const level = document.getElementById("level");
	const time = document.getElementById("time");
	const spicy = document.getElementById("spicy");
	const introduce = document.getElementById("introduce");
	const tip = document.getElementById("tip");
	const category = document.getElementById("category");
	const nickname = document.querySelectorAll(".nickname[data-id]");
	const servingInput = document.getElementById("servingInput");
	const ingredientkk = document.getElementById("ingredient");
	const stepkk = document.getElementById("step");
	const follower = document.getElementById("follower");
	const followButton = document.getElementById("followButton");
	const reviewCount = document.querySelectorAll(".review_count[data-id]");
	const commentCount = document.querySelectorAll(".comment_count[data-id]");
	const memo = document.getElementById("memo");
	
	// 데이터 패치
	fetch("http://localhost:8586/recipes/1", {
		method: "get"
	})
	.then(response => response.json())
	.then(data => {
		title.innerText = data.title;
		level.innerText = data.level;
		time.innerText = data.time;
		spicy.innerText = data.spicy;
		introduce.innerText = data.introduce;
		tip.innerText = data.tip;
		// 카테고리
		const span1 = document.createElement('span');
		span1.textContent = `#${ data.category.type }`;
		category.appendChild(span1);
		const span2 = document.createElement('span');
		span2.textContent = `#${ data.category.situation }`;
		category.appendChild(span2);
		const span3 = document.createElement('span');
		span3.textContent = `#${ data.category.ingredient }`;
		category.appendChild(span3);
		const span4 = document.createElement('span');
		span4.textContent = `#${ data.category.way }`;
		category.appendChild(span4);
		//
		nickname.forEach(n => {
			n.innerText = data.member.nickname;
		});
		servingInput.value = data.serving;
		previousValue = parseFloat(data.serving);
		// 재료
		data.ingredient_list.forEach(ingredient => {
			const tr = document.createElement("tr");
			const tdName = document.createElement("td");
			tdName.textContent = ingredient.name;
			const tdAmount = document.createElement("td");
			tdAmount.textContent = ingredient.amount + ingredient.unit;
			const tdNote = document.createElement("td");
			tdNote.textContent = ingredient.note || "";
			tr.appendChild(tdName);
			tr.appendChild(tdAmount);
			tr.appendChild(tdNote);
			ingredientkk.appendChild(tr);
		});
		// 조리 순서
		stepkk.innerHTML = data.step_list.map((step, index) => `
	        <li>
	            <div class="description">
	                <h5>STEP${ index + 1 }</h5>
	                <p><span class="hidden">${ index + 1 }.&nbsp;</span>${ step.description }</p>
	            </div>
	            ${step.image ? `<div class="image"><img src="${step.image}"></div>` : `<div class="image"><img src="/images/common/test.png"></div>`}
	        </li>
	    `).join("");
		
		// 구독자 수
		follower.innerText = data.follower_count;
		// 구독 버튼
		followButton.classList.toggle("btn_outline", data.isFollow);
		followButton.classList.toggle("btn_dark", !data.isFollow);
		followButton.innerText = (data.isFollow === false ? "구독" : "구독 중");
		
		// 댓글 수
		commentCount.forEach(c => {
			c.innerHTML = data.comment_count;
		})
		
		// 리뷰 수
		reviewCount.forEach(r => {
			r.innerHTML = data.review_count;
		});
		
		
		// 메모
		data.ingredient_list.forEach((ingredient, index) => {
			const tr = document.createElement("tr");
			const tdName = document.createElement("td");
			tdName.textContent = ingredient.name;
			const tdAmount = document.createElement("td");
			tdAmount.textContent = ingredient.amount + ingredient.unit;
			const tdCheck = document.createElement("td");
			tdCheck.innerHTML = `<input type="checkbox" id="addMemo_${ index }" name="ingredient" checked>
				<label for="addMemo_${ index }"></label>`;
			tr.appendChild(tdName);
			tr.appendChild(tdAmount);
			tr.appendChild(tdCheck);
			memo.appendChild(tr);
		});
	})
	.catch(error => console.log(error));
});







/**
 * 리뷰/댓글 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const tabItems = document.querySelectorAll('.tab_button button');
    const contentItems = document.querySelectorAll('.tab_content');

    // 탭 클릭 이벤트 설정
    tabItems.forEach((item, index) => {
        item.addEventListener("click", function(event) {
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
 * 
 */
var reviewOffset = 0; // 현재 데이터 시작 위치
var reviewLimit = 10; // 한 번에 가져올 데이터 개수
var reviewCount = 0; // 리뷰 전체 데이터 개수
var commentOffset = 0; // 현재 데이터 시작 위치
var commentLimit = 10; // 한 번에 가져올 데이터 개수
var commentCount = 0; // 리뷰 전체 데이터 개수
$(document).ready(function () {

	// 초기 페이지 로딩 시 데이터 로드
    loadRecipeReviewContent();
    loadRecipeCommentContent();
	
    // 리뷰 더보기 버튼 클릭 시 추가 데이터 로드
    $('.more_review_btn').click(function (event) {
        event.preventDefault();
        loadRecipeReviewContent();
    });
	
    // 댓글 더보기 버튼 클릭 시 추가 데이터 로드
    $('.more_comment_btn').click(function (event) {
        event.preventDefault();
        loadRecipeCommentContent();
    });
});



/**
 * 레시피 리뷰 목록 데이터를 로드하는 함수
 */
function loadRecipeReviewContent() {
	
    $.ajax({
        url: '/recipe/viewRecipe/review.do',
        type: 'GET',
        data: {
	        offset: reviewOffset,
	        limit: reviewLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#recipeReviewContent').append(response);

            // 서버에서 총 데이터의 개수를 가져와서 설정
            reviewCount = parseInt($('#reviewCount').val());
	        reviewOffset += reviewLimit;

            // 불러올 데이터가 없으면 더보기 버튼 숨김
            if (reviewOffset >= reviewCount) {
                $('.more_review_btn').hide();
            } else {
                $('.more_review_btn').show();
            }
        },
        error: function () {
            $('.more_review_btn').hide();
        }
    });
}



/**
 * 레시피 댓글 목록 데이터를 로드하는 함수
 */
function loadRecipeCommentContent() {
    $.ajax({
        url: '/recipe/viewRecipe/comment.do',
        type: 'GET',
        data: {
	        offset: commentOffset,
	        limit: commentLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#recipeCommentContent').append(response);

            // 서버에서 총 데이터의 개수를 가져와서 설정
            commentCount = parseInt($('#commentCount').val());
	        commentOffset += commentLimit;

            // 불러올 데이터가 없으면 더보기 버튼 숨김
            if (commentOffset >= commentCount) {
                $('.more_comment_btn').hide();
            } else {
                $('.more_comment_btn').show();
            }
        },
        error: function () {
            $('.more_comment_btn').hide();
        }
    });
}





/**
 * 리뷰 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabReviewItems = document.querySelectorAll('.review_order button');
	const contentReviewItems = document.querySelectorAll('.review_list');
	// 탭 클릭 이벤트 설정
	tabReviewItems.forEach((item, index) => {
		item.addEventListener("click", function(event) {
			event.preventDefault();
            
			tabReviewItems.forEach(button => button.classList.remove('active'));
			this.classList.add('active');
            
			contentReviewItems.forEach(content => content.style.display = 'none');
			contentReviewItems[index].style.display = 'block';
        });
	});
	// 기본으로 첫번째 활성화
	tabReviewItems.forEach(button => button.classList.remove('active'));
	contentReviewItems.forEach(content => content.style.display = 'none');
	tabReviewItems[0].classList.add('active');
	contentReviewItems[0].style.display = 'block';
});



/**
 * 리뷰 작성 폼의 별점을 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
	const stars = document.querySelectorAll('#starGroup .star');
	const starInput = document.getElementById('starInput');
	
	stars.forEach(star => {
		star.addEventListener('click', function() {
			starInput.value = this.getAttribute('data-value');
			stars.forEach(s => {
				const starValue = Number(s.getAttribute('data-value'));
				s.src = starValue <= this.getAttribute('data-value') ? '/images/recipe/star_full.png' : '/images/recipe/star_outline.png';
			});
		});
	});
});



/**
 * 리뷰 작성 폼의 focus 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reviewInput = document.getElementById("reviewInput");
	const reviewInputBorder = document.querySelector('.review_input');
	reviewInput.addEventListener('focus', function() {
		reviewInputBorder.classList.add('focus');
	});
	reviewInput.addEventListener('blur', function() {
		reviewInputBorder.classList.remove('focus');
	});
});



/**
 * 리뷰 작성 폼값을 검증하는 함수 (댓글 내용 미작성 시 버튼 비활성화)
 */
document.addEventListener('DOMContentLoaded', function() {
	const reviewButton = document.getElementById("reviewButton");
	reviewInput.addEventListener("input", function() {
		const isReviewEmpty = reviewInput.value.trim() === "";
		reviewButton.classList.toggle("disable", isReviewEmpty);
		reviewButton.disabled = isReviewEmpty;
	});
});




/**
 * 댓글 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabCommentItems = document.querySelectorAll('.comment_order button');
	const contentCommentItems = document.querySelectorAll('.comment_list');
	// 탭 클릭 이벤트 설정
	tabCommentItems.forEach((item, index) => {
	    item.addEventListener("click", function(event) {
	        event.preventDefault();
	        
	        tabCommentItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	        
	        contentCommentItems.forEach(content => content.style.display = 'none');
	        contentCommentItems[index].style.display = 'block';
	    });
	});
	// 기본으로 첫번째 활성화
	tabCommentItems.forEach(button => button.classList.remove('active'));
	contentCommentItems.forEach(content => content.style.display = 'none');
	tabCommentItems[0].classList.add('active');
	contentCommentItems[0].style.display = 'block';
});



/**
 * 댓글 작성 폼의 focus 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const commentInput = document.getElementById("commentInput");
	const commentInputBorder = document.querySelector('.comment_input');
	commentInput.addEventListener('focus', function() {
		commentInputBorder.classList.add('focus');
	});
	commentInput.addEventListener('blur', function() {
		commentInputBorder.classList.remove('focus');
	});
});



/**
 * 댓글 작성 폼값을 검증하는 함수 (댓글 내용 미작성 시 버튼 비활성화)
 */
document.addEventListener('DOMContentLoaded', function() {
	const commentButton = document.getElementById("commentButton");
	commentInput.addEventListener("input", function() {
		const isCommentEmpty = commentInput.value.trim() === "";
		commentButton.classList.toggle("disable", isCommentEmpty);
		commentButton.disabled = isCommentEmpty;
	});
});



/**
 * 신고 모달창의 신고 사유를 선택했는지 검증하는 함수
 * 
 * @returns {boolean} - 검증 결과
 */
function validateReportRecipeForm() {
	if (!document.querySelector('input[name="reason"]:checked')) {
		alert("신고 사유를 선택하세요.");
		return false;
	}
}



/**
 * 장보기메모에 재로 담기 모달창의 재료를 선택했는지 검증하는 함수
 * 
 * @returns {boolean} - 검증 결과
 */
function validateAddMemoForm() {
	if (!document.querySelector('input[name=ingredient]:checked')) {
		alert("재료를 하나 이상 선택하세요.");
		return false;
	}
}





