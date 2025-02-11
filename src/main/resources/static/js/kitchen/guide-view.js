/**
 * textarea의 focus 여부에 따라 클래스를 토글하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
  const textarea = document.querySelector('.comment_input textarea');
  const commentInput = document.querySelector('.comment_input');

  textarea.addEventListener('focus', function() {
      commentInput.classList.add('focus');
    });

    textarea.addEventListener('blur', function() {
      commentInput.classList.remove('focus');
    });
});



/**
 * 댓글 작성 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const commentInput = document.getElementById("commentInput");
	const commentButton = document.getElementById("commentButton")
	
    // 댓글 작성 폼 실시간 검사
    commentInput.addEventListener("input", function() {
        if (commentInput.value.trim() !== "") {
            commentButton.classList.remove("disable");
        }
        else {
            commentButton.classList.add("disable");
        }
    });
});


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












