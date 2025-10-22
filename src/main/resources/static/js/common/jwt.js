/**
 * 로그인 여부를 확인하는 함수
 */
function isLoggedIn() {
	return !!localStorage.getItem('accessToken');
}





/**
 * 관리자 로그인 여부를 확인하는 함수
 */
function isAdminLoggedIn() {
	return !!localStorage.getItem('accessToken');
}





/**
 * 토큰을 디코딩하는 함수
 */
function parseJwt(token) {
	
	const base64Url = token.split('.')[1];
	const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
		return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	}).join(''));
	
	return JSON.parse(jsonPayload);
}





/**
 * 페이로드를 얻는 함수
 */
function getPayload() {
	
	const token = localStorage.getItem('accessToken');
	const payload = parseJwt(token);
	
	return payload;
}





/**
 * 현재 로그인 상태에 따라 API 요청 헤더를 생성하는 함수
 */
function getAuthHeaders() {
	
    const headers = {
        'Content-Type': 'application/json'
    };

    if (isLoggedIn()) {
        const token = localStorage.getItem('accessToken');
        headers.Authorization = `Bearer ${token}`;
    }

    return headers;
}





/**
 * 토큰 만료 여부를 확인하는 함수
 */
function isTokenExpired(token) {
	
	const payload = parseJwt(token);
	const now = Math.floor(Date.now() / 1000);
	
	return payload.exp < now;
}





/**
 * 토큰을 재발급하는 함수
 */
async function reissueJwt() {

	try {
		const response = await fetch('/reissue', {
			method: 'POST',
			credentials: 'include'
		});

		const result = await response.json();
		
		console.log(result);

		if (result.code === 'AUTH_TOKEN_REISSUE_SUCCESS') {
			localStorage.setItem('accessToken', result.data.accessToken);
			return result.data.accessToken;
		}
		else {
			console.log(result);
			alert('토큰 재발급 실패 1 jwt.js')
			throw new Error(result.code);
		}
	}
	catch (error) {
		alert('토큰 재발급 실패 2 jwt.js');
		localStorage.removeItem('accessToken');
		// TODO : 쿠키는 서버에서 지우도록 HttpOnly는 js에서 수정 못하므로 백엔드수정 (계속 오류가 나타난다면)
		// document.cookie = 'refresh=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT;';
		
		throw error;
	}
}



/**
 * axios 인스턴스를 생성
 */
const baseURL = location.hostname === 'localhost'
  ? 'http://localhost:8586'
  : 'http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586';
const instance = axios.create({
	baseURL: baseURL,
	withCredentials: true
});



let isRefreshing = false; // 재발급 중인지 여부
let refreshQueue = []; // 재발급 기다리는 요청들



/**
 * 요청 전 액세스 토큰의 만료 여부를 확인하고 필요시 자동 재발급하는 요청 인터셉터
 */
instance.interceptors.request.use(async (config) => {
	const token = localStorage.getItem('accessToken');
	
	// 토큰이 없으면 요청 전송 (**** 이거 수정 필요 ****)
	if (!token) {
		throw new Error('ACCESS_TOKEN_MISSING');
	}

	if (!token) {
		return config;
	}
	
	// 토큰이 만료되었는지 검사
	if (isTokenExpired(token)) {
		// 이미 재발급 중이 아니라면 재발급 시도
		if (!isRefreshing) {
			isRefreshing = true;
			
			try {
				// 새로운 access token 요청
				const newToken = await reissueJwt();
								
				// 재발급을 기다리던 요청들에 새 토큰 전달
				refreshQueue.forEach(callback => callback(newToken));
				refreshQueue = [];
			}
			// 로그인 실패시 로그인 페이지로 이동
			catch (error) {
				redirectToLogin();
			}
			finally {
				isRefreshing = false;
			}
		}
		
		// 재발급 중이라면 기다렸다가 토큰을 받아서 요청에 붙임
		return new Promise(resolve => {
			refreshQueue.push((newToken) => {
				config.headers.Authorization = `Bearer ${newToken}`;
				resolve(config);
			});
		});
	}
	
	// 토큰이 아직 유효하다면 헤더에 붙여서 요청 전송
	config.headers.Authorization = `Bearer ${token}`;
	return config;
});



/**
 * 401 오류(인증 실패) 발생 시 로그아웃 처리하는 응답 인터셉터
 */
instance.interceptors.response.use(
	// 응답이 정상일 경우 그대로 전달
	(response) => response,
	(error) => {
		return Promise.reject(error);
	}
);



/**
 * 로그인 페이지로 이동시키는 함수
 */
function redirectToLogin(url, isBack = false) {
	
	// 로그인
	if (isLoggedIn()) {
		if (url) {
			location.href = url;
		}
	}
	// 비로그인
	else {
		reissueJwt();
		if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
			location.href = '/user/login.do';
		}
		else {
			if (isBack === true) {
				history.back();
			}
		}
	}
	
}



/**
 * 관리자 로그인 페이지로 이동시키는 함수
 */
function redirectToAdminLogin() {
	
	if (confirm('관리자 권한으로 로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
		location.href = '/admin/login.do';
	}
	else {
		history.back();
	}
	
}
