document.addEventListener("DOMContentLoaded", function () {
	
	// 닉네임 수정 
    const nickNameModifyBtn = document.getElementById("nickNameModifyBtn");
    const nickNameField = document.getElementById("nickNameField");
    const modifyNickName = document.getElementById("modifyNickName");
    const nickNameState = document.getElementById("nickNameState");
    const nickNameIcon = document.getElementById("nickNameIcon");

    if (nickNameModifyBtn && nickNameField && modifyNickName) {
        nickNameModifyBtn.addEventListener("click", function (event) {
            event.preventDefault(); 

            // 닉네임 수정 상태 전환
            nickNameField.classList.toggle("hidden");
            modifyNickName.classList.toggle("hidden");

            if (nickNameField.classList.contains("hidden")) {
                nickNameState.textContent = "취소";
                nickNameIcon.src = "/images/user/cancel_modify.png";
            } else {
                nickNameState.textContent = "수정";
                nickNameIcon.src = "/images/user/modify_member.png"; 
            }
        });
    }
	
	// 소개 수정 
    const commentModifyBtn = document.getElementById("commentModifyBtn");
    const comment = document.getElementById("comment");
    const modifyComment = document.getElementById("modifyComment");
    const commentState = document.getElementById("commentState");
    const commentIcon = document.getElementById("commentIcon");

    if (commentModifyBtn && comment && modifyComment) {
        commentModifyBtn.addEventListener("click", function (event) {
            event.preventDefault(); 

            // 소개 수정 상태 전환
            comment.classList.toggle("hidden");
            modifyComment.classList.toggle("hidden");

            if (comment.classList.contains("hidden")) {
                commentState.textContent = "취소";
                commentIcon.src = "/images/user/cancel_modify.png";
            } else {
                commentState.textContent = "수정";
                commentIcon.src = "/images/user/modify_member.png"; 
            }
        });
    }
	
	// 프로필 이미지 변경
	document.getElementById("profileModifyBtn").addEventListener("click", function() {
	    document.getElementById("fileInput").click();
	});
	document.getElementById("fileInput").addEventListener("change", function(event) {
	    const file = event.target.files[0];
	    if (file) {
	        const reader = new FileReader();
	        reader.onload = function(e) {
	            const profileImg = document.querySelector(".profile_img");
	            profileImg.style.backgroundImage = `url(${e.target.result})`; // 읽은 이미지의 인코딩 문자열 설정 
	            profileImg.style.backgroundColor = 'transparent'; 
	        };
	        reader.readAsDataURL(file);
	    }
	});
	
});
