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
	const params = new URLSearchParams(window.location.search);
	const megazineId = params.get('megazineId');

	// 매거진 정보 불러오기
	fetch(`http://localhost:8586/megazines/${megazineId}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector(".megazine_category").innerText = data.chomp_dto.category;
		document.querySelector(".megazine_title").innerText = data.chomp_dto.title;
		document.querySelector(".megazine_content").innerText = data.content;
		const date = new Date(data.postdate);
		const formatDate = `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
		document.querySelector(".megazine_postdate").innerText = formatDate;
	})
	.catch(error => console.log(error));

	// 댓글 데이터 최초 불러오기
	loadAndRenderComments(megazineId);

	// 정렬 탭 클릭
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener("click", function (event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			selectSort = this.getAttribute("data-sort");
			page = 0;
			document.querySelector(".comment_list").innerHTML = "";
			loadAndRenderComments(megazineId);
		});
	});

	// 더보기 버튼
	document.querySelector(".btn_more").addEventListener("click", function () {
		page++;
		renderChompMegazineCommentDataList();
	});
});


/**
 * 
 */
function loadAndRenderComments(megazineId) {
	fetch(`http://localhost:8586/megazines/${megazineId}/comments?sort=${selectSort}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		allCommentList = dataList;
		page = 0;
		renderChompMegazineCommentDataList();
	})
	.catch(error => console.log(error));
}


/**
 * 현재 페이지 기준으로 댓글 목록 렌더링
 */
function renderChompMegazineCommentDataList() {
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
				<li class="subcomment">
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
								<a href="">삭제</a>
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
			<li class="comment">
				<div class="comment_info">
					<div class="comment_writer">
						<img src="/images/common/test.png">
						<span>${origin.nickname}</span>
						<span>${formatPostdate}</span>
					</div>
					<div class="comment_action">
						<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">신고</a>
						<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">수정</a>
						<a href="">삭제</a>
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
