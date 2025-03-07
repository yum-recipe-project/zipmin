/**
 * 
 */
var commentOffset = 0;
var commentLimit = 10;
var commentCount = 0;
$(document).ready(function () {
	// 초기 페이지 로딩 시 데이터 로드
    loadmyCommentContent();
	
    // 댓글 더보기 버튼 클릭 시 추가 데이터 로드
    $('.more_comment_btn').click(function (event) {
        event.preventDefault();
        loadmyCommentContent();
    });
});






function loadmyCommentContent() {
    $.ajax({
        url: '/mypage/comment/comment.do',
        type: 'GET',
        data: {
	        offset: commentOffset,
	        limit: commentLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#myCommentContent').append(response);

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
