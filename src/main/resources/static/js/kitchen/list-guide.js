/**
 * 전역 변수
 */
let category = '';
let sort = 'new';
let totalPages = 0;
let page = 0;
const size = 10;
let guideList = [];





/**
 * 카테고리 클릭 시 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 카테고리 버튼
	document.querySelectorAll('.btn_tab').forEach(btn => {
        btn.addEventListener('click', function (event) {
            event.preventDefault();
            document.querySelector('.btn_tab.active')?.classList.remove('active');
            btn.classList.add('active');

            category = btn.dataset.category;
            page = 0;
			guideList = [];
			
            fetchGuideList();
        });
    });
	
	// 정렬 버튼
	document.querySelectorAll('.btn_sort').forEach(btn => {
		btn.addEventListener('click', function(event) {
			event.preventDefault();
			document.querySelector('.btn_sort.active')?.classList.remove('active');
			btn.classList.add('active');
			
			sort = btn.dataset.sort;
			page = 0;
			guideList = [];
			
			fetchGuideList();
		});
	});
	
	fetchGuideList();
});





/**
 * 서버에서 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchGuideList() {
	
	try {
		const params = new URLSearchParams({
			category: category,
			sort: sort,
			page: page,
			size: size
		});
		
		const response = await fetch(`/guides?${params}`, {
			method: 'GET'
		});
		const result = await response.json();
		
		if (result.code === 'KITCHEN_READ_LIST_SUCCESS') {
			
            totalPages = result.data.totalPages;
            page = result.data.number;
			guideList = result.data.content;
			
            renderGuideList(result.data.content);
            renderPagination();
			
			document.querySelector('.guide_util .total').innerText = `총 ${result.data.totalElements}개`;
		}
		// ****** 여기에 다른 에러 코드들 else if로 추가 ********
		// common/comment.js 참고 !!
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 키친가이드 목록을 화면에 렌더링하는 함수
 * 
 * @param {Array} guideList - 키친가이드 목록 배열
 */
function renderGuideList(guideList) {
    const container = document.querySelector('.guide_list');
    container.innerHTML = '';

    guideList.forEach(guide => {
        const li = document.createElement('li');

        const a = document.createElement('a');
        a.href = `/kitchen/viewGuide.do?id=${guide.id}`;

        const guideItem = document.createElement('div');
        guideItem.className = 'guide_item';

        const guideDetails = document.createElement('div');
        guideDetails.className = 'guide_details';

        const guideTop = document.createElement('div');
        guideTop.className = 'guide_top';

        const subtitleSpan = document.createElement('span');
        subtitleSpan.textContent = guide.subtitle;

        const favBtn = document.createElement('button');
        favBtn.className = 'favorite_btn';

        guideTop.append(subtitleSpan, favBtn);

        const titleSpan = document.createElement('span');
        titleSpan.textContent = guide.title;

        const infoDiv = document.createElement('div');
        infoDiv.className = 'info';

        const scrapP = document.createElement('p');
        scrapP.textContent = `스크랩 ${guide.likecount}`;

        const dateP = document.createElement('p');
        dateP.textContent = formatDate(guide.postdate);

        infoDiv.append(scrapP, dateP);

        const writerDiv = document.createElement('div');
        writerDiv.className = 'writer';

        if (guide.writerImage) {
            const img = document.createElement('img');
            img.src = guide.writerImage;
            writerDiv.appendChild(img);
        } else {
            const profileImg = document.createElement('span');
            profileImg.className = 'profile_img';
            writerDiv.appendChild(profileImg);
        }

        const nicknameP = document.createElement('p');
        nicknameP.textContent = '집밥의민족';
        writerDiv.appendChild(nicknameP);

        guideDetails.append(guideTop, titleSpan, infoDiv, writerDiv);
        guideItem.appendChild(guideDetails);
        a.appendChild(guideItem);
        li.appendChild(a);
        container.appendChild(li);
    });

	/******** 이거 수정 필요 *********/
	/*** Dto에 likestatus를 저장해두었으므로 필요없음 ***/
	/*** 위 코드의 favBtn을 적절히 표시하고 그 버튼 눌렀을 때의 백엔드 처리를 만들기 ****/
    initFavoriteButtons(); 
}





/**
 * 찜 버튼 클릭 이벤트 등록 함수
 * 
 * ***** common/comment.js 보고 수정할 것 (renderLikeButton 부분)
 */
function initFavoriteButtons() {
    document.querySelectorAll(".favorite_btn").forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            e.stopPropagation();
            button.classList.toggle("active");
        });
    });
}





/**
 * 페이지네이션을 화면에 렌더링하는 함수
 */
function renderPagination() {
	const pagination = document.querySelector('.pagination ul');
	pagination.innerHTML = '';

	// 이전 버튼
	const prevLi = document.createElement('li');
	const prevLink = document.createElement('a');
	prevLink.href = '#';
	prevLink.className = 'prev';
	prevLink.dataset.page = page - 1;

	if (page === 0) {
		prevLink.style.pointerEvents = 'none';
		prevLink.style.opacity = '0';
	}

	const prevImg = document.createElement('img');
	prevImg.src = '/images/common/chevron_left.png';
	prevLink.appendChild(prevImg);
	prevLi.appendChild(prevLink);
	pagination.appendChild(prevLi);

	// 페이지 번호
	for (let i = 0; i < totalPages; i++) {
		const li = document.createElement('li');
		const a = document.createElement('a');
		a.href = '#';
		a.className = `page${i === page ? ' active' : ''}`;
		a.dataset.page = i;
		a.textContent = i + 1;
		li.appendChild(a);
		pagination.appendChild(li);
	}

	// 다음 버튼
	const nextLi = document.createElement('li');
	const nextLink = document.createElement('a');
	nextLink.href = '#';
	nextLink.className = 'next';
	nextLink.dataset.page = page + 1;

	if (page === totalPages - 1) {
		nextLink.style.pointerEvents = 'none';
		nextLink.style.opacity = '0';
	}

	const nextImg = document.createElement('img');
	nextImg.src = '/images/common/chevron_right.png';
	nextLink.appendChild(nextImg);
	nextLi.appendChild(nextLink);
	pagination.appendChild(nextLi);

	// 바인딩
	document.querySelectorAll('.pagination a').forEach(link => {
		link.addEventListener('click', function (e) {
			e.preventDefault();
			const newPage = parseInt(this.dataset.page);
			if (!isNaN(newPage) && newPage >= 0 && newPage < totalPages && newPage !== page) {
				page = newPage;
				fetchGuideList();
			}
		});
	});
}

