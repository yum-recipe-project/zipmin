/**
 * 전역변수
 */
let totalPages = 0;
let totalElements = 0;
let page = 0;
const size = 20;
let keyword = '';
let categoryList = [];
let sortKey = 'id';
let sortOrder = 'desc';
let recipeList = [];





/**
 * 레시피 목록 검색 필터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// ***** 검색 추가 *****
	
	// ***** 카테고리 추가 *****
	
	// ***** 정렬버튼 추가 *****
	
	// ***** 팔로우만 보기 추가 *****
	
	fetchRecipeList();
});





/**
 * 서버에서 레시피 목록 데이터를 가져오는 함수
 */
async function fetchRecipeList() {
	
	try {
		const category = new URLSearchParams(location.search).get('category') ?? '';
		if (category) categoryList.push(category);

		const params = new URLSearchParams();
		categoryList.forEach(category => params.append('categoryList', category));
		params.append('sort', `${sortKey}-${sortOrder}`);
		params.append('keyword', keyword);
		params.append('page', page);
		params.append('size', size);
		
		const response = await fetch(`/recipes?${params.toString()}`, {
			method: 'GET',
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'RECIPE_READ_LIST_SUCCESS') {
			
			// 전역 변수 설정
			totalPages = result.data.totalPages;
			totalElements = result.data.totalElements;
			page = result.data.number;
			recipeList = result.data.content;
			
			// 렌더링
			renderRecipeList(recipeList);
			// renderPagination(fetchRecipeList);
			document.querySelector('.total').innerText = `총 ${totalElements}개`;
			
			// 검색 결과 없음 표시
			if (result.data.totalPages === 0) {
				document.querySelector('.recipe_list').style.display = 'none';
				document.querySelector('.search_empty')?.remove();
				const table = document.querySelector('.recipe_content');
				table.insertAdjacentElement('afterend', renderSearchEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.search_empty')?.remove();
				document.querySelector('.recipe_list').style.display = '';
			}

			// 스크롤 최상단 이동
			window.scrollTo({ top: 0, behavior: 'smooth' });
		}
		
		/***** 에러코드 추가 *****/
		
	}
	catch (error) {
		console.log(error);
	}
	
}





/**
 * 쩝쩝박사 목록을 화면에 렌더링하는 함수
 */
function renderRecipeList(recipeList) {
	
}


















