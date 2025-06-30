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
			const opendate = new Date(result.data.opendate);
			const closedate = new Date(result.data.closedate);
			const formatDate = `${opendate.getFullYear()}년 ${String(opendate.getMonth() + 1).padStart(2, '0')}월 ${String(opendate.getDate()).padStart(2, '0')}일 - ${closedate.getFullYear()}년 ${String(closedate.getMonth() + 1).padStart(2, '0')}월 ${String(closedate.getDate()).padStart(2, '0')}일`;
			document.querySelector('.vote_postdate').innerText = formatDate;
			document.querySelector('.vote_total').innerText = result.data.total;
			
			// 사용자 투표 완료
			if (result.data.voted) {
				showRecord(result.data.choice_list, result.data.choice_id);
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
 * @param {*} choices - 선택지
 */
function showChoice(choices) {
	document.querySelector('.vote_form').style.display = 'block';
	document.querySelector('.vote_result').style.display = 'none';
	
	document.querySelector('.choice_list').innerHTML = '';

	choices.forEach((choice, index) => {
		document.querySelector('.choice_list').innerHTML += `
			<li>
				<div class="vote_checkbox_wrap">
					<input class="checkbox_group" type="checkbox" id="vote${index + 1}" value="${choice.id}">
					<label for="vote${index + 1}">${choice.choice}</label>
				</div>
			</li>
		`;
	});
}



/**
 * !!!!!!!!! 수정 필요 !!!!!!!!!!!!
 */
/*
function showRecord(choices, choiceId) {
	document.querySelector('.vote_form').style.display = 'none';
	document.querySelector('.vote_result').style.display = 'block';
	
	const listElement = document.querySelector('.record_list');
	listElement.innerHTML = '';

	choices.forEach(choice => {
		const isChoiced = choice.id === choiceId;
		const icon = isChoiced ? `<img src="/images/chompessor/check_blue.png">` : '';
		const choicedClass = isChoiced ? 'select' : '';

		listElement.innerHTML += `
			<li>
				<div class="vote_option_wrap ${choicedClass}">
					<h5>${icon}${choice.choice}</h5>
					<span>${choice.count}명</span>
					<h3>${choice.rate}%</h3>
				</div>
			</li>
		`;
	});
}
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

		// 텍스트들
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
 * 투표
 */
document.addEventListener("DOMContentLoaded", function() {
	
	document.querySelectorAll(".checkbox_group").forEach((checkbox) => {
		checkbox.addEventListener("change", function() {
			// 체크되면 모든 체크박스를 disabled 처리
			if (this.checked) {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					if (cb !== this) {
						cb.disabled = true;
						cb.classList.add("disable");
					}
				});
			}
			// 하나도 체크되지 않으면 다시 활성화
			else {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					cb.disabled = false;
					cb.classList.remove("disable");
				});
			}
		});
	});
});



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
