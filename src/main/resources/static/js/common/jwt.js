/**
 * 토큰을 디코딩하는 함수
 * 
 * @param {string} token - JWT access token
 * @returns {object} - payload 객체
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
 * 로그인 여부를 확인하는 함수
 */
function isLoggedIn() {
	return !!localStorage.getItem("accessToken");
}



/**
 * 토큰 만료 여부를 확인하는 함수
 * 
 * @param {string} token - JWT access token
 * @returns {boolean} - true/false
 */
function isTokenExpired(token) {
	const payload = parseJwt(token);
	const now = Math.floor(Date.now() / 1000);
	return payload.exp < now;
}



/**
 * 토큰을 재발급하는 함수
 * 
 * @returns {Promise<string>} - 새 access token
 * @throws {Error} - 재발급 실패 시 에러 발생
 */
async function reissueJwt() {
	const token = localStorage.getItem('accessToken');

	if (!isTokenExpired(token)) {
		throw new Error('토큰 재발급 실패');
	}

	try {
		const response = await fetch('/reissue', {
			method: 'POST',
			credentials: 'include'
		});

		const result = await response.json();

		if (result.code === 'AUTH_TOKEN_REISSUE_SUCCESS') {
			localStorage.setItem('accessToken', result.data.accessToken);
			return result.data.accessToken;
		}
		else {
			throw new Error(result.code);
		}
	}
	catch (error) {
		throw error;
	}
}



/**
 * axios 인스턴스를 생성
 */
const baseURL = location.hostname === 'localhost'
  ? 'http://localhost:8586'
  : 'http://ec2-43-202-4-20.ap-northeast-2.compute.amazonaws.com:8586';
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
	
	// 토큰이 없으면 요청 전송
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
/*
function redirectToLogin() {
	localStorage.removeItem('accessToken');
	alert('로그인 후 이용 가능합니다.');
	window.location.href = '/user/login.do';
}
*/
function redirectToLogin() {
	localStorage.removeItem('accessToken');
	if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
		location.href = '/user/login.do';
	}
}

