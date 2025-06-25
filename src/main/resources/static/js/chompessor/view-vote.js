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
 * 투표의 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
});







/**
 * 댓글 정렬 버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
	
	// 정렬 버튼 클릭 시 초기화 후 댓글 목록 조회
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			
			sort = this.getAttribute('data-sort');
			page = 0;
			
			document.querySelector('.comment_list').innerHTML = '';
			commentList = [];
			
			fetchCommentList('chomp_vote');
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList('chomp_vote');
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList('chomp_vote');
	});
});
