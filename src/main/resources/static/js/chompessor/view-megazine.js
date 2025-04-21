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
	const megazineId = new URLSearchParams(window.location.search).get('megazineId');

	// 매거진 정보 불러오기
	fetch(`http://localhost:8586/megazines/${megazineId}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector(".megazine_title").innerText = data.chomp_dto.title;
		document.querySelector(".megazine_content").innerText = data.content;
		const date = new Date(data.postdate);
		const formatDate = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
		document.querySelector(".megazine_postdate").innerText = formatDate;
		document.querySelectorAll(".comment_count").forEach(count => {
			count.innerText = `댓글 ${data.comment_count}`;
		});
	})
	.catch(error => console.log(error));

	// 댓글 데이터 최초 불러오기
	loadChompMegazineCommentList(megazineId);

	// 정렬 탭 클릭
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener("click", function (event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			selectSort = this.getAttribute("data-sort");
			page = 0;
			document.querySelector(".comment_list").innerHTML = "";
			loadChompMegazineCommentList(megazineId);
		});
	});

	// 더보기 버튼
	document.querySelector(".btn_more").addEventListener("click", function () {
		page++;
		showChompMegazineCommentList();
	});
});


/**
 * 
 */
function loadChompMegazineCommentList(megazineId) {
	fetch(`http://localhost:8586/megazines/${megazineId}/comments?sort=${selectSort}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		allCommentList = dataList;
		page = 0;
		showChompMegazineCommentList(megazineId);
	})
	.catch(error => console.log(error));
}


/**
 * 현재 페이지 기준으로 댓글 목록 렌더링
 */
function showChompMegazineCommentList(megazineId) {
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
								<a href="javascript:deleteChompMegazineComment(${reply.id});">삭제</a>
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
						<a href="javascript:deleteChompMegazineComment(${origin.id});">삭제</a>
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
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	document.getElementById("writeCommentForm").addEventListener("submit", function (event) {
		event.preventDefault();
		const params = new URLSearchParams(window.location.search);
		const megazineId = params.get('megazineId');
		
		const commentData = {
			content: document.getElementById("writeCommentContent").value.trim(),
			tablename: "chomp_megazine",
			recodenum: Number(megazineId),
		};

		fetch(`http://localhost:8586/megazines/${megazineId}/comments`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(commentData)
		})
		.then((response) => response.json())
		.then(result => {
			console.log("결과: ", result);
			// location.reload();
		})
		.catch(error => console.log(error));
	});
});




/**
 * 댓글을 삭제하는 함수
 * 
 * @param {Integer} commId - 댓글 일련번호
 */
function deleteChompMegazineComment(commId) {
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





