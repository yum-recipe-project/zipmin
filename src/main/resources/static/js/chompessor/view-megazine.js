/**
 * 
 */
selectSort = "new";

document.addEventListener('DOMContentLoaded', function() {
	const params = new URLSearchParams(window.location.search);
	const megazineId = params.get('megazineId');
	
	// 매거진 데이터 패치
	fetch(`http://localhost:8586/megazines/${megazineId}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector(".megazine_category").innerText = data.chomp_dto.category;
		document.querySelector(".megazine_title").innerText = data.chomp_dto.title;
		document.querySelector(".megazine_content").innerText = data.content;
		const date = new Date(data.postdate);
		const formatDate = `${date.getFullYear()}년 ${String(date.getMonth()+1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
		document.querySelector(".megazine_postdate").innerText = formatDate;
	})
	.catch(error => console.log(error));
	
	renderChompMegazineDataList(megazineId);

	// 댓글 정렬순서 탭 버튼 클릭 이벤트 처리
	const tabList = document.querySelectorAll('.comment_order button');
	tabList.forEach(tabI => {
	    tabI.addEventListener("click", function(event) {
	        event.preventDefault();
	        tabList.forEach(tabJ => tabJ.classList.remove('active'));
	        this.classList.add('active');
			selectSort = this.getAttribute("data-sort");
			renderChompMegazineDataList(megazineId);
	    });
	});
});



/**
 * 매거진에 해당하는 댓글 목록을 출력하는 함수
 * 
 * @param {Integer} megazineId - 매거진 일련번호
 */
function renderChompMegazineDataList(megazineId) {
	fetch(`http://localhost:8586/megazines/${megazineId}/comments?sort=${selectSort}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(dataList => {
		const commentList = dataList.filter(data => data.id === data.comm_id);
		const subcommentList = dataList.filter(data => data.id !== data.comm_id);

		document.querySelector(".comment_list").innerHTML = commentList.map(origin => {
			const postdate = new Date(origin.postdate);
			const formatPostdate = `${postdate.getFullYear()}년 ${String(postdate.getMonth()+1).padStart(2, '0')}월 ${String(postdate.getDate()).padStart(2, '0')}일`;
			
			const replyList = subcommentList.filter(reply => reply.comm_id === origin.id);
			
			const subcommentListHTML = replyList.map(reply => {
				const replydate = new Date(reply.postdate);
				const formatReplydate = `${replydate.getFullYear()}년 ${String(replydate.getMonth()+1).padStart(2, '0')}월 ${String(replydate.getDate()).padStart(2, '0')}일`;
				
				console.log(reply);
				
				return `
					<li class="subcomment">
						<img class="subcomment_arrow" src="/images/chompessor/arrow_right.png">
						<div class="subcomment_inner">
							<div class="subcomment_info">
								<div class="subcomment_writer">
									<img src="/images/common/test.png">
									<span>${ reply.nickname }</span>
									<span>${ formatReplydate }</span>
								</div>
								<c:if test="${ true }">
									<div class="subcomment_action">
										<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
											신고
										</a>
										<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">
											수정
										</a>
										<a href="">삭제</a>
									</div>
								</c:if>
							</div>
							<p class="subcomment_content">${ reply.content }</p>
							<div class="comment_tool">
								<button class="btn_tool write_subcomment_btn">
									<img src="/images/common/thumb_up_full.png">
		                            <img src="/images/common/thumb_up_empty.png">
		                            <p>${ reply.likecount }</p>
								</button>
							</div>
						</div>
					</li>
				`;
			}).join("");
			
			return `
				<li class="comment">
					<div class="comment_info">
						<div class="comment_writer">
							<img src="/images/common/test.png">
							<span>아잠만</span>
							<span>${ formatPostdate }</span>
						</div>
							<div class="comment_action">
								<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
									신고
								</a>
								<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editCommentModal">
									수정
								</a>
								<a href="">삭제</a>
							</div>
					</div>
					<p class="comment_content">${ origin.content }</p>
					<div class="comment_tool">
						<button class="btn_tool write_subcomment_btn">
							<img src="/images/common/thumb_up_full.png">
	                        <img src="/images/common/thumb_up_empty.png">
	                        <p>${ origin.likecount }</p>
						</button>
						<a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeSubcommentModal">
							<span>답글 쓰기</span>
						</a>
					</div>
				</li>
				<ul class="subcomment_list">
					${subcommentListHTML}
				</ul>`;
		}).join("");
	})
	.catch(error => console.log(error));
}








