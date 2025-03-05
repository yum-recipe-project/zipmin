/**
 * 댓글 더보기 버튼 클릭 시 댓글목록을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const btnMore = document.querySelector('.btn_more');
    
    if (btnMore) {
        btnMore.removeEventListener('click', handleClick); // 이전 이벤트 리스너가 있으면 제거
        btnMore.addEventListener('click', handleClick);
    }
	
	/* 버튼 클릭 함수 */
	function handleClick() {
	    console.log('댓글 더보기 버튼 클릭');
	    const eventId = 123;
	    loadComments(eventId);
	}
	
	/* 댓글 데이터 api 요청 함수 */
	function loadComments(eventId) {
	    const url = `/event/${eventId}/comments`;

	    fetch(url)
	        .then((response) => response.json())
	        .then((data) => {
	            console.log(data); 
	            renderComments(data);
	        })
	        .catch((error) => console.log("error:", error));
	}
	
	/* 댓글 출력 함수 */
	function renderComments(comments) {
	    const commentList = document.querySelector('.comment_list');
		
	    const commentMap = new Map(); // 원 댓글 맵

	    comments.forEach(comment => {
	        if (comment.comm_id === comment.id) {
	            // 원 댓글 처리
	            const li = createCommentElement(comment);
	            commentMap.set(comment.id, li);
	            commentList.appendChild(li);
	        } else {
	            // 대댓글 처리
	            if (commentMap.has(comment.comm_id)) {
	                const parentLi = commentMap.get(comment.comm_id);
	                const subCommentList = parentLi.querySelector('.subcomment_list') || createSubcommentList();
	                const subCommentLi = createSubCommentElement(comment);
	                subCommentList.appendChild(subCommentLi);
	                parentLi.appendChild(subCommentList);
	            }
	        }
	    });
	}
	
	/* 원댓글 html 처리 함수 */
	function createCommentElement(comment) {
	    const li = document.createElement('li');
	    li.classList.add('comment');
	    li.innerHTML = `
	        <div class="comment_info">
	            <div class="comment_writer">
	                <img src="/images/common/test.png" alt="Writer">
	                <span>아잠만</span>
	                <span>${new Date(comment.postdate).toLocaleDateString()}</span>
	            </div>
	            <div class="comment_action">
	                <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportVoteCommentModal" onclick="openReportVoteCommentModal();">신고</a>
	                <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editVoteCommentModal" onclick="openEditVoteCommentModal();">수정</a>
	                <a href="#">삭제</a>
	            </div>
	        </div>
	        <p class="comment_content">${comment.content}</p>
	        <div class="comment_tool">
	            <button class="btn_tool write_subcomment_btn">
	                <img src="/images/recipe/thumb_up_full.png">
	                <img src="/images/recipe/thumb_up_empty.png">
	                <p>3</p>
	            </button>
	            <a class="btn_outline_small write_subcomment_btn" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#writeVoteSubcommentModal" onclick="openWriteVoteSubcommentModal();">
	                <span>답글 쓰기</span>
	            </a>
	        </div>
	    `;
	    return li;
	}
	
	/* 대댓글 html 처리 함수 */
	function createSubCommentElement(comment) {
	    const li = document.createElement('li');
	    li.classList.add('subcomment');
	    li.innerHTML = `
	        <img class="subcomment_arrow" src="/images/chompessor/arrow_right.png" alt="Arrow">
	        <div class="subcomment_inner">
	            <div class="subcomment_info">
	                <div class="subcomment_writer">
	                    <img src="/images/common/test.png" alt="Writer">
	                    <span>아잠만</span>
	                    <span>${new Date(comment.postdate).toLocaleDateString()}</span>
	                </div>
	                <div class="subcomment_action">
	                    <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#reportVoteSubcommentModal" onclick="openReportVoteSubcommentModal();">신고</a>
	                    <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editVoteSubcommentModal" onclick="openEditVoteSubcommentModal();">수정</a>
	                    <a href="#">삭제</a>
	                </div>
	            </div>
	            <p class="subcomment_content">${comment.content}</p>
	            <div class="comment_tool">
	                <button class="btn_tool write_subcomment_btn">
	                    <img src="/images/recipe/thumb_up_full.png">
	                    <img src="/images/recipe/thumb_up_empty.png">
	                    <p>3</p>
	                </button>
	            </div>
	        </div>
	    `;
	    return li;
	}
	
	/* 대댓글 리스트 생성 함수 */
	function createSubcommentList() {
	    const ul = document.createElement('ul');
	    ul.classList.add('subcomment_list');
	    return ul;
	}
	
});

/******** NOTE: 여기까지 테스트코드 *********/ 




/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.comment_order button');
	const contentItems = document.querySelectorAll('.comment_list');
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
	// 기본으로 첫번째 활성화
	tabItems.forEach(button => button.classList.remove('active'));
	contentItems.forEach(content => content.style.display = 'none');
	tabItems[0].classList.add('active');
	contentItems[0].style.display = 'block';
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
	const commentButton = document.getElementById("commentButton")
	commentInput.addEventListener("input", function() {
		const isCommentEmpty = commentInput.value.trim() === "";
		commentButton.classList.toggle("disable", isCommentEmpty);
		commentButton.disabled = isCommentEmpty;
	});
});








