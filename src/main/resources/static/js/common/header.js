/**
 * 메뉴 항목 클릭 시 활성화 상태를 토글하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const menuItems = document.querySelectorAll('.gnb ul li a');
    const logo = document.querySelector('.logo');	
	const currentPath = window.location.pathname.split('/')[1];
	
    // 메뉴의 href 속성과 비교하여 active 클래스 추가
    menuItems.forEach(item => {
        const menuPath = item.getAttribute('href').split('/')[1];
        if (menuPath === currentPath) {
            item.parentElement.classList.add('active');
        }
    });

    // 로고 클릭 시 'active' 클래스 제거
    if (logo) {
        logo.addEventListener('click', function() {
            document.querySelectorAll('.gnb li').forEach(li => li.classList.remove('active'));
        });
    }
});



/**
 * 헤더에 로그인한 사용자의 정보를 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const token = localStorage.getItem("accessToken");
	
	// 비로그인 상태
	if (!token) {
		document.querySelector(".logout_state").style.display = "flex";
		document.querySelector(".login_state").style.display = "none";
		return;
	}
	
	if (token) {
		
		// 토큰 만료시 재발급 시도
		if (isTokenExpired(token)) {
			fetch("/reissue", {
				method: "POST",
				credentials: "include"
			})
			.then(response => response.json())
			.then(result => {
				if (result.code === "AUTH_TOKEN_REISSUE_SUCCESS") {
					localStorage.setItem("accessToken", result.data.accessToken);
					const payload = parseJwt(token);
					document.querySelector(".logout_state").style.display = "none";
					document.querySelector(".login_state").style.display = "flex";
					document.querySelector(".user_name").innerText = `${payload.nickname}님`;
				}
				else {
					// 비회원으로 간주
					localStorage.removeItem("accessToken");
					return;
				}
			})
			.catch(error => console.log(error));
			return;
		}
		
		// 유효한 토큰이면 사용자 정보 표시
		const payload = parseJwt(token);
		document.querySelector(".logout_state").style.display = "none";
		document.querySelector(".login_state").style.display = "flex";
		document.querySelector(".user_name").innerText = `${payload.nickname}님`;
	}
});



/**
 * 로그아웃
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("logout").addEventListener("click", function(event) {
		event.preventDefault();
		fetch("/logout", {
			method: "POST",
			credentials: "include"
		})
		.then(response => response.json())
		.then((data) => {
			if (data.code === "USER_LOGOUT_SUCCESS") {
				localStorage.removeItem("accessToken");
				window.location.href = "/";
			}
			else if (data.code === "AUTH_REFRESH_TOKEN_MISSING") {
				alert("로그아웃 실패");
			}
			else if (data.code === "AUTH_REFRESH_TOKEN_EXPIRED") {
				alert("로그아웃 실패");
			}
			else if (data.code === "AUTH_REFRESH_TOKEN_INVALID") {
				alert("로그아웃 실패");
			}
			else if (data.code === "USER_NOT_FOUND") {
				alert("로그아웃 실패");
			}
			else {
				alert("로그아웃 실패");
			}
		})
		.catch(error => console.log(error));
	});
});