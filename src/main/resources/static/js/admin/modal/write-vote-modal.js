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
	
	// 옵션 목록 실시간 검사
	// const choiceList = Array
	
});






/**
 * 모달이 닫히면 폼을 초기화 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('writeVoteModal');
	
	modal.addEventListener('show.bs.modal', () => {
		
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







document.addEventListener('DOMContentLoaded', function () {
  const choiceList = document.getElementById('writeVoteChoiceList');
  const addBtn = document.getElementById('addChoiceBtn');

  // 선택지 한 줄 추가: [input + 삭제 버튼]
  function addChoiceRow(value = '') {
    const row = document.createElement('div');
    row.className = 'd-flex align-items-center gap-2';

    const input = document.createElement('input');
    input.type = 'text';
    input.name = 'choice';
    input.className = 'form-control';
    input.placeholder = '옵션을 입력해주세요';
    if (value) input.value = value;

    const del = document.createElement('button');
    del.type = 'button';
    // del.className = 'btn btn-outline-danger btn-sm';
	del.className = 'btn btn-outline-danger btn-sm flex-shrink-0';
    del.textContent = '삭제';
    del.addEventListener('click', function () {
      // 기본 2칸은 data-fixed="true"로 되어 있어 삭제 버튼이 애초에 없습니다.
      // 추가된 행만 이 삭제 버튼이 있으므로 그냥 제거하면 됩니다.
      row.remove();
    });

    row.append(input, del);
    choiceList.appendChild(row);
    input.focus();
  }

  // 추가 버튼 클릭 시 새 선택지 추가
  addBtn.addEventListener('click', function () {
    addChoiceRow('');
  });
});







/**
 * 투표를 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
})














