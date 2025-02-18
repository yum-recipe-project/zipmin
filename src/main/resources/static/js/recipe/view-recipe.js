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
 * 레시피를 후원하는 모달창을 여는 함수
 */
function openSupportRecipeModal() {
	
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
	 * 
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

