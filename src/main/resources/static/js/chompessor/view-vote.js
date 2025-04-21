/**
 * 전역 변수 선언
 */
let allCommentList = [];
let selectSort = "new";
let page = 0;
const size = 10;



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function () {
	const voteId = new URLSearchParams(window.location.search).get('voteId');
	
	// 투표 정보 불러오기
	fetch(`http://localhost:8586/votes/${voteId}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector(".vote_title").innerText = data.chomp_dto.title;
		const opendate = new Date(data.opendate);
		const closedate = new Date(data.closedate);
		const formatOpendate = `${opendate.getFullYear()}년 ${String(opendate.getMonth() + 1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일`;
		const formatClosedate = `${closedate.getFullYear()}년 ${String(closedate.getMonth() + 1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;
		document.querySelector(".vote_date").innerText = `${formatOpendate} - ${formatClosedate}`;
		document.querySelectorAll(".comment_count").forEach(count => {
			count.innerText = `댓글 ${data.comment_count}`;
		});
	})
	.catch(error => console.log(error));
	
	// 댓글 데이터 최초 불러오기
	loadChompVoteCommentList(voteId);
	
	// 정렬 탭 클릭
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener("click", function(event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			selectSort = this.getAttribute("data-sort");
			page = 0;
			document.querySelector(".comment_list").innerHTML = "";
			loadChompVoteCommentList(voteId);
		});
	});
	
	// 더보기 버튼
	document.querySelector(".btn_more").addEventListener("click", function() {
		page++;
		showChompVoteCommentList();
	});
})



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
function loadChompVoteCommentList(voteId) {
	fetch(`http://localhost:8586/votes/${voteId}/comments?sort=${selectSort}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		allCommentList = dataList;
		page = 0;
		showChompVoteCommentList();
	})
	.catch(error => console.log(error));
}



/**
 * 
 */
function showChompVoteCommentList() {
	const start = 0;
	const end = (page + 1) * size;
	const pagedList = allCommentList.slice(start, end);

	const commentList = pagedList.filter(data => data.id === data.comm_id);
	const subcommentList = pagedList.filter(data => data.id !== data.comm_id);

	const commentHTML = commentList.map(origin => {
		const postdate = new Date(origin.postdate);
		const formatPostdate = `${postdate.getFullYear()}년 ${String(postdate.getMonth() + 1).padStart(2, '0')}월 ${String(postdate.getDate()).padStart(2, '0')}일`;

		const replyList = subcommentList.filter(reply => reply.comm_id === origin.id);

		const subcommentListHTML = replyList.map(reply => {
			const replydate = new Date(reply.postdate);
			const formatReplydate = `${replydate.getFullYear()}년 ${String(replydate.getMonth() + 1).padStart(2, '0')}월 ${String(replydate.getDate()).padStart(2, '0')}일`;

			return `
			<li class="subcomment" data-comment-id="${reply.id}" data-subcomment-id="${origin.id}">
					<img class="subcomment_arrow" src="/images/chompessor/arrow_right.png">
					<div class="subcomment_inner">
						<div class="subcomment_info">
							<div class="subcomment_writer">
								<img src="/images/common/test.png">
								<span>${reply.nickname}</span>
								<span>${formatReplydate}</span>
							</div>
							<div class="subcomment_action">
								<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">신고</a>
								<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">수정</a>
								<a href="javascript:deleteChompVoteComment(${reply.id});">삭제</a>
							</div>
						</div>
						<p class="subcomment_content">${reply.content}</p>
						<div class="comment_tool">
							<button class="btn_tool write_subcomment_btn">
								<img src="/images/common/thumb_up_full.png">
								<img src="/images/common/thumb_up_empty.png">
								<p>${reply.likecount}</p>
							</button>
						</div>
					</div>
				</li>`;
		}).join("");

		return `
			<li class="comment" data-comment-id="${origin.id}">
				<div class="comment_info">
					<div class="comment_writer">
						<img src="/images/common/test.png">
						<span>${origin.nickname}</span>
						<span>${formatPostdate}</span>
					</div>
					<div class="comment_action">
						<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">신고</a>
						<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">수정</a>
						<a href="javascript:deleteChompVoteComment(${origin.id});">삭제</a>
					</div>
				</div>
				<p class="comment_content">${origin.content}</p>
				<div class="comment_tool">
					<button class="btn_tool write_subcomment_btn">
						<img src="/images/common/thumb_up_full.png">
						<img src="/images/common/thumb_up_empty.png">
						<p>${origin.likecount}</p>
					</button>
					<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeSubcommentModal">
						<span>답글 쓰기</span>
					</a>
				</div>
			</li>
			<ul class="subcomment_list">${subcommentListHTML}</ul>`;
	}).join("");

	document.querySelector(".comment_list").insertAdjacentHTML('beforeend', commentHTML);

	// 더보기 버튼 제어
	const btnMore = document.querySelector(".btn_more");
	if (end >= allCommentList.length) {
		btnMore.style.display = "none";
	} else {
		btnMore.style.display = "block";
	}
}



/**
 * 댓글을 삭제하는 함수
 * 
 * @param {Integer} commId - 댓글 일련번호
 */
function deleteChompVoteComment(commId) {
	if (confirm("댓글을 삭제하시겠습니까?")) {
		fetch(`http://localhost:8586/comments/${commId}`, {
			method: "DELETE"
		})
		.then(async (response) => {
			const message = await response.text();
			switch (response.status) {
				case 204:
					alert("댓글이 삭제되었습니다.");
					const comment = document.querySelector(`[data-comment-id='${commId}']`);
					if (comment) {
						comment.remove();
					}
					const subcomments = document.querySelectorAll(`[data-subcomment-id='${commId}']`);
					subcomments.forEach(subcomment => subcomment.remove());
					break;
				case 400:
					alert(message); // 잘못된 요청입니다.
					break;
				case 403:
					alert(message); // 권한이 없습니다.
					break;
				case 404:
					alert(message); // 댓글을 찾을 수 없습니다.
					break;
				case 409:
					alert(message); // 중복된 요청입니다.
					break;
				default:
					alert(message);
					break;
			}
		})
		.catch(error => console.log(error));
	}
}

















