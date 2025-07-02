/**
 * 투표 생성 테스트
 */
document.addEventListener('DOMContentLoaded', async function () {
	const data = {
		title: "올 여름 가장 맛있는 아이스크림은?",
		opendate: "2025-07-02T10:00:00",
		closedate: "2025-07-10T23:59:59",
		user_id : 1,
		choices: [
			{ choice: "말차 아이스크림" },
			{ choice: "초코 아이스크림" },
			{ choice: "바닐라 아이스크림" }
		]
	};

	try {
		const response = await instance.post('/votes', data);
		
	}
	catch (error) {
	}
});



/**
 * 투표의 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	const params = new URLSearchParams(window.location.search);
	const id = params.get('id');
	const token = localStorage.getItem('accessToken');
	
	const headers = {
		'Content-Type': 'application/json'
	};
	if (token) {
		headers['Authorization'] = `Bearer ${token}`;
	}
	
	// 투표 정보 조회
	try {
		const response = await fetch(`/votes/${id}`, {
			method: 'GET',
			headers: headers
		});
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'VOTE_READ_SUCCESS') {
			document.querySelector('.vote_title').innerText = result.data.title;
			const now = new Date();
			const opendate = new Date(result.data.opendate);
			const closedate = new Date(result.data.closedate);
			const formatDate = `${opendate.getFullYear()}년 ${String(opendate.getMonth() + 1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일 - ${closedate.getFullYear()}년 ${String(closedate.getMonth() + 1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;
			document.querySelector('.vote_postdate').innerText = formatDate;
			document.querySelector('.vote_total').innerText = result.data.total;
			
			// 사용자 투표 완료
			if (result.data.voted) {
				showRecord(result.data.choice_list, result.data.choice_id);
			}
			// 투표 종료
			else if (now < opendate || now > closedate) {
				showRecord(result.data.choice_list, result.data.choice_id);
				document.getElementById('revoteBtn').style.display = 'none';
			}
			// 비로그인 상태 혹은 투표 미완료
			else {
				showChoice(result.data.choice_list);
			}
		}
		else {
			alert(result.message);
			location.href = '/chompessor/listChomp.do';
		}
	}
	catch (error) {
		console.log(error);
	}
});



/**
 * 투표의 선택지를 보여주는 함수
 * 
 * @param {Array} choice - 각 선택지와 투표 수 및 비율 정보가 담긴 객체 배열
 */
function showChoice(choices) {

	document.querySelector('.vote_form').style.display = 'block';
	document.querySelector('.vote_result').style.display = 'none';
	document.querySelector('.choice_list').innerHTML = '';

	choices.forEach((choice, index) => {
		const li = document.createElement('li');

		const wrap = document.createElement('div');
		wrap.classList.add('vote_checkbox_wrap');

		const checkbox = document.createElement('input');
		checkbox.type = 'checkbox';
		checkbox.classList.add('checkbox_group');
		checkbox.id = `vote${index + 1}`;
		checkbox.value = choice.id;

		const label = document.createElement('label');
		label.setAttribute('for', `vote${index + 1}`);
		label.textContent = choice.choice;

		wrap.appendChild(checkbox);
		wrap.appendChild(label);
		li.appendChild(wrap);
		document.querySelector('.choice_list').appendChild(li);
	});
}



/**
 * 하나의 체크박스만 선택 가능하도록 제어하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.querySelector('.choice_list').addEventListener('change', function (e) {
		if (e.target.classList.contains('checkbox_group')) {
			if (e.target.checked) {
				document.querySelectorAll('.checkbox_group').forEach((cb) => {
					if (cb !== e.target) {
						cb.disabled = true;
						cb.classList.add('disable');
					}
				});
			} else {
				document.querySelectorAll('.checkbox_group').forEach((cb) => {
					cb.disabled = false;
					cb.classList.remove('disable');
				});
			}
		}
	});
});



/**
 * 투표의 결과를 보여주는 함수
 * 
 * @param {Array} choices - 각 선택지와 투표 수 및 비율 정보가 담긴 객체 배열
 * @param {number} choiceId - 사용자가 선택한 선택지의 ID
 */
