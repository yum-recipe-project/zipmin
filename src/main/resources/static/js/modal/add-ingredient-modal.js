
// TODO: 모듈화
document.addEventListener("DOMContentLoaded", function() {
	/**
	 * 냉장고 채우기 모달 
	 */
	
    // 냉장고 채우기 버튼과 모달 관련 요소 선택
    const openModalButton = document.querySelector('.btn_primary');
    const modal = document.getElementById('addIngredientModal');
    const submitButton = modal.querySelector('button[type="submit"]');

	// 모달 오픈
    openModalButton.addEventListener("click", function() {
        const modalInstance = new bootstrap.Modal(modal);
        modalInstance.show();
    });

	// 재료 추가 
    submitButton.addEventListener("click", function() {
        console.log("재료 추가 완료");
    });
	
	
	
	

	/**
	 * 재료 입력 - 이미지 선택하기
	 */
	const ingredientAddBtn = document.getElementById("ingredientAddBtn");
    const imageSelectWrap = document.getElementById("imageSelectWrap");
    const ingredientImg = document.querySelector(".ingredient_img");

    // 이미지 선택 영역 토글 기능
    ingredientAddBtn.addEventListener("click", function () {
        if (imageSelectWrap.style.display === "none") {
            imageSelectWrap.style.display = "block";
        } else {
            imageSelectWrap.style.display = "none";
        }
    });

    // 이미지 선택 시 해당 이미지 적용
    document.querySelectorAll(".img_btn").forEach((button) => {
        button.addEventListener("click", function () {
            const selectedImgSrc = this.querySelector("img").src;
            ingredientImg.innerHTML = `<img src="${selectedImgSrc}" alt="선택한 재료">`;
            imageSelectWrap.style.display = "none"; // 선택 후 이미지 선택창 숨기기
        });
    });
	

		
			
});
