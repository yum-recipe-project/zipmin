/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
	
	document.querySelectorAll(".checkbox_group").forEach((checkbox) => {
		checkbox.addEventListener("change", function() {
			// 체크되면 모든 체크박스를 disabled 처리
			if (this.checked) {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					cb.disabled = true;
				});
			}
			// 하나도 체크되지 않으면 다시 활성화
			else {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					cb.disabled = false;
				});
			}
		});
	});
});
