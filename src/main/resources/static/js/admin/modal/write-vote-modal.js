/**
 * 투표 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 제목 실시간 검사
	const title = document.getElementById('writeVoteTitleInput');
	title.addEventListener('input', function() {
		const isTitleEmpty = this.value.trim() === '';
		title.classList.toggle('is-invalid', isTitleEmpty);
		document.getElementById('writeVoteTitleHint').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 날짜 실시간 검사
	const opendate = document.getElementById('writeVoteOpendateInput');
	const closedate = document.getElementById('writeVoteClosedateInput');
	opendate.addEventListener('input', function() {
		const isOpendateEmpty = this.value.trim() ==='';
		opendate.classList.toggle('is-invalid', isOpendateEmpty);
		if (closedate.value.trim() !== '') {
			document.getElementById('writeVoteDateHint').style.display = isOpendateEmpty ? 'block' : 'none';
		}
	});
	closedate.addEventListener('input', function() {
		const isClosedateEmpty = this.value.trim() ==='';
		closedate.classList.toggle('is-invalid', isClosedateEmpty);
		if (opendate.value.trim() !== '') {
			document.getElementById('writeVoteDateHint').style.display = isClosedateEmpty ? 'block' : 'none';
		}
	});
	opendate.addEventListener('change', function () {
		closedate.min = opendate.value;
	});
	closedate.addEventListener('change', function () {
		opendate.max = closedate.value;
	});
	
	// 옵션 실시간 검사
	const choiceList = document.getElementById('writeVoteChoiceList');
	choiceList.addEventListener('input', function(event) {
		if (event.target && event.target.name === 'choice') {
			const value = event.target.value.trim().toLowerCase();
			
			// 공백 또는 중복 여부
			let invalid = value === '';
			if (!invalid) {
				const others = Array.from(choiceList.querySelectorAll('input[name="choice"]'))
					.filter(el => el !== event.target);
				invalid = others.some(other => other.value.trim().toLowerCase() === value && value !== '');
			}
			event.target.classList.toggle('is-invalid', invalid);
			
			// 힌트 표시
			const hasInvalid = choiceList.querySelector('.is-invalid') !== null;
			document.getElementById('writeVoteChoiceHint').style.display = hasInvalid ? 'block' : 'none';
		}
	});
	
});






/**
 * 모달이 닫히면 폼을 초기화 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('writeVoteModal');
	modal.addEventListener('hidden.bs.modal', function() {
		
		const form = document.getElementById('writeVoteForm');

		if (form) {
			form.reset();
		}
		
		modal.querySelectorAll('.is-invalid').forEach(el => 
			{el.classList.remove('is-invalid')
		});
		modal.querySelectorAll('p[id*="Hint"]').forEach(p => {
			p.style.display = 'none'
		});
	});
	
});







/**
 * 선택지 추가 버튼 클릭시 선택지를 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function () {
	const choiceList = document.getElementById('writeVoteChoiceList');
	const addChoiceBtn = document.getElementById('addChoiceBtn');
	
	addChoiceBtn.addEventListener('click', function() {
		const row = document.createElement('div');
		row.className = 'd-flex align-items-center gap-2';
		
		const input = document.createElement('input');
		input.type = 'text';
		input.name = 'choice';
		input.className = 'form-control';
		input.placeholder = '옵션을 입력해주세요';
		
		const del = document.createElement('button');
		del.type = 'button';
		del.className = 'btn btn-outline-danger btn-sm flex-shrink-0';
		del.textContent = '삭제';
		del.addEventListener('click', function () {
			row.remove();
		});
		
		row.append(input, del);
		choiceList.insertBefore(row, addChoiceBtn);
		input.focus();
	});
});






/**
 * 투표를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('writeVoteForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const choiceList = document.querySelectorAll('#writeVoteChoiceList input[name="choice"]');
		choiceList.forEach(choice => {
			if (choice.value.trim() === '') {
				choice.classList.add('is-invalid');
				document.getElementById('writeVoteChoiceHint').style.display = 'block';
				choice.focus();
				isValid = false;
			}
		});

		const closedate = document.getElementById('writeVoteClosedateInput');
		if (closedate.value.trim() === '') {
			closedate.classList.add('is-invalid');
			document.getElementById('writeVoteDateHint').style.display = 'block';
			closedate.focus();
			isValid = false;
		}

		const opendate = document.getElementById('writeVoteOpendateInput');
		if (opendate.value.trim() === '') {
			opendate.classList.add('is-invalid');
			document.getElementById('writeVoteDateHint').style.display = 'block';
			opendate.focus();
			isValid = false;
		}
		
		const title = document.getElementById('writeVoteTitleInput');
		if (title.value.trim() === '') {
			title.classList.add('is-invalid');
			document.getElementById('writeVoteTitleHint').style.display = 'block';
			title.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const data = {
					title: title.value.trim(),
					opendate: opendate.value.trim(),
					closedate: closedate.value.trim(),
					choice_list: Array.from(choiceList).map(input => input.value.trim()),
					category: 'vote'
				};
				
				const response = await instance.post('/votes', data, {
					headers: getAuthHeaders()
				});
				
				console.log(response);
				
				if (response.data.code === 'VOTE_CREATE_SUCCESS') {
					alert('성공');
				}
				
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				/******* 추가하기 !!!!!!!!!! ********/
				if (code === 'VOTE_CREATE_FAIL') {
					alertDanger('투표 작성에 실패했습니다.');
				}
			}
		}
		
	})
	
	
	
});














