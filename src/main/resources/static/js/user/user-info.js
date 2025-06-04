/**
 * 접근 권한을 설정하는 함수
 */
/*
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		alert('로그인 후 이용 가능합니다.');
		window.location.href = '/user/login.do';
		return;
	}
	
	alert("로그인은 되어있음");
	const token = localStorage.getItem('accessToken');
	try {
		const response = await fetch("/auth/check", {
			method: "GET",
			headers: {
				"Authorization": "Bearer " + token
			}
		});
		
		alert("실행할거임");
		const result = await response.json();
		alert(result.code);
		// 인증 성공 및 정상 접근
		if (result.code === "AUTH_TOKEN_INVALID") {
			alert("정상접근");
			return;
		}
		// 토큰 만료시 재발급 시도
		else if (isTokenExpired(token)) {
			const reissueResponse = await fetch("/reissue", {
				method: "POST",
				credentials: "include"
			});
			const reissueResult = await reissueResponse.json();
			alert(reissueResult);
			if (reissueResult.code === 'AUTH_TOKEN_REISSUE_SUCCESS') {
				localStorage.setItem('accessToken', reissueResult.data.accessToken);
				alert(성공);
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
*/



/**
 * 회원 정보를 조회하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const token = localStorage.getItem('accessToken');
	const payload = parseJwt(token);
	
	fetch(`/users/${payload.id}`, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => response.json())
	.then(result => {
		if (result.code === 'USER_PROFILE_FETCH_SUCCESS') {
			document.querySelector('.username_field .username').innerText = result.data.username;
			document.querySelector('.name_field .name').innerText = result.data.name;
			document.querySelector('.nickname_field .nickname').innerText = result.data.nickname;
			document.querySelector('.tel_field .tel').innerText = result.data.tel;
			document.querySelector('.email_field .email').innerText = result.data.email;
			
			document.querySelector('input[name="name"]').value = result.data.name;
			document.querySelector('input[name="nickname"]').value = result.data.nickname;
			document.querySelector('input[name="tel"]').value = result.data.tel;
			document.querySelector('input[name="email"]').value = result.data.email;
		}
	})
	.catch(error => console.log(error));
});



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 기본 정보 수정
	document.getElementById('edit-basic-info-btn').addEventListener('click', function(event) {
		event.preventDefault();
		
		const icon = document.querySelector('#edit-basic-info-btn img');
		const label = document.querySelector('#edit-basic-info-btn a');
		const isEdit = label.innerText === '취소';
		
		label.innerText = isEdit ? '수정' : '취소';
		icon.src = isEdit ? '/images/user/modify_member.png' : '/images/user/cancel_modify.png';

		document.querySelector('input[name="name"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('input[name="nickname"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('input[name="tel"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('.name_field .name').style.display = isEdit ? 'block' : 'none';
		document.querySelector('.nickname_field .nickname').style.display = isEdit ? 'block' : 'none';
		document.querySelector('.tel_field .tel').style.display = isEdit ? 'block' : 'none';
	});
	
	// 이메일 정보 수정
	document.getElementById('edit-email-info-btn').addEventListener('click', function(event) {
		event.preventDefault();
		
		const icon = document.querySelector('#edit-email-info-btn img');
		const label = document.querySelector('#edit-email-info-btn a');
		const isEdit = label.innerText === '취소';
		
		label.innerText = isEdit ? '수정' : '취소';
		icon.src = isEdit ? '/images/user/modify_member.png' : '/images/user/cancel_modify.png';

		document.querySelector('input[name="email"]').style.display = isEdit ? 'none' : 'block';
		document.querySelector('.email_field .email').style.display = isEdit ? 'block' : 'none';
	});
});



/**
 * 정보 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const basicInfoForm = document.getElementById('basic-info-form');
	const emailInfoForm = document.getElementById('email-info-form');
	
	
	// 이름 실시간 검사
	basicInfoForm.name.addEventListener('input', function() {
		const isNameEmpty = this.value.trim() === "";
		this.classList.toggle('danger', isNameEmpty);
		document.querySelector('.name_field .name_hint').style.display = isNameEmpty ? 'block' : 'none';
	});
	
	// 닉네임 실시간 검사
	basicInfoForm.nickname.addEventListener('input', function() {
		const isNicknameEmpty = this.value.trim() === "";
		this.classList.toggle('danger', isNicknameEmpty);
		document.querySelector('.nickname_field .nickname_hint').style.display = isNicknameEmpty ? 'block' : 'none';
	});
	
	// 휴대폰 번호 실시간 검사
	basicInfoForm.tel.addEventListener('input', function() {
		let tel = this.value.replace(/[^0-9]/g, "");
		if (tel.length <= 3) {
			this.value = tel;
		}
		else if (tel.length <= 7) {
			this.value = tel.slice(0, 3) + "-" + tel.slice(3);
		}
		else {
			this.value = tel.slice(0, 3) + "-" + tel.slice(3, 7) + "-" + tel.slice(7, 11);
		}
		const isTelEmpty = this.value.trim() === "";
		this.classList.toggle('danger', isTelEmpty);
		document.querySelector('.tel_field .tel_hint').style.display = isTelEmpty ? 'block' : 'none';
	});
	
	// 이메일 실시간 검사
	emailInfoForm.email.addEventListener('input', function() {
		const isEmailEmpty = this.value.trim() === "";
		this.classList.toggle('danger', isEmailEmpty);
		document.querySelector('.email_field .email_hint').style.display = isEmailEmpty ? 'block' : 'none';
	});
});




/**
 * 
 */
/*
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
*/



/**
 * 회원 정보를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
});


















