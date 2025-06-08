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
document.addEventListener('DOMContentLoaded', async function() {
	if (!isLoggedIn()) {
		setLoginState(false);
		return;
	}
	
	try {
		await instance.get('/dummy');
		
		const token = localStorage.getItem('accessToken');
		const payload = parseJwt(token);
		
		if (document.querySelector(".logout_state")) {
			setLoginState(true, payload.nickname);
		}
	}
	catch (error) {
		alert(error.message);
		if (document.querySelector(".logout_state")) {
			setLoginState(false);
		}
	}
});



/**
 * 로그인 상태에 따라 헤더 표시를 변경하는 함수
 * 
 * @param {boolean} isLoggedIn - 로그인 여부
 * @param {String} nickname - 사용자 닉네임
 */
function setLoginState(isLoggedIn, nickname = '') {
	const logoutState = document.querySelector(".logout_state");
	const loginState = document.querySelector(".login_state");
	const userName = document.querySelector(".user_name");
	
	if (isLoggedIn) {
		logoutState.style.display = "none";
		loginState.style.display = "flex";
		userName.innerText = `${nickname}님`;
	} else {
		logoutState.style.display = "flex";
		loginState.style.display = "none";
		userName.innerText = "";
	}
}



/**
 * 로그아웃
 */

document.addEventListener('DOMContentLoaded', async function() {
	const token = localStorage.getItem("accessToken");
	
	// 비로그인 상태
	if (!token) {
		setLoginState(false);
		return;
	}
	
	try {
		await instance.get('/auth/check');
		
		const newToken = localStorage.getItem('accessToken');
		const payload = parseJwt(newToken);
		setLoginState(true, payload.nickname);
	}
	catch (error) {
		localStorage.removeItem('accessToken');
		setLoginState(false);
	}
});


document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("logout").addEventListener("click", async function(event) {
		event.preventDefault();
		
		try {
			await instance.get('/auth/check');
			
			const response = await instance.post('/logout');
			
			if (response.data.code === "USER_LOGOUT_SUCCESS") {
				localStorage.removeItem("accessToken");
				window.location.href = "/";
			}
			else if (response.data.code === "AUTH_REFRESH_TOKEN_MISSING") {
				alert("로그아웃 실패");
			}
			else if (response.data.code === "AUTH_REFRESH_TOKEN_EXPIRED") {
				alert("로그아웃 실패");
			}
			else if (response.data.code === "AUTH_REFRESH_TOKEN_INVALID") {
				alert("로그아웃 실패");
			}
			else if (response.data.code === "USER_NOT_FOUND") {
				alert("로그아웃 실패");
			}
			else {
				alert("로그아웃 실패");
			}
		}
		catch {error => console.log(error)}
	});
});