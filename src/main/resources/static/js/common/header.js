/**
 * 메뉴 항목 클릭 시 활성화 상태를 토글하고 검색창을 보여주는 함수
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
	
	// 검색창
	document.querySelectorAll('.search_form').forEach(form => {
		const type = form.getAttribute('data-type');
		form.style.display = (type === currentPath) ? 'flex' : 'none';
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
		
		setLoginState(true, payload.nickname, payload.id);
	}
	catch (error) {
		setLoginState(false);
	}
	
});





/**
 * 로그인 상태에 따라 헤더 표시를 변경하는 함수
 */
function setLoginState(isLoggedIn, nickname = '', id) {
	const logoutState = document.querySelector(".logout_state");
	const loginState = document.querySelector(".login_state");
	const userName = document.querySelector(".user_name");
	
	if (isLoggedIn) {
		logoutState.style.display = "none";
		loginState.style.display = "flex";
		userName.innerText = `${nickname}님`;
		userName.addEventListener('click', function(event) {
			event.preventDefault();
			location.href = `/mypage/profile.do?id=${id}`;
		});
	} else {
		logoutState.style.display = "flex";
		loginState.style.display = "none";
		userName.innerText = "";
	}
}





/**
 * 로그아웃을 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("logout").addEventListener('click', async function(event) {
		event.preventDefault();
		
		if (!isLoggedIn()) {
			redirectToLogin();
		}
		
		try {
			const response = await instance.post('/logout');
			
			if (response.data.code === 'USER_LOGOUT_SUCCESS') {
				localStorage.removeItem("accessToken");
				window.location.href = '/';
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'AUTH_REFRESH_TOKEN_MISSING') {
				alert(result.message);
			}
			else if (code === 'AUTH_REFRESH_TOKEN_EXPIRED') {
				alert(result.message);
			}
			else if (code === 'AUTH_REFRESH_TOKEN_INVALID') {
				alert(result.message);
			}
			else if (code === 'USER_NOT_FOUND') {
				alert(result.message);
			}
			else {
				alert(result.message);
			}
		}
	});
});





/**
 * 키친가이드 팁을 보여주는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 서버에서 키친가이드 조회
	let tipList = [];
	try {
		const params = new URLSearchParams({
			page: 0,
			size: 100
		}).toString();

		const response = await fetch(`/guides?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'KITCHEN_READ_LIST_SUCCESS') {
			tipList = result.data.content;			
		}
		
		/***** TODO: 에러코드 추가하기 *****/
	}
	catch (error) {
		console.error(error);
	}
	
	// 키친팁 변경 동작
	function changeTip() {
		document.querySelector('.tip_text').classList.add('slide-up');
		
		setTimeout(() => {
			const randomList = tipList[Math.floor(Math.random() * tipList.length)];
			document.querySelector('.cook_tip a').href = `/kitchen/viewGuide.do?id=${randomList.id}`;
			document.querySelector('.tip_text').innerHTML = `${randomList.title}&nbsp;&nbsp;→`;
			document.querySelector('.tip_text').classList.remove('slide-up');
		}, 500);
	}
	
	changeTip();
	setInterval(changeTip, 10000);
	
});