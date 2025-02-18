/**
 * 
 */
var reviewOffset = 0;
var reviewLimit = 10;
var reviewCount = 0;
$(document).ready(function () {
	// 초기 페이지 로딩 시 데이터 로드
    loadMyReviewContent();
	
    // 댓글 더보기 버튼 클릭 시 추가 데이터 로드
    $('.more_review_btn').click(function (event) {
        event.preventDefault();
        loadMyReviewContent();
    });
});




/**
 * 나의 댓글 목록 데이터를 로드하는 함수
 */
function loadMyReviewContent() {
    $.ajax({
        url: '/mypage/review/review.do',
        type: 'GET',
        data: {
	        offset: reviewOffset,
	        limit: reviewLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#myReviewContent').append(response);

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