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
 * 메뉴를 이동하는 함수 (접근 권한 설정)
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("chompessor").addEventListener("click", async (event) => {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			alert("로그인 후 이용 가능합니다.");
			window.location.href = "/user/login.do";
			return;
		}
		
		const token = localStorage.getItem("accessToken");
		try {
			const response = await fetch(`${API_BASE_URL}/auth/check`, {
				method: "GET",
				headers: {
					"Authorization": "Bearer " + token
				}
			});
			
			const result = await response.json();
			// 인증 성공
			if (result.code === "SUCCESS") {
				window.location.href ="/chompessor/listChomp.do";
			}
			// 토큰 만료시 재발급 시도
			else if (isTokenExpired(token)) {
				const reissueResponse = await fetch(`${API_BASE_URL}/reissue`, {
					method: "POST",
					credentials: "include"
				});
				const result = await reissueResponse.json();
				if (result.code === "SUCCESS") {
					localStorage.setItem("accessToken", result.data.accessToken);
					window.location.href ="/chompessor/listChomp.do";
				}
				else {
					
				}
			}
			// 재로그인 필요
			else {
				localStorage.removeItem("accessToken");
				alert("세션이 만료되었습니다. 다시 로그인 해주세요.");
				window.location.href = "/user/login.do";
			}
		}
		catch (error) {
			console.log(error);
		}
	});
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
			fetch(`${API_BASE_URL}/reissue`, {
				method: "POST",
				credentials: "include"
			})
			.then(response => response.json())
			.then(result => {
				if (result.code === "SUCCESS") {
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
		fetch(`${API_BASE_URL}/logout`, {
			method: "POST",
			credentials: "include"
		})
		.then(response => response.json())
		.then((data) => {
			if (data.code === "SUCCESS") {
				localStorage.removeItem("accessToken");
				window.location.href = "/";
			}
			else {
				alert("로그아웃 실패");
			}
		})
		.catch(error => console.log(error));
	});
});