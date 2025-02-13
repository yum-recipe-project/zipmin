/**
 * 
 */

/**
 * 리뷰 작성 시 별점 선택하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const stars = document.querySelectorAll('#starGroup .star');
	const starInput = document.getElementById('starInput');
	alert("1");
	
	alert(stars[0].getAttribute('data-value'));
	
	stars.forEach(star => {
		alert(star.getAttribute('data-value'));
		
		star.addEventListener('click', function() {
			
			alert("클릭");
			const selectedValue = Number(this.getAttribute('data-value'));
			starInput.value = selectedValue;
			
			// 선택된 별과 그 이하의 별은 색을 변경
			stars.forEach(s => {
				const starValue = Number(s.getAttribute('data-value'));
				s.src = starValue <= selectedValue ? '/images/recipe/star_full.png' : '/images/recipe/star_outline.png';
			});
		});
	});
});

