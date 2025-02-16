/**
 * 투표
 */
document.addEventListener("DOMContentLoaded", function() {
	
	document.querySelectorAll(".checkbox_group").forEach((checkbox) => {
		checkbox.addEventListener("change", function() {
			// 체크되면 모든 체크박스를 disabled 처리
			if (this.checked) {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					if (cb !== this) {
						cb.disabled = true;
						cb.classList.add("disable");
					}
				});
			}
			// 하나도 체크되지 않으면 다시 활성화
			else {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					cb.disabled = false;
					cb.classList.remove("disable");
				});
			}
		});
	});
});



/**
 * 
 */
var commentOffset = 0;
var commentLimit = 10;
var commentCount = 0;
$(document).ready(function () {

	// 초기 페이지 로딩 시 데이터 로드
    loadForumCommentContent();
	
    // 댓글 더보기 버튼 클릭 시 추가 데이터 로드
    $('.more_comment_btn').click(function (event) {
        event.preventDefault();
        loadForumCommentContent();
    });
});



/**
 * 레시피 댓글 목록 데이터를 로드하는 함수
 */
function loadForumCommentContent() {
    $.ajax({
        url: '/chompessor/viewForum/comment.do',
        type: 'GET',
        data: {
	        offset: commentOffset,
	        limit: commentLimit
		},
        success: function (response) {
            // 응답 데이터를 리스트에 추가
            $('#forumCommentContent').append(response);

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



