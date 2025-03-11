/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	/**
	 * 카테고리 선택 
	 */	
	const tabs = document.querySelectorAll(".btn_tab");

	tabs.forEach(tab => {
	    tab.addEventListener("click", function(event) {
	        event.preventDefault(); 

	        document.querySelector(".btn_tab.active")?.classList.remove("active");

	        this.classList.add("active");
	    });
	});
		
		
	
	

		
});