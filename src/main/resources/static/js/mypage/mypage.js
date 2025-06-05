/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		alert('로그인 후 이용 가능합니다.');
		window.location.href = '/user/login.do';
		return;
	}
	
	const token = localStorage.getItem('accessToken');
	try {
		const response = await fetch("/auth/check", {
			method: "GET",
			headers: {
				"Authorization": "Bearer " + token
			}
		});
		
		const result = await response.json();
		// 인증 성공 및 정상 접근
		if (result.code === "AUTH_TOKEN_INVALID") {
			return;
		}
		// 토큰 만료시 재발급 시도
		else if (isTokenExpired(token)) {
			const reissueResponse = await fetch("/reissue", {
				method: "POST",
				credentials: "include"
			});
			const reissueResult = await reissueResponse.json();
			if (reissueResult.code === 'AUTH_TOKEN_REISSUE_SUCCESS') {
				localStorage.setItem('accessToken', reissueResult.data.accessToken);
				return;
			}
		}
		// 인증 실패 또는 재발급 실패 시 재로그인 필요
		else {
			localStorage.removeItem("accessToken");
			alert('세션이 만료되었습니다. 다시 로그인 해주세요.');
			window.location.href = '/user/login.do';
		}
	}
	catch (error) {
		alert('문제가 발생했습니다.');
		console.log(error);
		window.location.href = '/user/login.do';
	}
});











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
