/**
 * 투표의 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 투표 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/votes/${id}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'VOTE_READ_SUCCESS') {
			document.querySelector('.vote_title').innerText = result.data.title;
			document.querySelector('.vote_postdate').innerText = `${formatDatePeriod(result.data.opendate, result.data.closedate)}`;
			document.querySelector('.vote_total').innerText = result.data.recordcount;
			
			// 투표 종료
			const now = new Date();
			if (now < new Date(result.data.opendate) || now > new Date(result.data.opendate)) {
				renderRecord(result.data.choice_list, result.data.choice_id);
				document.getElementById('revoteBtn').style.display = 'none';
			}
			// 사용자 투표 완료
			else if (result.data.voted) {
				renderRecord(result.data.choice_list, result.data.choice_id);
			}
			// 비로그인 상태 혹은 투표 미완료
			else {
				renderChoiceList(result.data.choice_list);
			}
		}
		else if (result.code === 'VOTE_READ_FAIL') {
			alert('투표 조회에 실패했습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else if (result.code === 'VOTE_RECORD_READ_FAIL') {
			alert('투표 조회에 실패했습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else if (result.code === 'VOTE_NOT_FOUND') {
			alert('해당 투표를 찾을 수 없습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else {
			console.log(error);
		}
	}
	catch (error) {
		console.log(error);
	}
});





/**
 * 투표의 선택지를 보여주는 함수
 */
function renderChoiceList(choiceList) {

	document.querySelector('.vote_form').style.display = 'block';
	document.querySelector('.vote_result').style.display = 'none';
	document.querySelector('.choice_list').innerHTML = '';

	choiceList.forEach((choice, index) => {
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

		wrap.append(checkbox, label);
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
 */
function renderRecord(choiceList, choiceId) {
	document.querySelector('.vote_form').style.display = 'none';
	document.querySelector('.vote_result').style.display = 'block';

	const listElement = document.querySelector('.record_list');
	listElement.innerHTML = '';

	choiceList.forEach(choice => {
		const isChoiced = choice.id === choiceId;

		const li = document.createElement('li');
		
		const wrap = document.createElement('div');
		wrap.classList.add('vote_option_wrap');
		if (isChoiced) {
			wrap.classList.add('select');
		}
		wrap.style.setProperty('--bar-width', `${choice.recordrate}%`);

		const h5 = document.createElement('h5');
		if (isChoiced) {
			const icon = document.createElement('img');
			icon.src = '/images/chompessor/check_blue.png';
			h5.appendChild(icon);
		}
		h5.appendChild(document.createTextNode(choice.choice));

		const span = document.createElement('span');
		span.innerText = `${choice.recordcount}명`;

		const h3 = document.createElement('h3');
		h3.innerText = `${choice.recordrate}%`;

		wrap.append(h5, span, h3);
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
	
	const tablename = 'vote';
	let sort = 'postdate-desc';
	let size = 15;

	
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
			
			fetchCommentList(tablename, sort, size);
		});
	});
	
	// 최초 댓글 목록 조회
	fetchCommentList(tablename, sort, size);
	
	// 더보기 버튼 클릭 시 다음 페이지 로드
	document.querySelector('.btn_more').addEventListener('click', function () {
		fetchCommentList(tablename, sort, size);
	});
});




/**
 * 댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	document.getElementById('writeCommentForm').addEventListener('submit', function (event) {
		event.preventDefault();
		writeComment('vote');
	});
});



/**
 * 대댓글을 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	
	document.getElementById('writeSubcommentForm').addEventListener('submit', function (event) {
		event.preventDefault();
		writeSubcomment('vote');
	});
	
});


