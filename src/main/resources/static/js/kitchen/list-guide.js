/**
 * 전역 변수
 */
let category = '';
let sort = 'id-desc';
let totalPages = 0;
let page = 0;
let keyword = '';
const size = 10;
let guideList = [];





/**
 * 카테고리 버튼 클릭시 연결된 내용을 표시하는 함수
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
	

	// 검색
	document.querySelector('.search_form[data-type="kitchen"]').addEventListener('submit', function(e) {
	    e.preventDefault();
	    keyword = this.querySelector('.search_word').value.trim();
	    page = 0;        
	    guideList = [];
	    fetchGuideList();
	});
	
	fetchGuideList();
});




/**
 * 서버에서 키친가이드 목록 데이터를 가져오는 함수
 */
async function fetchGuideList() {
	const token = localStorage.getItem('accessToken');
	const payload = parseJwt(token);
	
	
	try {
		const params = new URLSearchParams({
			category: category,
			keyword: keyword,  
			sort: sort,
			page: page,
			size: size
		});
		
		const response = await fetch(`/guides?${params}`, {
			method: 'GET',
			headers: getAuthHeaders()
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

	    const favBtn = renderLikeButton(guide.id, guide.likestatus);
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
}


/**
 * 좋아요(찜) 버튼 렌더링 함수
 * 
 * @param {number} id - 가이드(게시글) ID
 * @param {boolean} likestatus - 현재 사용자가 좋아요를 눌렀는지 여부
 */
function renderLikeButton(id, likestatus) {
	
    const button = document.createElement('button');
    button.className = 'favorite_btn';

    const img = document.createElement('img');
    img.src = likestatus ? '/images/common/star_full.png' : '/images/recipe/star_empty.png';
    button.append(img);

    // 좋아요 버튼 동작
    button.addEventListener('click', async (event) => {
        event.preventDefault();

		const token = localStorage.getItem('accessToken');
		
        if (!token) {
            alert("로그인이 필요합니다.");
            return;
        }

		try {
           const url = `/guides/${id}/likes`;
           const options = {
               method: likestatus ? 'DELETE' : 'POST',
               headers: {
                   ...getAuthHeaders(),
                   'Content-Type': 'application/json'
               },
               body: JSON.stringify({
                   tablename: "guide",
                   recodenum: id
               })
           };

           const response = await fetch(url, options);
           const result = await response.json();

           // 성공 코드 분기
           if (result.code === "KITCHEN_LIKE_SUCCESS" || result.code === "KITCHEN_UNLIKE_SUCCESS") {
               // 상태 토글
               likestatus = !likestatus;
               img.src = likestatus
                   ? '/images/common/star_full.png'
                   : '/images/recipe/star_empty.png';

               // 스크랩 수 갱신
               const likeCountElement = button.closest('.guide_item')
                   .querySelector('.info p:first-child');
               let currentCount = parseInt(likeCountElement.textContent.replace(/\D/g, '')) || 0;
               currentCount = likestatus ? currentCount + 1 : currentCount - 1;
               likeCountElement.textContent = `스크랩 ${currentCount}`;
           } else {
               alert(result.message || "좋아요 요청 실패");
           }

       } catch (error) {
           console.error("좋아요 처리 중 오류:", error);
       }
   });

    return button;
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

