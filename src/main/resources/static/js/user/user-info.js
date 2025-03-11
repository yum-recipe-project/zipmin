/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
	
	// 이메일 수정 
    const emailModifyBtn = document.getElementById("emailModifyBtn");
    const emailText = document.getElementById("emailText");
    const modifyEmail = document.getElementById("modifyEmail");
    const emailState = document.getElementById("emailState");
    const emailIcon = document.getElementById("emailIcon");

    if (emailModifyBtn && emailText && modifyEmail) {
        emailModifyBtn.addEventListener("click", function (event) {
            event.preventDefault(); 

            // 이메일 수정 상태 전환
            emailText.classList.toggle("hidden");
            modifyEmail.classList.toggle("hidden");

            if (emailText.classList.contains("hidden")) {
                emailState.textContent = "취소";
                emailIcon.src = "/images/user/cancel_modify.png";
            } else {
                emailState.textContent = "수정";
                emailIcon.src = "/images/user/modify_member.png"; 
            }
        });
    }
	
	// 기본정보 수정
	const basicModifyBtn = document.getElementById("basicModifyBtn");
	const basicState = document.getElementById("basicState");
	const basicIcon = document.getElementById("basicIcon");
	const basicInfo = document.getElementById("basicInfo");
	const modifyBasicInfo = document.getElementById("modifyBasicInfo");

	if (basicModifyBtn && basicState && basicIcon && basicInfo && modifyBasicInfo) {
		basicModifyBtn.addEventListener("click", function (event) {
			event.preventDefault(); // 기본 동작 방지

			// 기본정보 상태 전환
			basicInfo.classList.toggle("hidden");
			modifyBasicInfo.classList.toggle("hidden");

			if (basicInfo.classList.contains("hidden")) {
				basicState.textContent = "취소";
				basicIcon.src = "/images/user/cancel_modify.png"; // 취소 아이콘
			} else {
				basicState.textContent = "수정";
				basicIcon.src = "/images/user/modify_member.png"; // 수정 아이콘
			}
		});
	}
	
});
