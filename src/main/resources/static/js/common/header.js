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
 * 로그인 시 헤더에 정보 표시 (수정 필요 *************8)
 */
/*
document.addEventListener('DOMContentLoaded', function() {
	const token = localStorage.getItem("accessToken");
	
	if (token) {
		try {
			const payload = parseJwt(token);
			const now = Math.floor(Date.now() / 1000);
			
			if (payload.exp > now) {
				document.querySelector(".logout_state").style.display = "none";
				document.querySelector(".login_state").style.display = "flex";
				document.querySelector(".user_name").innerText = payload.nickname;
			}
			else {
				// 만료된 경우 access 재발급 시도
				fetch(`${API_BASE_URL}/reissue`, {
					method: "POST",
					credentials: "include"
				})
				.then(response => response.json())
				.then(result => {
					if (result.code === "SUCCESS") {
						payload = parseJwt(result.data.accessToken);
						localStorage.setItem("accessToken", result.data.accessToken);
						document.querySelector(".logout_state").style.display = "none";
						document.querySelector(".login_state").style.display = "flex";
						document.querySelector(".user_name").innerText = payload.nickname;
					}
					else {
						localStorage.removeItem("accessToken");
						location.href = "/login";
					}
				})
				.catch(error => console.log(error));
			}
		}
		catch(e) {
			localStorage.removeItem("accessToken");
		}
	}
})
*/



/**
 * 로그아웃
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("logout").addEventListener("click", function(event) {
		event.preventDefault();
		fetch(`${API_BASE_URL}/logout`, {
			method: "POST",
			credentials: "include"
		})
		.then((response) => {
			alert(response);
			if (response.ok) {
				localStorage.removeItem("accessToken");
				window.location.href = `${API_BASE_URL}/user/login.do`;
			}
			else {
				alert("로그아웃 실패");
			}
		})
		.catch(error => console.log(error));
	});
});