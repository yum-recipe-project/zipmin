
document.addEventListener("DOMContentLoaded", function () {
   
	document.getElementById('editBtn').addEventListener('click', function(event) {
	    event.preventDefault(); // 기본 동작 방지 (링크 클릭 시 페이지 새로 고침 방지)

	    // 기존 이메일 텍스트 숨기고, 입력 필드와 저장 버튼 보이기
	    document.getElementById('emailText').style.display = 'none';
	    document.getElementById('editFields').style.display = 'block';  // label과 input 보이기
	    document.getElementById('saveBtnWrap').style.display = 'inline';  // 저장 버튼 보이기
	});

	// 저장 버튼 클릭 시 동작 (여기에 이메일 저장 로직 추가)
	document.getElementById('saveBtn').addEventListener('click', function() {
	    const newEmail = document.getElementById('emailInput').value;
	    // 이메일 저장 로직 구현 (예: 서버에 전송)

	    // 텍스트로 다시 표시
	    document.getElementById('emailText').textContent = newEmail;

	    // 입력 필드와 저장 버튼 숨기기, 텍스트 보이기
	    document.getElementById('emailText').style.display = 'inline';
	    document.getElementById('editFields').style.display = 'none';
	    document.getElementById('saveBtnWrap').style.display = 'none';
	});

	
	
	
});