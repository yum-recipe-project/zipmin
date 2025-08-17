/**
 * 서버에서 신고 목록 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('listReportModal');
	modal.addEventListener('show.bs.modal', async function (event) {
	
		try {
			const params = new URLSearchParams({
				tablename : 'comments',
				recodenum : event.relatedTarget?.dataset.recodenum
			}).toString();
			
			const response = await instance.get(`/reports?${params}`, {
				headers: getAuthHeaders()
			});
			
			console.log(response);
			
			if (response.data.code === 'REPORT_READ_LIST_SUCCESS') {
				renderReportList(response.data.data);
				
				document.getElementById('reportCount').innerText = `총 ${response.data.data.length}개`;
			}
		}
		catch (error) {
			/*****!!!!!!!! 에러코드 추가 */
			console.log(error);
		}
	});
	
});




/**
 * 신고 목록을 화면에 렌더링하는 함수
 */
function renderReportList(reportList) {
	const container = document.querySelector('.report_list');
	container.innerHTML = '';
	
	reportList.forEach((report, index) => {
		const tr = document.createElement('tr');
		
		// 번호
		const noTd = document.createElement('td');
		const noH6 = document.createElement('h6');
		noH6.className = 'fw-semibold mb-0';
		noH6.textContent = index + 1;
		noTd.appendChild(noH6);
		
		// 신고 사유
		const reasonTd = document.createElement('td');
		const reasonH6 = document.createElement('h6');
		reasonH6.className = 'fw-semibold mb-0';
		reasonH6.textContent = report.reason;
		reasonTd.appendChild(reasonH6);
		
		// 신고자
		const writerTd = document.createElement('td');
		const writerH6 = document.createElement('h6');
		writerH6.className = 'fw-semibold mb-0';
		writerH6.textContent = `${report.nickname} (${report.username})`;
		writerTd.appendChild(writerH6);
		
		tr.append(noTd, reasonTd, writerTd);
		container.appendChild(tr);
	});
	
}