function showRecord(choices, choiceId) {
	document.querySelector('.vote_form').style.display = 'none';
	document.querySelector('.vote_result').style.display = 'block';

	const listElement = document.querySelector('.record_list');
	listElement.innerHTML = '';

	choices.forEach(choice => {
		const isChoiced = choice.id === choiceId;

		const li = document.createElement('li');
		
		const wrap = document.createElement('div');
		wrap.classList.add('vote_option_wrap');
		if (isChoiced) {
			wrap.classList.add('select');
		}
		wrap.style.setProperty('--bar-width', `${choice.rate}%`);

		const h5 = document.createElement('h5');
		if (isChoiced) {
			const icon = document.createElement('img');
			icon.src = '/images/chompessor/check_blue.png';
			h5.appendChild(icon);
		}
		h5.appendChild(document.createTextNode(choice.choice));

		const span = document.createElement('span');
		span.innerText = `${choice.count}명`;

		const h3 = document.createElement('h3');
		h3.innerText = `${choice.rate}%`;

		wrap.appendChild(h5);
		wrap.appendChild(span);
		wrap.appendChild(h3);
		
		li.appendChild(wrap);
		listElement.appendChild(li);
	});
}



/**
 * 투표하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {

	const form = document.getElementById('vote_form');
	
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		const voteId = new URLSearchParams(window.location.search).get('id');
		const token = localStorage.getItem('accessToken');
		
		if (token) {
			const payload = parseJwt(token);
			const checked = document.querySelector('.checkbox_group:checked');
			
			if (!checked) {
				alert('항목을 선택해주세요.');
				return;
			}
	
			const data = {
				user_id: payload.id,
				vote_id: voteId,
				choice_id: checked.value
			};
			
			try {
				const response = await instance.post(`/votes/${voteId}/records`, data);
				
				if (response.data.code === 'VOTE_RECORD_CREATE_SUCCESS') {
					location.reload();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;

				if (code === 'VOTE_RECORD_CREATE_FAIL') {
					alert(result.message);
				}
				else if (code === 'VOTE_RECORD_INVALID_INPUT') {
					alert(result.message);
				}
				else if (code === 'VOTE_UNAUTHORIZED_ACCESS') {
					alert(result.message);
				}
				else if (code === 'VOTE_FORBIDDEN') {
					alert(result.message);
				}
				else if (code === 'VOTE_NOT_STARTED') {
					alert(result.message);
				}
				else if (code === 'VOTE_ALREADY_ENDED') {
					alert(result.message);
				}
				else if (code === 'USER_NOT_FOUND') {
					alert(result.message);
				}
				else if (code === 'USER_RECORD_DUPLICATE') {
					alert(result.message);
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alert(result.message);
				}
				else {
					console.log('서버 요청 중 오류 발생');
				}
			}
		}
		else {
			if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
				location.href = '/user/login.do';
			}
		}
	});
});



/**
 * 투표를 취소하는 함수
 */
async function cancelVote() {
	
	const voteId = new URLSearchParams(window.location.search).get('id');
	const token = localStorage.getItem('accessToken');
	
	if (token) {
		const payload = parseJwt(token);
		const userId = payload.id;
		
		const data = {
			user_id: Number(userId),
			vote_id: Number(voteId)
		};
		
		try {
			const response = await instance.delete(`/votes/${voteId}/records`, {
				data : data
			});
			
			if (response.data.code === 'VOTE_RECORD_DELETE_SUCCESS') {
	            location.reload();
	        }
		}
		catch (error) {
			const code = error?.response?.data?.code;
			
			if (code === 'VOTE_RECORD_DELETE_FAIL') {
				alert(result.message);
			}
			else if (code === 'VOTE_RECORD_INVALID_INPUT') {
				alert(result.message);
			}
			else if (code === 'VOTE_UNAUTHORIZED_ACCESS') {
				alert(result.message);
			}
			else if (code === 'VOTE_FORBIDDEN') {
				alert(result.message);
			}
			else if (code === 'VOTE_NOT_STARTED') {
				alert(result.message);
			}
			else if (code === 'VOTE_ALREADY_ENDED') {
				alert(result.message);
			}
			else if (code === 'USER_NOT_FOUND') {
				alert(result.message);
			}
			else if (code === 'VOTE_RECORD_NONT_FOUND') {
				alert(result.message);
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alert(result.message);
			}
			else {
				console.log('서버 요청 중 오류 발생');
			}
		}
	}
	else {
		if (confirm('로그인이 필요합니다. 로그인 페이지로 이동합니다.')) {
			location.href = '/user/login.do';
		}
	}
}




/**
 * 댓글 정렬 버튼 클릭 시 해당하는 댓글을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {	
	
	// 정렬 버튼 클릭 시 초기화 후 댓글 목록 조회
	document.querySelectorAll('.comment_order button').forEach(tab => {
		tab.addEventListener('click', function (event) {
			event.preventDefault();
			document.querySelectorAll('.comment_order button').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
			
			sort = this.getAttribute('data-sort');
			page = 0;
			
			document.querySelector('.comment_list').innerHTML = '';
			commentList = [];
			
			fetchCommentList('chomp_vote');
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList('chomp_vote');
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList('chomp_vote');
	});
});
